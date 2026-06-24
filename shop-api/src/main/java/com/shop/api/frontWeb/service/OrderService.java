package com.shop.api.frontWeb.service;

import com.shop.core.entity.Order;
import com.shop.core.entity.OrderDelivery;
import com.shop.core.entity.OrderItem;
import com.shop.core.entity.PointHistory;
import com.shop.core.enums.DeliveryStatus;
import com.shop.core.enums.OrderStatus;
import com.shop.core.enums.PointType;
import com.shop.core.frontWeb.dao.OrderDao;
import com.shop.core.frontWeb.dao.PointDao;
import com.shop.core.frontWeb.vo.request.OrderRequest;
import com.shop.core.frontWeb.vo.response.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderDao orderDao;
    private final PointDao pointDao;

    @Transactional
    public OrderResponse.Info createOrder(OrderRequest.Create request) {
        Order order = Order.builder()
                .orderNo(request.getOrderNo())
                .partnerId(request.getPartnerId())
                .socialAccountId(request.getSocialAccountId())
                .orderStatus(OrderStatus.ORDER.getCode())
                .productAmount(request.getProductAmount())
                .discountAmount(request.getDiscountAmount())
                .usedPoint(request.getUsedPoint())
                .paymentAmount(request.getPaymentAmount())
                .earnedPoint(request.getEarnedPoint())
                .receiverName(getReceiverName(request))
                .receiverPhone(getReceiverPhone(request))
                .zipCode(getZipCode(request))
                .address(getAddress(request))
                .addressDetail(getAddressDetail(request))
                .memo(getMemo(request))
                .build();
        orderDao.insertOrder(order);

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
                .deliveryStatus(DeliveryStatus.READY.getCode())
                .delYn("N")
                .build();
        orderDao.insertOrderDelivery(delivery);

        // 포인트 사용 이력 (usedPoint > 0 일 때만)
        if (order.getUsedPoint() != null && order.getUsedPoint() > 0) {
            pointDao.insertPointHistory(PointHistory.builder()
                    .socialAccountId(order.getSocialAccountId())
                    .orderId(order.getId())
                    .pointType(PointType.USE)
                    .pointAmount(-order.getUsedPoint())
                    .description("주문 " + order.getOrderNo() + " 포인트 사용")
                    .build());
        }

        // 포인트 적립 이력 (earnedPoint > 0 일 때만)
        if (order.getEarnedPoint() != null && order.getEarnedPoint() > 0) {
            pointDao.insertPointHistory(PointHistory.builder()
                    .socialAccountId(order.getSocialAccountId())
                    .orderId(order.getId())
                    .pointType(PointType.EARN)
                    .pointAmount(order.getEarnedPoint())
                    .description("주문 " + order.getOrderNo() + " 구매 적립")
                    .build());
        }

        // getId()가 null인 경우(useGeneratedKeys 미설정 환경) orderNo로 폴백 조회
        if (order.getId() != null) {
            return getOrder(order.getId());
        }
        Order created = orderDao.selectOrderByOrderNo(order.getOrderNo());
        return created != null ? toInfo(created) : null;
    }

    public OrderResponse.Info getOrder(Long orderId) {
        Order order = orderDao.selectOrderById(orderId);
        if (order == null) return null;
        return toInfo(order);
    }

    public List<OrderResponse.Info> getOrders(Long socialAccountId) {
        List<OrderResponse.Info> result = new ArrayList<>();
        for (Order order : orderDao.selectOrdersBySocialAccountId(socialAccountId)) {
            result.add(toInfo(order));
        }
        return result;
    }

    private OrderResponse.Info toInfo(Order order) {
        OrderResponse.Info info = new OrderResponse.Info();
        info.setOrderId(order.getId());
        info.setOrderNo(order.getOrderNo());
        info.setSocialAccountId(order.getSocialAccountId());
        info.setOrderStatus(order.getOrderStatus());
        info.setProductAmount(order.getProductAmount());
        info.setDiscountAmount(order.getDiscountAmount());
        info.setUsedPoint(order.getUsedPoint());
        info.setPaymentAmount(order.getPaymentAmount());
        info.setEarnedPoint(order.getEarnedPoint());
        info.setCreTm(order.getCreTm());
        info.setDelivery(toDelivery(orderDao.selectOrderDelivery(order.getId())));

        List<OrderResponse.Item> items = new ArrayList<>();
        for (OrderItem orderItem : orderDao.selectOrderItems(order.getId())) {
            OrderResponse.Item item = new OrderResponse.Item();
            item.setOrderItemId(orderItem.getId());
            item.setProductId(orderItem.getProductId());
            item.setProductDetId(orderItem.getProductDetId());
            item.setProductName(orderItem.getProductName());
            item.setProductImage(orderItem.getProductImage());
            item.setOptionName(orderItem.getOptionName());
            item.setQuantity(orderItem.getQuantity());
            item.setUnitPrice(orderItem.getUnitPrice());
            item.setDiscountAmount(orderItem.getDiscountAmount());
            item.setPaymentAmount(orderItem.getPaymentAmount());
            items.add(item);
        }
        info.setItems(items);
        return info;
    }

    private OrderResponse.Delivery toDelivery(OrderDelivery delivery) {
        if (delivery == null) return null;
        OrderResponse.Delivery response = new OrderResponse.Delivery();
        response.setDeliveryId(delivery.getId());
        response.setReceiverName(delivery.getReceiverName());
        response.setReceiverPhone(delivery.getReceiverPhone());
        response.setZipCode(delivery.getZipCode());
        response.setAddress(delivery.getAddress());
        response.setAddressDetail(delivery.getAddressDetail());
        response.setMemo(delivery.getMemo());
        response.setDeliveryStatus(delivery.getDeliveryStatus());
        response.setDeliveryCompany(delivery.getDeliveryCompany());
        response.setInvoiceNo(delivery.getInvoiceNo());
        response.setShippedTm(delivery.getShippedTm());
        response.setDeliveredTm(delivery.getDeliveredTm());
        return response;
    }

    private String getReceiverName(OrderRequest.Create request) {
        return request.getDelivery() != null ? request.getDelivery().getReceiverName() : request.getReceiverName();
    }

    private String getReceiverPhone(OrderRequest.Create request) {
        return request.getDelivery() != null ? request.getDelivery().getReceiverPhone() : request.getReceiverPhone();
    }

    private String getZipCode(OrderRequest.Create request) {
        return request.getDelivery() != null ? request.getDelivery().getZipCode() : request.getZipCode();
    }

    private String getAddress(OrderRequest.Create request) {
        return request.getDelivery() != null ? request.getDelivery().getAddress() : request.getAddress();
    }

    private String getAddressDetail(OrderRequest.Create request) {
        return request.getDelivery() != null ? request.getDelivery().getAddressDetail() : request.getAddressDetail();
    }

    private String getMemo(OrderRequest.Create request) {
        return request.getDelivery() != null ? request.getDelivery().getMemo() : request.getMemo();
    }
}
