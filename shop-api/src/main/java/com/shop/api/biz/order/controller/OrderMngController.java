package com.shop.api.biz.order.controller;

import com.shop.api.annotation.AccessLog;
import com.shop.api.annotation.JwtUser;
import com.shop.api.biz.order.service.OrderMngService;
import com.shop.core.biz.orderMng.vo.request.OrderMngRequest;
import com.shop.core.biz.orderMng.vo.response.OrderMngResponse;
import com.shop.core.biz.system.vo.response.ApiResponse;
import com.shop.core.entity.User;
import com.shop.core.enums.ApiResultCode;
import com.shop.core.frontWeb.vo.request.PaymentRequest;
import com.shop.core.frontWeb.vo.response.OrderResponse;
import com.shop.core.frontWeb.vo.response.PaymentResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orderMng")
@Tag(name = "OrderMngController", description = "BO 주문 관리 API")
@RequiredArgsConstructor
public class OrderMngController {

    private final OrderMngService orderMngService;

    @AccessLog("주문 목록 조회")
    @GetMapping("/list")
    @Operation(summary = "주문 목록 조회 (기간별)")
    public ApiResponse<List<OrderMngResponse.BoListItem>> getOrderList(
            @Parameter(hidden = true) @JwtUser User jwtUser,
            @ModelAttribute OrderMngRequest.ListFilter filter
    ) {
        return new ApiResponse<>(ApiResultCode.SUCCESS, orderMngService.getOrderListForBo(filter));
    }

    @AccessLog("주문 상세 조회")
    @GetMapping("/{orderId}")
    @Operation(summary = "주문 상세 조회 (BO)")
    public ApiResponse<OrderResponse.Info> getOrderDetail(
            @Parameter(hidden = true) @JwtUser User jwtUser,
            @Parameter(description = "주문 ID") @PathVariable Long orderId
    ) {
        return new ApiResponse<>(ApiResultCode.SUCCESS, orderMngService.getOrderDetail(orderId));
    }

    @AccessLog("주문 결제 취소")
    @PostMapping("/payment/{paymentSeq}/cancel")
    @Operation(summary = "결제 취소 (BO)")
    public ApiResponse<PaymentResponse.Info> cancelPayment(
            @Parameter(hidden = true) @JwtUser User jwtUser,
            @Parameter(description = "결제 SEQ") @PathVariable Long paymentSeq,
            @RequestBody(required = false) PaymentRequest.Cancel request
    ) {
        return new ApiResponse<>(ApiResultCode.SUCCESS, orderMngService.cancelPayment(paymentSeq, request));
    }
}
