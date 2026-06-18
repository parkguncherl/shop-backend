package com.shop.api.frontWeb.controller;

import com.shop.api.frontWeb.service.ComuService;
import com.shop.core.annotations.NotAuthRequired;
import com.shop.core.biz.system.vo.response.ApiResponse;
import com.shop.core.frontWeb.vo.request.ComuRequest;
import com.shop.core.frontWeb.vo.response.ComuResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/frontWeb/comu")
@Tag(name = "ComuController", description = "FO 고객 상담 API")
@RequiredArgsConstructor
public class ComuController {

    private final ComuService comuService;

    @NotAuthRequired
    @PostMapping
    @Operation(summary = "상담 시작 (첫 메시지 포함)")
    public ApiResponse<ComuResponse.Thread> createComu(
            @RequestBody ComuRequest.Create request) {
        return new ApiResponse<>(comuService.createComu(request));
    }

    @NotAuthRequired
    @PostMapping("/{comuId}/message")
    @Operation(summary = "메시지 추가")
    public ApiResponse<ComuResponse.Thread> addMessage(
            @PathVariable Long comuId,
            @RequestBody ComuRequest.AddMessage request) {
        return new ApiResponse<>(comuService.addMessage(comuId, request));
    }

    @NotAuthRequired
    @DeleteMapping("/message/{comuDetId}")
    @Operation(summary = "메시지 삭제 (고객 본인 메시지만)")
    public ApiResponse<Void> deleteMessage(
            @PathVariable Long comuDetId,
            @Parameter(description = "소셜 계정 ID") @RequestParam Long socialAccountId) {
        comuService.deleteMessage(comuDetId, socialAccountId);
        return new ApiResponse<>();
    }

    @NotAuthRequired
    @GetMapping("/{comuId}")
    @Operation(summary = "상담 스레드 조회 (메시지 목록 포함)")
    public ApiResponse<ComuResponse.Thread> getThread(@PathVariable Long comuId) {
        return new ApiResponse<>(comuService.getThread(comuId));
    }

    @NotAuthRequired
    @GetMapping("/order/{orderId}")
    @Operation(summary = "주문별 상담 목록 조회")
    public ApiResponse<List<ComuResponse.Summary>> getComuListByOrderId(@PathVariable Long orderId) {
        return new ApiResponse<>(comuService.getComuListByOrderId(orderId));
    }

    @NotAuthRequired
    @DeleteMapping("/{comuId}")
    @Operation(summary = "상담 삭제 (고객 메시지 없이 닫을 때)")
    public ApiResponse<Void> deleteComu(@PathVariable Long comuId) {
        comuService.deleteComu(comuId);
        return new ApiResponse<>();
    }
}
