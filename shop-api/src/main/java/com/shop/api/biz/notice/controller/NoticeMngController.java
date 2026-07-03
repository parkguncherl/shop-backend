package com.shop.api.biz.notice.controller;

import com.shop.api.annotation.AccessLog;
import com.shop.api.annotation.JwtUser;
import com.shop.core.biz.common.vo.request.PageRequest;
import com.shop.core.biz.common.vo.response.PageResponse;
import com.shop.core.biz.notice.dao.NoticeDao;
import com.shop.core.biz.notice.vo.request.NoticeRequest;
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

@RestController
@RequestMapping("/noticeMng")
@Tag(name = "NoticeMngController", description = "공지사항 관리 API")
@RequiredArgsConstructor
public class NoticeMngController {

    private final NoticeDao noticeDao;

    @AccessLog("공지사항 목록 조회")
    @GetMapping("/list")
    @Operation(summary = "공지사항 목록 조회 (페이징)")
    public ApiResponse<PageResponse<NoticeResponse.Paging>> getList(
            @Parameter(hidden = true) @JwtUser User jwtUser,
            PageRequest<NoticeRequest.PagingFilter> pageRequest
    ) {
        if (pageRequest.getFilter() == null) pageRequest.setFilter(new NoticeRequest.PagingFilter());
        pageRequest.getFilter().setPartnerId(jwtUser.getPartnerId());
        return new ApiResponse<>(ApiResultCode.SUCCESS, noticeDao.selectNoticeListPaging(pageRequest));
    }

    @AccessLog("공지사항 단건 조회")
    @GetMapping("/{id}")
    @Operation(summary = "공지사항 단건 조회")
    public ApiResponse<Notice> getOne(
            @Parameter(hidden = true) @JwtUser User jwtUser,
            @PathVariable Integer id
    ) {
        return new ApiResponse<>(ApiResultCode.SUCCESS, noticeDao.selectNoticeById(id));
    }

    @AccessLog("공지사항 등록")
    @PostMapping("/create")
    @Operation(summary = "공지사항 등록")
    public ApiResponse<Void> create(
            @Parameter(hidden = true) @JwtUser User jwtUser,
            @RequestBody NoticeRequest.Create request
    ) {
        request.setPartnerId(jwtUser.getPartnerId());
        request.setCreUser(jwtUser.getLoginId());
        request.setUpdUser(jwtUser.getLoginId());
        noticeDao.insertNotice(request);
        return new ApiResponse<>(ApiResultCode.SUCCESS, null);
    }

    @AccessLog("공지사항 수정")
    @PutMapping("/update")
    @Operation(summary = "공지사항 수정")
    public ApiResponse<Void> update(
            @Parameter(hidden = true) @JwtUser User jwtUser,
            @RequestBody NoticeRequest.Update request
    ) {
        request.setUpdUser(jwtUser.getLoginId());
        noticeDao.updateNotice(request);
        return new ApiResponse<>(ApiResultCode.SUCCESS, null);
    }

    @AccessLog("공지사항 삭제")
    @DeleteMapping("/{id}")
    @Operation(summary = "공지사항 삭제")
    public ApiResponse<Void> delete(
            @Parameter(hidden = true) @JwtUser User jwtUser,
            @PathVariable Integer id
    ) {
        NoticeRequest.Delete request = new NoticeRequest.Delete();
        request.setId(id);
        request.setUpdUser(jwtUser.getLoginId());
        noticeDao.deleteNotice(request);
        return new ApiResponse<>(ApiResultCode.SUCCESS, null);
    }
}
