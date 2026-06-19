package com.shop.api.biz.order.service;

import com.shop.api.frontWeb.service.PaymentService;
import com.shop.core.frontWeb.dao.OrderDao;
import com.shop.core.frontWeb.vo.request.PaymentRequest;
import com.shop.core.frontWeb.vo.response.OrderResponse;
import com.shop.core.frontWeb.vo.response.PaymentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderMngService {

    private final OrderDao orderDao;
    private final com.shop.api.frontWeb.service.OrderService foOrderService;
    private final PaymentService paymentService;

    public List<OrderResponse.BoListItem> getOrderListForBo(LocalDate fromDate, LocalDate toDate) {
        LocalDate normalizedFrom = fromDate != null ? fromDate : LocalDate.now();
        LocalDate normalizedTo   = toDate   != null ? toDate   : LocalDate.now();

        LocalDateTime fromDateTime = normalizedFrom.atStartOfDay();
        LocalDateTime toDateTime   = normalizedTo.plusDays(1).atStartOfDay();

        return orderDao.selectOrderListForBo(fromDateTime, toDateTime);
    }

    public OrderResponse.Info getOrderDetail(Long orderId) {
        return foOrderService.getOrder(orderId);
    }

    public PaymentResponse.Info cancelPayment(Long paymentSeq, PaymentRequest.Cancel request) {
        return paymentService.cancelPayment(paymentSeq, request);
    }
}
