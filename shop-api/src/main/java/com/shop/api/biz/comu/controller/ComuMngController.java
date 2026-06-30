package com.shop.api.biz.comu.controller;

import com.shop.api.annotation.AccessLog;
import com.shop.api.annotation.JwtUser;
import com.shop.api.frontWeb.service.ComuService;
import com.shop.core.biz.system.vo.response.ApiResponse;
import com.shop.core.entity.User;
import com.shop.core.enums.ApiResultCode;
import com.shop.core.frontWeb.vo.request.ComuRequest;
import com.shop.core.frontWeb.vo.response.ComuResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/comuMng")
@Tag(name = "ComuMngController", description = "BO 고객 문의 관리 API")
@RequiredArgsConstructor
public class ComuMngController {

    private final ComuService comuService;

    @AccessLog("고객 문의 목록 조회")
    @GetMapping("/list")
    @Operation(summary = "고객 문의 목록 조회 (BO)")
    public ApiResponse<List<ComuResponse.BoListItem>> getComuList(
            @Parameter(hidden = true) @JwtUser User jwtUser,
            @Parameter(description = "문의 유형 코드") @RequestParam(required = false) String comuType,
            @Parameter(description = "특이사항 여부 (Y만 조회 시 Y)") @RequestParam(required = false) String remarkYn,
            @Parameter(description = "상품명 검색") @RequestParam(required = false) String productName,
            @Parameter(description = "구매일 시작") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
            @Parameter(description = "구매일 종료") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate
    ) {
        return new ApiResponse<>(ApiResultCode.SUCCESS,
                comuService.getComuListForBo(comuType, remarkYn, productName, fromDate, toDate));
    }

    @AccessLog("고객 문의 스레드 조회")
    @GetMapping("/{comuId}/thread")
    @Operation(summary = "고객 문의 스레드 조회 + 읽음 처리 (BO)")
    public ApiResponse<ComuResponse.Thread> getThread(
            @Parameter(hidden = true) @JwtUser User jwtUser,
            @PathVariable Long comuId
    ) {
        comuService.markRead(comuId, true);
        return new ApiResponse<>(ApiResultCode.SUCCESS, comuService.getThread(comuId));
    }

    @AccessLog("관리자 메시지 삭제")
    @DeleteMapping("/message/{comuDetId}")
    @Operation(summary = "관리자 본인 메시지 삭제 (BO)")
    public ApiResponse<Void> deleteAdminMessage(
            @Parameter(hidden = true) @JwtUser User jwtUser,
            @PathVariable Long comuDetId
    ) {
        comuService.deleteAdminMessage(comuDetId, jwtUser.getLoginId());
        return new ApiResponse<>(ApiResultCode.SUCCESS, null);
    }

    @AccessLog("고객 문의 특이사항/메모 수정")
    @PatchMapping("/{comuId}/remark")
    @Operation(summary = "특이사항 체크 및 관리자 메모 수정 (BO)")
    public ApiResponse<Void> updateRemark(
            @Parameter(hidden = true) @JwtUser User jwtUser,
            @PathVariable Long comuId,
            @RequestBody ComuRequest.UpdateRemark request
    ) {
        comuService.updateRemark(comuId, request.getRemarkYn(), request.getComment());
        return new ApiResponse<>(ApiResultCode.SUCCESS, null);
    }

    @AccessLog("고객 문의 답변 등록")
    @PostMapping("/{comuId}/reply")
    @Operation(summary = "관리자 답변 등록 (BO)")
    public ApiResponse<ComuResponse.Thread> reply(
            @Parameter(hidden = true) @JwtUser User jwtUser,
            @PathVariable Long comuId,
            @RequestBody ComuRequest.AdminReply request
    ) {
        return new ApiResponse<>(ApiResultCode.SUCCESS,
                comuService.addAdminReply(comuId, request.getContent(), request.getFileId()));
    }
}
