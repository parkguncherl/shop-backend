package com.shop.api.frontWeb.controller;

import com.shop.api.frontWeb.service.PageViewLogService;
import com.shop.core.annotations.NotAuthRequired;
import com.shop.core.biz.system.vo.response.ApiResponse;
import com.shop.core.enums.ApiResultCode;
import com.shop.core.frontWeb.vo.request.PageViewLogRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/frontWeb/log")
@Tag(name = "PageViewLogController", description = "페이지 뷰 로그 API")
@RequiredArgsConstructor
public class PageViewLogController {

    private final PageViewLogService pageViewLogService;

    @NotAuthRequired
    @PostMapping("/pageView")
    @Operation(summary = "페이지 뷰 로그 저장")
    public ApiResponse<Void> insertPageViewLog(
            @RequestBody PageViewLogRequest.Save  request,
            HttpServletRequest httpRequest) {

        String guestId = (String) httpRequest.getAttribute("GUEST_ID");
        request.setGuestId(guestId);
        pageViewLogService.insertPageViewLog(request);
        return new ApiResponse<>(ApiResultCode.SUCCESS, null);
    }
}