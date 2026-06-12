package com.shop.api.frontWeb.controller;

import com.shop.api.frontWeb.service.OrderService;
import com.shop.core.annotations.NotAuthRequired;
import com.shop.core.biz.system.vo.response.ApiResponse;
import com.shop.core.frontWeb.vo.request.OrderRequest;
import com.shop.core.frontWeb.vo.response.OrderResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/frontWeb/order")
@Tag(name = "OrderController", description = "FO order API")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @NotAuthRequired
    @PostMapping
    @Operation(summary = "Create order")
    public ApiResponse<OrderResponse.Info> createOrder(@RequestBody OrderRequest.Create request) {
        return new ApiResponse<>(orderService.createOrder(request));
    }

    @NotAuthRequired
    @GetMapping("/{orderId}")
    @Operation(summary = "Get order")
    public ApiResponse<OrderResponse.Info> getOrder(
            @Parameter(description = "Order ID") @PathVariable Long orderId) {
        return new ApiResponse<>(orderService.getOrder(orderId));
    }

    @NotAuthRequired
    @GetMapping("/list")
    @Operation(summary = "Get order list")
    public ApiResponse<List<OrderResponse.Info>> getOrders(
            @Parameter(description = "Social account ID") @RequestParam Long socialAccountId) {
        return new ApiResponse<>(orderService.getOrders(socialAccountId));
    }
}
