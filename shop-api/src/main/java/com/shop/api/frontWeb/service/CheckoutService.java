package com.shop.api.frontWeb.service;

import com.shop.core.entity.*;
import com.shop.core.enums.OrderStatus;
import com.shop.core.enums.PaymentStatus;
import com.shop.core.enums.PointType;
import com.shop.core.frontWeb.dao.CartDao;
import com.shop.core.frontWeb.dao.OrderDao;
import com.shop.core.frontWeb.dao.PaymentDao;
import com.shop.core.frontWeb.dao.PointDao;
import com.shop.core.frontWeb.vo.request.CheckoutRequest;
import com.shop.core.frontWeb.vo.request.OrderRequest;
import com.shop.core.frontWeb.vo.request.PaymentRequest;
import com.shop.core.frontWeb.vo.response.CheckoutResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CheckoutService {

    private final OrderDao orderDao;
    private final PaymentDao paymentDao;
    private final PointDao pointDao;
    private final CartDao cartDao;

    @Transactional
    public CheckoutResponse.Info checkout(CheckoutRequest.Create request) {

        // ── 1. 주문 생성 ──
        Order order = Order.builder()
                .orderNo(request.getOrderNo())
                .socialAccountId(request.getSocialAccountId())
                .orderStatus(OrderStatus.ORDER.getCode())
                .productAmount(request.getProductAmount())
                .discountAmount(request.getDiscountAmount())
                .usedPoint(request.getUsedPoint())
                .paymentAmount(request.getPaymentAmount())
                .earnedPoint(request.getEarnedPoint())
                .receiverName(request.getReceiverName())
                .receiverPhone(request.getReceiverPhone())
                .zipCode(request.getZipCode())
                .address(request.getAddress())
                .addressDetail(request.getAddressDetail())
                .memo(request.getMemo())
                .build();
        orderDao.insertOrder(order);

        // ── 2. 주문 아이템 생성 ──
        if (request.getItems() != null) {
            for (OrderRequest.Item requestItem : request.getItems()) {
                OrderItem item = OrderItem.builder()
                        .orderId(order.getId())
                        .cartId(requestItem.getCartId())
                        .productId(requestItem.getProductId())
                        .productDetId(requestItem.getProductDetId())
                        .productName(requestItem.getProductName())
                        .productImage(requestItem.getProductImage())
                        .optionName(requestItem.getOptionName())
                        .quantity(requestItem.getQuantity())
                        .unitPrice(requestItem.getUnitPrice())
                        .discountAmount(requestItem.getDiscountAmount())
                        .paymentAmount(requestItem.getPaymentAmount())
                        .build();
                orderDao.insertOrderItem(item);
            }
        }

        // ── 3. 배송지 생성 ──
        OrderDelivery delivery = OrderDelivery.builder()
                .orderId(order.getId())
                .orderNo(order.getOrderNo())
                .socialAccountId(order.getSocialAccountId())
                .receiverName(order.getReceiverName())
                .receiverPhone(order.getReceiverPhone())
                .zipCode(order.getZipCode())
                .address(order.getAddress())
                .addressDetail(order.getAddressDetail())
                .memo(order.getMemo())
                .deliveryStatus("READY")
                .delYn("N")
                .build();
        orderDao.insertOrderDelivery(delivery);

        // ── 4. 결제 생성 ──
        Payment payment = Payment.builder()
                .orderId(order.getId())
                .orderNo(order.getOrderNo())
                .paymentId(request.getPaymentId())
                .paymentStatus(PaymentStatus.PAID.getCode())
                .totalAmount(request.getTotalAmount())
                .currency(request.getCurrency() != null ? request.getCurrency() : "KRW")
                .cancelAmount(0L)
                .delYn("N")
                .build();
        paymentDao.insertPayment(payment);

        // ── 5. 결제 상세 생성 ──
        if (request.getDetails() != null) {
            for (PaymentRequest.CreateDet det : request.getDetails()) {
                PaymentDet paymentDet = PaymentDet.builder()
                        .paymentId(payment.getId())
                        .payType(det.getPayType())
                        .payMethod(det.getPayMethod())
                        .amount(det.getAmount())
                        .pgProvider(det.getPgProvider())
                        .pgTid(det.getPgTid())
                        .portonePaymentId(det.getPortonePaymentId())
                        .rawResponse(det.getRawResponse())
                        .cancelAmount(0L)
                        .delYn("N")
                        .build();
                paymentDao.insertPaymentDet(paymentDet);
            }
        }

        // ── 6. 주문 상태 → 결제완료 ──
        orderDao.updateOrderStatus(order.getId(), OrderStatus.PAID.getCode());

        // ── 7. 포인트 처리 ──
        if (order.getUsedPoint() != null && order.getUsedPoint() > 0) {
            pointDao.insertPointHistory(PointHistory.builder()
                    .socialAccountId(order.getSocialAccountId())
                    .orderId(order.getId())
                    .pointType(PointType.USE)
                    .pointAmount(-order.getUsedPoint())
                    .description("주문 " + order.getOrderNo() + " 포인트 사용")
                    .build());
        }
        if (order.getEarnedPoint() != null && order.getEarnedPoint() > 0) {
            pointDao.insertPointHistory(PointHistory.builder()
                    .socialAccountId(order.getSocialAccountId())
                    .orderId(order.getId())
                    .pointType(PointType.EARN)
                    .pointAmount(order.getEarnedPoint())
                    .description("주문 " + order.getOrderNo() + " 구매 적립")
                    .build());
        }

        // ── 8. 장바구니 주문완료 처리 ──
        if (order.getSocialAccountId() != null) {
            int updated = cartDao.markOrderedBySocialAccountId(order.getSocialAccountId());
            log.info("[checkout] socialAccountId={} cart updated={}", order.getSocialAccountId(), updated);
        }

        // ── 응답 ──
        CheckoutResponse.Info response = new CheckoutResponse.Info();
        response.setOrderId(order.getId());
        response.setOrderNo(order.getOrderNo());
        response.setPaymentSeq(payment.getId());
        response.setPaymentId(payment.getPaymentId());
        response.setPaymentStatus(payment.getPaymentStatus());
        response.setTotalAmount(payment.getTotalAmount());
        response.setCurrency(payment.getCurrency());
        return response;
    }
}
