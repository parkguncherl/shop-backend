package com.shop.api.frontWeb.controller;

import com.shop.api.frontWeb.service.DeliveryAddressService;
import com.shop.core.annotations.NotAuthRequired;
import com.shop.core.biz.system.vo.response.ApiResponse;
import com.shop.core.frontWeb.vo.request.DeliveryAddressRequest;
import com.shop.core.frontWeb.vo.response.DeliveryAddressResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/frontWeb/delivery-address")
@Tag(name = "DeliveryAddressController", description = "FO 배송지 주소록 API")
@RequiredArgsConstructor
public class DeliveryAddressController {

    private final DeliveryAddressService deliveryAddressService;

    @NotAuthRequired
    @GetMapping("/list")
    @Operation(summary = "배송지 목록 조회")
    public ApiResponse<List<DeliveryAddressResponse.Info>> getList(
            @Parameter(description = "Social account ID") @RequestParam Long socialAccountId) {
        return new ApiResponse<>(deliveryAddressService.getList(socialAccountId));
    }

    @NotAuthRequired
    @PostMapping
    @Operation(summary = "배송지 저장")
    public ApiResponse<DeliveryAddressResponse.Info> save(
            @RequestBody DeliveryAddressRequest.Save request) {
        return new ApiResponse<>(deliveryAddressService.save(request));
    }

    @NotAuthRequired
    @PutMapping
    @Operation(summary = "배송지 수정")
    public ApiResponse<DeliveryAddressResponse.Info> update(
            @RequestBody DeliveryAddressRequest.Update request) {
        return new ApiResponse<>(deliveryAddressService.update(request));
    }

    @NotAuthRequired
    @DeleteMapping("/{id}")
    @Operation(summary = "배송지 삭제")
    public ApiResponse<Void> delete(
            @Parameter(description = "배송지 ID") @PathVariable Long id) {
        deliveryAddressService.delete(id);
        return new ApiResponse<>(null);
    }
}
