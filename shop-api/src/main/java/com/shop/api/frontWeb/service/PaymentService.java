package com.shop.api.frontWeb.service;

import com.shop.core.entity.Payment;
import com.shop.core.entity.PaymentDet;
import com.shop.core.entity.Order;
import com.shop.core.entity.PointHistory;
import com.shop.core.enums.OrderStatus;
import com.shop.core.enums.PaymentStatus;
import com.shop.core.enums.PointType;
import com.shop.core.frontWeb.dao.CartDao;
import com.shop.core.frontWeb.dao.OrderDao;
import com.shop.core.frontWeb.dao.PaymentDao;
import com.shop.core.frontWeb.dao.PointDao;
import com.shop.core.frontWeb.vo.request.PaymentRequest;
import com.shop.core.frontWeb.vo.response.PaymentResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentDao paymentDao;
    private final OrderDao orderDao;
    private final CartDao cartDao;
    private final PointDao pointDao;
    private final PortOnePaymentClient portOnePaymentClient;

    @Transactional
    public PaymentResponse.Info createPayment(PaymentRequest.Create request) {
        // orderId가 없으면 orderNo로 조회 (order 생성 응답 파싱 실패 방어)
        Long orderId = request.getOrderId();

        if (orderId == null) {
            throw new IllegalArgumentException("orderId를 확인할 수 없습니다. orderNo=" + request.getOrderNo());
        }

        Payment payment = Payment.builder()
                .orderId(orderId)
                .orderNo(request.getOrderNo())
                .paymentId(request.getPaymentId())
                .paymentStatus(PaymentStatus.PAID.getCode())
                .totalAmount(request.getTotalAmount())
                .currency(request.getCurrency())
                .cancelAmount(0L)
                .delYn("N")
                .build();
        paymentDao.insertPayment(payment);

        if (request.getDetails() != null) {
            for (PaymentRequest.CreateDet requestDet : request.getDetails()) {
                PaymentDet det = PaymentDet.builder()
                        .paymentId(payment.getId())
                        .payType(requestDet.getPayType())
                        .payMethod(requestDet.getPayMethod())
                        .amount(requestDet.getAmount())
                        .pgProvider(requestDet.getPgProvider())
                        .pgTid(requestDet.getPgTid())
                        .portonePaymentId(requestDet.getPortonePaymentId())
                        .rawResponse(requestDet.getRawResponse())
                        .cancelAmount(0L)
                        .delYn("N")
                        .build();
                paymentDao.insertPaymentDet(det);
            }
        }

        orderDao.updateOrderStatus(payment.getOrderId(), OrderStatus.PAID.getCode());
        clearOrderedCart(request, payment);

        return getPayment(payment.getId());
    }

    public PaymentResponse.Info getPayment(Long paymentId) {
        Payment payment = paymentDao.selectPaymentById(paymentId);
        if (payment == null) return null;
        return toInfo(payment);
    }

    public PaymentResponse.Info getPaymentByOrderNo(String orderNo) {
        Payment payment = paymentDao.selectPaymentByOrderNo(orderNo);
        if (payment == null) return null;
        return toInfo(payment);
    }

    @Transactional
    public PaymentResponse.Info cancelPayment(Long paymentSeq, PaymentRequest.Cancel request) {
        Payment payment = paymentDao.selectPaymentById(paymentSeq);
        if (payment == null) {
            throw new IllegalArgumentException("Payment not found.");
        }
        if (PaymentStatus.CANCELLED.getCode().equals(payment.getPaymentStatus())) {
            PaymentResponse.Info info = toInfo(payment);
            info.setAlreadyCancelled(true);
            return info;
        }
        if (!PaymentStatus.PAID.getCode().equals(payment.getPaymentStatus())) {
            throw new IllegalStateException("Only paid payments can be cancelled.");
        }

        Order order = orderDao.selectOrderById(payment.getOrderId());
        if (order == null) {
            throw new IllegalStateException("Order not found.");
        }
        if (request != null && request.getSocialAccountId() != null
                && !request.getSocialAccountId().equals(order.getSocialAccountId())) {
            throw new IllegalStateException("Payment owner does not match.");
        }

        List<PaymentDet> details = paymentDao.selectPaymentDets(payment.getId());
        boolean hasPgPayment = details.stream()
                .anyMatch(det -> "PG".equals(det.getPayType()) && det.getAmount() != null && det.getAmount() > 0);
        if (hasPgPayment) {
            portOnePaymentClient.cancelPayment(payment.getPaymentId(), request != null ? request.getReason() : null);
        }

        payment.setCancelAmount(payment.getTotalAmount());
        paymentDao.updatePaymentCancel(payment);

        for (PaymentDet det : details) {
            det.setCancelAmount(det.getAmount());
            paymentDao.updatePaymentDetCancel(det);
        }

        orderDao.updateOrderStatus(payment.getOrderId(), OrderStatus.CANCEL.getCode());

        // ── 포인트 취소 처리 ──
        // 적립된 포인트 차감 (earnedPoint > 0 이면 차감)
        if (order.getEarnedPoint() != null && order.getEarnedPoint() > 0) {
            pointDao.insertPointHistory(PointHistory.builder()
                    .socialAccountId(order.getSocialAccountId())
                    .orderId(order.getId())
                    .pointType(PointType.RESTORE)
                    .pointAmount(-order.getEarnedPoint())
                    .description("주문 " + order.getOrderNo() + " 취소 - 적립 포인트 회수")
                    .build());
        }
        // 사용한 포인트 환불 (usedPoint > 0 이면 복원)
        if (order.getUsedPoint() != null && order.getUsedPoint() > 0) {
            pointDao.insertPointHistory(PointHistory.builder()
                    .socialAccountId(order.getSocialAccountId())
                    .orderId(order.getId())
                    .pointType(PointType.RESTORE)
                    .pointAmount(order.getUsedPoint())
                    .description("주문 " + order.getOrderNo() + " 취소 - 사용 포인트 환불")
                    .build());
        }

        return getPayment(payment.getId());
    }

    public List<PaymentResponse.ListItem> getPaymentList(Long socialAccountId, LocalDate fromDate, LocalDate toDate) {
        LocalDate normalizedToDate = toDate != null ? toDate : LocalDate.now();
        LocalDate normalizedFromDate = fromDate != null ? fromDate : normalizedToDate.minusMonths(1);

        if (normalizedFromDate.isAfter(normalizedToDate)) {
            normalizedFromDate = normalizedToDate.minusMonths(1);
        }
        if (normalizedFromDate.isBefore(normalizedToDate.minusYears(1))) {
            normalizedFromDate = normalizedToDate.minusYears(1);
        }

        LocalDateTime fromDateTime = normalizedFromDate.atStartOfDay();
        LocalDateTime toDateTime = normalizedToDate.plusDays(1).atStartOfDay();
        return paymentDao.selectPaymentListBySocialAccountId(socialAccountId, fromDateTime, toDateTime);
    }

    private void clearOrderedCart(PaymentRequest.Create request, Payment payment) {
        Order order = orderDao.selectOrderById(payment.getOrderId());
        if (order == null || order.getSocialAccountId() == null) {
            log.warn("[clearOrderedCart] order 또는 socialAccountId 없음 → 건너뜀");
            return;
        }
        int updated = cartDao.markOrderedBySocialAccountId(order.getSocialAccountId());
        log.info("[clearOrderedCart] socialAccountId={} updated={}", order.getSocialAccountId(), updated);
    }

    private PaymentResponse.Info toInfo(Payment payment) {
        PaymentResponse.Info info = new PaymentResponse.Info();
        info.setPaymentSeq(payment.getId());
        info.setOrderId(payment.getOrderId());
        info.setOrderNo(payment.getOrderNo());
        info.setPaymentId(payment.getPaymentId());
        info.setPaymentStatus(payment.getPaymentStatus());
        info.setTotalAmount(payment.getTotalAmount());
        info.setCurrency(payment.getCurrency());
        info.setPaidTm(payment.getPaidTm());

        List<PaymentResponse.Det> details = new ArrayList<>();
        for (PaymentDet paymentDet : paymentDao.selectPaymentDets(payment.getId())) {
            PaymentResponse.Det det = new PaymentResponse.Det();
            det.setPaymentDetId(paymentDet.getId());
            det.setPaymentId(paymentDet.getPaymentId());
            det.setPayType(paymentDet.getPayType());
            det.setPayMethod(paymentDet.getPayMethod());
            det.setAmount(paymentDet.getAmount());
            det.setPgProvider(paymentDet.getPgProvider());
            det.setPgTid(paymentDet.getPgTid());
            det.setPortonePaymentId(paymentDet.getPortonePaymentId());
            det.setApprovedTm(paymentDet.getApprovedTm());
            details.add(det);
        }
        info.setDetails(details);
        return info;
    }
}
