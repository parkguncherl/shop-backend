package com.shop.api.frontWeb.controller;

import com.shop.api.frontWeb.service.CheckoutService;
import com.shop.core.annotations.NotAuthRequired;
import com.shop.core.biz.system.vo.response.ApiResponse;
import com.shop.core.frontWeb.vo.request.CheckoutRequest;
import com.shop.core.frontWeb.vo.response.CheckoutResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/frontWeb/checkout")
@Tag(name = "CheckoutController", description = "FO 통합 주문/결제 API")
@RequiredArgsConstructor
public class CheckoutController {

    private final CheckoutService checkoutService;

    @NotAuthRequired
    @PostMapping
    @Operation(summary = "통합 주문/결제 처리")
    public ApiResponse<CheckoutResponse.Info> checkout(@RequestBody CheckoutRequest.Create request) {
        return new ApiResponse<>(checkoutService.checkout(request));
    }
}
