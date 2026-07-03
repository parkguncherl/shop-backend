package com.shop.api.biz.notice.controller;

import com.shop.api.annotation.AccessLog;
import com.shop.api.annotation.JwtUser;
import com.shop.core.biz.notice.dao.NoticeDao;
import com.shop.core.biz.notice.vo.response.NoticeResponse;
import com.shop.core.biz.system.vo.response.ApiResponse;
import com.shop.core.entity.Notice;
import com.shop.core.entity.User;
import com.shop.core.enums.ApiResultCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/popupNoticeMng")
@Tag(name = "PopupNoticeMngController", description = "팝업 공지사항 관리 API")
@RequiredArgsConstructor
public class PopupNoticeMngController {

    private final NoticeDao noticeDao;

    @AccessLog("팝업 공지사항 목록 조회")
    @GetMapping("/list")
    @Operation(summary = "팝업 공지사항 목록 조회")
    public ApiResponse<List<NoticeResponse.PopupNotice>> getList(
            @Parameter(hidden = true) @JwtUser User jwtUser
    ) {
        return new ApiResponse<>(ApiResultCode.SUCCESS,
                noticeDao.selectPopupNoticeMngList(jwtUser.getPartnerId())
                        .stream().map(NoticeResponse.PopupNotice::fromEntity).collect(Collectors.toList()));
    }

    @AccessLog("팝업 공지사항 단건 조회")
    @GetMapping("/{id}")
    @Operation(summary = "팝업 공지사항 단건 조회")
    public ApiResponse<NoticeResponse.PopupNotice> getOne(
            @Parameter(hidden = true) @JwtUser User jwtUser,
            @PathVariable Integer id
    ) {
        return new ApiResponse<>(ApiResultCode.SUCCESS,
                NoticeResponse.PopupNotice.fromEntity(noticeDao.selectPopupNoticeById(id)));
    }

    @AccessLog("팝업 공지사항 등록")
    @PostMapping("/create")
    @Operation(summary = "팝업 공지사항 등록")
    public ApiResponse<Void> create(
            @Parameter(hidden = true) @JwtUser User jwtUser,
            @RequestBody Notice notice
    ) {
        notice.setPartnerId(jwtUser.getPartnerId());
        notice.setCreUser(jwtUser.getLoginId());
        notice.setUpdUser(jwtUser.getLoginId());
        noticeDao.insertPopupNotice(notice);
        return new ApiResponse<>(ApiResultCode.SUCCESS, null);
    }

    @AccessLog("팝업 공지사항 수정")
    @PutMapping("/update")
    @Operation(summary = "팝업 공지사항 수정")
    public ApiResponse<Void> update(
            @Parameter(hidden = true) @JwtUser User jwtUser,
            @RequestBody Notice notice
    ) {
        notice.setUpdUser(jwtUser.getLoginId());
        noticeDao.updatePopupNotice(notice);
        return new ApiResponse<>(ApiResultCode.SUCCESS, null);
    }

    @AccessLog("팝업 공지사항 게시 여부 변경")
    @PatchMapping("/{id}/gesiYn")
    @Operation(summary = "팝업 공지사항 게시 여부 변경")
    public ApiResponse<Void> updateGesiYn(
            @Parameter(hidden = true) @JwtUser User jwtUser,
            @PathVariable Integer id,
            @RequestBody Map<String, String> body
    ) {
        noticeDao.updatePopupNoticeGesiYn(id, body.get("gesiYn"), jwtUser.getLoginId());
        return new ApiResponse<>(ApiResultCode.SUCCESS, null);
    }

    @AccessLog("팝업 공지사항 삭제")
    @DeleteMapping("/{id}")
    @Operation(summary = "팝업 공지사항 삭제")
    public ApiResponse<Void> delete(
            @Parameter(hidden = true) @JwtUser User jwtUser,
            @PathVariable Integer id
    ) {
        noticeDao.deletePopupNotice(id, jwtUser.getLoginId());
        return new ApiResponse<>(ApiResultCode.SUCCESS, null);
    }
}
