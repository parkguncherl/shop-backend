package com.shop.api.frontWeb.service;

import com.shop.core.entity.Payment;
import com.shop.core.entity.PaymentDet;
import com.shop.core.entity.Order;
import com.shop.core.frontWeb.dao.CartDao;
import com.shop.core.frontWeb.dao.OrderDao;
import com.shop.core.frontWeb.dao.PaymentDao;
import com.shop.core.frontWeb.vo.request.PaymentRequest;
import com.shop.core.frontWeb.vo.response.PaymentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentDao paymentDao;
    private final OrderDao orderDao;
    private final CartDao cartDao;
    private final PortOnePaymentClient portOnePaymentClient;

    @Transactional
    public PaymentResponse.Info createPayment(PaymentRequest.Create request) {
        Payment payment = Payment.builder()
                .orderId(request.getOrderId())
                .orderNo(request.getOrderNo())
                .paymentId(request.getPaymentId())
                .paymentStatus("P")
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
                        .paymentNo(requestDet.getPaymentNo())
                        .orderId(payment.getOrderId())
                        .orderNo(payment.getOrderNo())
                        .payType(requestDet.getPayType())
                        .payMethod(requestDet.getPayMethod())
                        .paymentStatus("P")
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

        orderDao.updateOrderStatus(payment.getOrderId(), "P");
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
        if ("C".equals(payment.getPaymentStatus())) {
            return toInfo(payment);
        }
        if (!"P".equals(payment.getPaymentStatus())) {
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
                .anyMatch(det -> "PG".equals(det.getPayType()) && "P".equals(det.getPaymentStatus()) && det.getAmount() != null && det.getAmount() > 0);
        if (hasPgPayment) {
            portOnePaymentClient.cancelPayment(payment.getPaymentId(), request != null ? request.getReason() : null);
        }

        payment.setCancelAmount(payment.getTotalAmount());
        paymentDao.updatePaymentCancel(payment);

        for (PaymentDet det : details) {
            if (!"P".equals(det.getPaymentStatus())) continue;
            det.setCancelAmount(det.getAmount());
            paymentDao.updatePaymentDetCancel(det);
        }

        orderDao.updateOrderStatus(payment.getOrderId(), "C");
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
        Long cartId = request.getCartId();
        if (cartId == null) {
            Order order = orderDao.selectOrderById(payment.getOrderId());
            cartId = order != null ? order.getCartId() : null;
        }
        if (cartId == null || cartId <= 0) return;

        cartDao.deleteAllCartItems(cartId);
        cartDao.updateCartStatus(cartId, "ordered");
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
            det.setPaymentNo(paymentDet.getPaymentNo());
            det.setPayType(paymentDet.getPayType());
            det.setPayMethod(paymentDet.getPayMethod());
            det.setPaymentStatus(paymentDet.getPaymentStatus());
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
