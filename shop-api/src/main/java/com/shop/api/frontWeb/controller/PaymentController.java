package com.shop.api.frontWeb.controller;

import com.shop.api.frontWeb.service.PaymentService;
import com.shop.core.annotations.NotAuthRequired;
import com.shop.core.biz.system.vo.response.ApiResponse;
import com.shop.core.frontWeb.vo.request.PaymentRequest;
import com.shop.core.frontWeb.vo.response.PaymentResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/frontWeb/payment")
@Tag(name = "PaymentController", description = "FO payment API")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @NotAuthRequired
    @PostMapping
    @Operation(summary = "Create payment")
    public ApiResponse<PaymentResponse.Info> createPayment(@RequestBody PaymentRequest.Create request) {
        return new ApiResponse<>(paymentService.createPayment(request));
    }

    @NotAuthRequired
    @GetMapping("/order/{orderNo}")
    @Operation(summary = "Get payment by order number")
    public ApiResponse<PaymentResponse.Info> getPaymentByOrderNo(
            @Parameter(description = "Order number") @PathVariable String orderNo) {
        return new ApiResponse<>(paymentService.getPaymentByOrderNo(orderNo));
    }

    @NotAuthRequired
    @GetMapping("/list")
    @Operation(summary = "Get payment list")
    public ApiResponse<List<PaymentResponse.ListItem>> getPaymentList(
            @Parameter(description = "Social account ID") @RequestParam Long socialAccountId) {
        return new ApiResponse<>(paymentService.getPaymentList(socialAccountId));
    }
}
