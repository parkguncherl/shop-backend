package com.shop.api.biz.order.service;

import com.shop.api.frontWeb.service.PaymentService;
import com.shop.core.biz.orderMng.dao.OrderMngDao;
import com.shop.core.biz.orderMng.vo.request.OrderMngRequest;
import com.shop.core.biz.orderMng.vo.response.OrderMngResponse;
import com.shop.core.entity.OrderDelivery;
import com.shop.core.entity.OrderItem;
import com.shop.core.frontWeb.dao.OrderDao;
import com.shop.core.frontWeb.vo.request.PaymentRequest;
import com.shop.core.frontWeb.vo.response.OrderResponse;
import com.shop.core.frontWeb.vo.response.PaymentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderMngService {

    private final OrderMngDao orderMngDao;
    private final OrderDao orderDao;
    private final PaymentService paymentService;

    public List<OrderMngResponse.BoListItem> getOrderListForBo(OrderMngRequest.ListFilter filter) {
        LocalDate fromDate = filter.getFromDate() != null ? filter.getFromDate() : LocalDate.now();
        LocalDate toDate   = filter.getToDate()   != null ? filter.getToDate()   : LocalDate.now();

        LocalDateTime fromDateTime = fromDate.atStartOfDay();
        LocalDateTime toDateTime   = toDate.plusDays(1).atStartOfDay();

        return orderMngDao.selectOrderListForBo(fromDateTime, toDateTime);
    }

    public OrderResponse.Info getOrderDetail(Long orderId) {
        OrderResponse.Info info = orderMngDao.selectOrderDetailForBo(orderId);
        if (info == null) return null;

        // delivery
        OrderDelivery delivery = orderDao.selectOrderDelivery(orderId);
        if (delivery != null) {
            OrderResponse.Delivery d = new OrderResponse.Delivery();
            d.setDeliveryId(delivery.getId());
            d.setReceiverName(delivery.getReceiverName());
            d.setReceiverPhone(delivery.getReceiverPhone());
            d.setZipCode(delivery.getZipCode());
            d.setAddress(delivery.getAddress());
            d.setAddressDetail(delivery.getAddressDetail());
            d.setMemo(delivery.getMemo());
            d.setDeliveryStatus(delivery.getDeliveryStatus());
            d.setDeliveryCompany(delivery.getDeliveryCompany());
            d.setInvoiceNo(delivery.getInvoiceNo());
            d.setShippedTm(delivery.getShippedTm());
            d.setDeliveredTm(delivery.getDeliveredTm());
            info.setDelivery(d);
        }

        // items
        List<OrderResponse.Item> items = new ArrayList<>();
        for (OrderItem oi : orderDao.selectOrderItems(orderId)) {
            OrderResponse.Item item = new OrderResponse.Item();
            item.setOrderItemId(oi.getId());
            item.setProductId(oi.getProductId());
            item.setProductDetId(oi.getProductDetId());
            item.setProductName(oi.getProductName());
            item.setProductImage(oi.getProductImage());
            item.setOptionName(oi.getOptionName());
            item.setQuantity(oi.getQuantity());
            item.setUnitPrice(oi.getUnitPrice());
            item.setDiscountAmount(oi.getDiscountAmount());
            item.setPaymentAmount(oi.getPaymentAmount());
            items.add(item);
        }
        info.setItems(items);

        return info;
    }

    public PaymentResponse.Info cancelPayment(Long paymentSeq, PaymentRequest.Cancel request) {
        return paymentService.cancelPayment(paymentSeq, request);
    }
}
