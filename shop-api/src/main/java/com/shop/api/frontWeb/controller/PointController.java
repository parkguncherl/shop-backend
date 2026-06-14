package com.shop.api.frontWeb.controller;

import com.shop.api.frontWeb.service.PointService;
import com.shop.core.annotations.NotAuthRequired;
import com.shop.core.biz.system.vo.response.ApiResponse;
import com.shop.core.frontWeb.vo.response.PointResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/frontWeb/point")
@Tag(name = "PointController", description = "FO point API")
@RequiredArgsConstructor
public class PointController {

    private final PointService pointService;

    @NotAuthRequired
    @GetMapping("/balance")
    @Operation(summary = "Get point balance")
    public ApiResponse<PointResponse.Balance> getBalance(
            @Parameter(description = "Social account ID") @RequestParam Long socialAccountId) {
        return new ApiResponse<>(pointService.getBalance(socialAccountId));
    }

    @NotAuthRequired
    @GetMapping("/histories")
    @Operation(summary = "Get point histories")
    public ApiResponse<PointResponse.HistoryList> getHistories(
            @Parameter(description = "Social account ID") @RequestParam Long socialAccountId) {
        return new ApiResponse<>(pointService.getHistories(socialAccountId));
    }
}
