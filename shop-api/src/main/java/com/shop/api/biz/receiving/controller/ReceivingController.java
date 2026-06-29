package com.shop.api.biz.receiving.controller;

import com.shop.api.annotation.AccessLog;
import com.shop.api.annotation.JwtUser;
import com.shop.api.biz.receiving.service.ReceivingService;
import com.shop.core.biz.receiving.vo.request.ReceivingRequest;
import com.shop.core.biz.receiving.vo.response.ReceivingResponse;
import com.shop.core.biz.system.vo.response.ApiResponse;
import com.shop.core.entity.User;
import com.shop.core.enums.ApiResultCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/receiving")
@Tag(name = "ReceivingController", description = "입고/출고 관리 API")
@RequiredArgsConstructor
public class ReceivingController {

    private final ReceivingService receivingService;

    @AccessLog("입고/출고 목록 조회")
    @GetMapping("/receivingList")
    @Operation(summary = "입고/출고 목록 조회")
    public ApiResponse<List<ReceivingResponse.ReceivingItem>> getReceivingList(
            @Parameter(hidden = true) @JwtUser User jwtUser,
            @ModelAttribute ReceivingRequest.ListFilter filter
    ) {
        filter.setPartnerId(jwtUser.getPartnerId());
        return new ApiResponse<>(ApiResultCode.SUCCESS, receivingService.getReceivingList(filter));
    }

    @AccessLog("입고용 상품상세 검색")
    @GetMapping("/productDetSearchList")
    @Operation(summary = "입고용 상품상세 검색 (상품명으로 검색)")
    public ApiResponse<List<ReceivingResponse.ProductDetSearchItem>> getProductDetSearchList(
            @Parameter(hidden = true) @JwtUser User jwtUser,
            @ModelAttribute ReceivingRequest.ProductDetSearchFilter filter
    ) {
        filter.setPartnerId(jwtUser.getPartnerId());
        return new ApiResponse<>(ApiResultCode.SUCCESS, receivingService.getProductDetSearchList(filter));
    }

    @AccessLog("입고/출고 등록")
    @PutMapping("/insertReceiving")
    @Operation(summary = "입고/출고 등록")
    public ApiResponse<Void> insertReceiving(
            @Parameter(hidden = true) @JwtUser User jwtUser,
            @RequestBody ReceivingRequest.InsertReceiving request
    ) {
        request.setCreUser(jwtUser.getUserNm());
        receivingService.insertReceiving(request);
        return new ApiResponse<>(ApiResultCode.SUCCESS);
    }

    @AccessLog("입고/출고 수정")
    @PatchMapping("/updateReceiving")
    @Operation(summary = "입고/출고 수정")
    public ApiResponse<Void> updateReceiving(
            @Parameter(hidden = true) @JwtUser User jwtUser,
            @RequestBody ReceivingRequest.UpdateReceiving request
    ) {
        request.setUpdUser(jwtUser.getUserNm());
        receivingService.updateReceiving(request);
        return new ApiResponse<>(ApiResultCode.SUCCESS);
    }

    @AccessLog("입고/출고 삭제")
    @DeleteMapping("/deleteReceiving")
    @Operation(summary = "입고/출고 삭제")
    public ApiResponse<Void> deleteReceiving(
            @Parameter(hidden = true) @JwtUser User jwtUser,
            @RequestBody ReceivingRequest.DeleteReceiving request
    ) {
        request.setUpdUser(jwtUser.getUserNm());
        receivingService.deleteReceiving(request);
        return new ApiResponse<>(ApiResultCode.SUCCESS);
    }
}
