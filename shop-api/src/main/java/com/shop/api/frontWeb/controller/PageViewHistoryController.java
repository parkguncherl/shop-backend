package com.shop.api.frontWeb.controller;

import com.shop.api.annotation.GuestUser;
import com.shop.api.frontWeb.service.PageViewHistoryService;
import com.shop.core.annotations.NotAuthRequired;
import com.shop.core.biz.system.vo.response.ApiResponse;
import com.shop.core.entity.GuestToken;
import com.shop.core.enums.ApiResultCode;
import com.shop.core.frontWeb.vo.response.PageViewHistoryResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/frontWeb/pageViewHistory")
@Tag(name = "PageViewHistoryController", description = "최근 본 상품 API")
@RequiredArgsConstructor
public class PageViewHistoryController {

    private final PageViewHistoryService pageViewHistoryService;

    @NotAuthRequired
    @GetMapping("/recentProducts")
    @Operation(summary = "최근 본 상품 목록 조회 (최대 10개)")
    public ApiResponse<List<PageViewHistoryResponse.RecentProduct>> getRecentProducts(
            @Parameter(hidden = true) @GuestUser GuestToken guestUser) {

        List<PageViewHistoryResponse.RecentProduct> result =
                pageViewHistoryService.getRecentProducts(guestUser.getGuestId());
        return new ApiResponse<>(ApiResultCode.SUCCESS, result);
    }
}
