package com.shop.api.biz.order.service;

import com.shop.api.frontWeb.service.PaymentService;
import com.shop.core.biz.orderMng.dao.OrderMngDao;
import com.shop.core.biz.orderMng.vo.request.OrderMngRequest;
import com.shop.core.biz.orderMng.vo.response.OrderMngResponse;
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

    private final OrderMngDao orderMngDao;
    private final com.shop.api.frontWeb.service.OrderService foOrderService;
    private final PaymentService paymentService;

    public List<OrderMngResponse.BoListItem> getOrderListForBo(OrderMngRequest.ListFilter filter) {
        LocalDate fromDate = filter.getFromDate() != null ? filter.getFromDate() : LocalDate.now();
        LocalDate toDate   = filter.getToDate()   != null ? filter.getToDate()   : LocalDate.now();

        LocalDateTime fromDateTime = fromDate.atStartOfDay();
        LocalDateTime toDateTime   = toDate.plusDays(1).atStartOfDay();

        return orderMngDao.selectOrderListForBo(fromDateTime, toDateTime);
    }

    public OrderResponse.Info getOrderDetail(Long orderId) {
        return foOrderService.getOrder(orderId);
    }

    public PaymentResponse.Info cancelPayment(Long paymentSeq, PaymentRequest.Cancel request) {
        return paymentService.cancelPayment(paymentSeq, request);
    }
}
