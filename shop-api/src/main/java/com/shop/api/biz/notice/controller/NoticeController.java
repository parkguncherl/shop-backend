package com.shop.api.biz.notice.controller;

import com.shop.api.annotation.JwtUser;
import com.shop.api.biz.notice.service.NoticeService;
import com.shop.api.biz.system.service.UserService;
import com.shop.core.biz.common.vo.request.PageRequest;
import com.shop.core.biz.common.vo.response.PageResponse;
import com.shop.core.entity.User;
import com.shop.core.enums.ApiResultCode;
import com.shop.core.biz.notice.vo.request.NoticeRequest;
import com.shop.core.biz.notice.vo.response.NoticeResponse;
import com.shop.core.enums.NoticeCd;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.shop.core.biz.system.vo.response.ApiResponse;

import java.util.List;

@RestController
@RequestMapping("/notice")
@RequiredArgsConstructor
@Tag(name = "NoticeController", description = "공지사항 API")
public class NoticeController {

    private final NoticeService noticeService;
    private final UserService userService;

    @Operation(summary = "공지사항 목록 조회(페이징)", description = "공지사항 목록을 조회합니다.")
    @GetMapping("/paging")
    public ApiResponse<PageResponse<NoticeResponse.Paging>> getNoticeListPaging(
            @Parameter(hidden = true) @JwtUser User jwtUser,
            @Parameter(name = "NoticeRequestPagingFilter", description = "공지사항 목록 조회 (페이징) 필터", in = ParameterIn.PATH) NoticeRequest.PagingFilter filter,
            @Parameter(name = "PageRequest", description = "공지사항 목록 조회 페이징") PageRequest<NoticeRequest.PagingFilter> pageRequest) {
        pageRequest.setFilter(filter);
        PageResponse<NoticeResponse.Paging> result = noticeService.getNoticeListPaging(pageRequest, jwtUser);
        return new ApiResponse<>(ApiResultCode.SUCCESS, result);
    }

    @Operation(summary = "공지사항 생성", description = "새로운 공지사항을 생성합니다.")
    @PostMapping("/create")
    public ApiResponse<Boolean> createNotice(
            @Parameter(hidden = true) @JwtUser User jwtUser,
            @Parameter(name = "NoticeRequestCreate", description = "공지사항 생성 Request") @RequestBody NoticeRequest.Create request) {
        Boolean result = noticeService.createNotice(request, jwtUser);
        return new ApiResponse<>(result ? ApiResultCode.SUCCESS : ApiResultCode.FAIL_CREATE);
    }

    @Operation(summary = "공지사항 수정", description = "기존 공지사항을 수정합니다.")
    @PutMapping("/update")
    public ApiResponse<Boolean> updateNotice(
            @Parameter(hidden = true) @JwtUser User jwtUser,
            @Parameter(name = "NoticeRequestUpdate", description = "공지사항 수정 Request") @RequestBody NoticeRequest.Update request) {
        Boolean result = noticeService.updateNotice(request, jwtUser);
        return new ApiResponse<>(result ? ApiResultCode.SUCCESS : ApiResultCode.FAIL_UPDATE);
    }

    @Operation(summary = "공지사항 삭제", description = "공지사항을 논리적으로 삭제합니다.")
    @DeleteMapping("/delete")
    public ApiResponse<Boolean> deleteNotice(
            @Parameter(hidden = true) @JwtUser User jwtUser,
            @RequestBody List<Integer> listOfId) {
        Integer result = noticeService.deleteNotices(listOfId, jwtUser);
        if (result != listOfId.size()) {
            return new ApiResponse<>(ApiResultCode.FAIL_DELETE);
        }
        return new ApiResponse<>(ApiResultCode.SUCCESS);
    }
    @Operation(summary = "공지사항 개별삭제", description = "공지사항을 논리적으로 삭제합니다.")
    @DeleteMapping("/delete/{noticeId}")
    public ApiResponse<Boolean> deleteNotice(
            @Parameter(hidden = true) @JwtUser User jwtUser,
            @PathVariable Integer noticeId) {
        List<Integer> listOfId = List.of(noticeId);
        Integer result = noticeService.deleteNotices(listOfId, jwtUser);
        if (result != 1) {
            return new ApiResponse<>(ApiResultCode.FAIL_DELETE);
        }
        return new ApiResponse<>(ApiResultCode.SUCCESS);
    }

    @Operation(summary = "공지사항 상세 조회", description = "선택한 공지사항의 상세 정보를 조회합니다.")
    @GetMapping("/detail/{noticeId}")
    public ApiResponse<NoticeResponse.Detail> getNoticeDetail(
            @Parameter(hidden = true) @JwtUser User jwtUser,
            @PathVariable Integer noticeId) {
        NoticeResponse.Detail result = noticeService.getNoticeDetail(noticeId, jwtUser);
        if (result == null) {
            return new ApiResponse<>(ApiResultCode.FAIL, "공지사항 정보를 찾을 수 없습니다.");
        }
        return new ApiResponse<>(ApiResultCode.SUCCESS, result);
    }

    @Operation(summary = "공지사항 내용 제거", description = "선택한 공지사항의 내용을 null로 변경합니다.")
    @PutMapping("/remove-content/{noticeId}")
    public ApiResponse<Boolean> removeNoticeContent(
            @Parameter(hidden = true) @JwtUser User jwtUser,
            @PathVariable Integer noticeId) {
        Boolean result = noticeService.removeNoticeContent(noticeId, jwtUser);
        return new ApiResponse<>(result ? ApiResultCode.SUCCESS : ApiResultCode.FAIL_UPDATE);
    }
    @Operation(summary = "스티커 정보 조회", description = "파트너 ID와 일치하는 가장 낮은 ID의 스티커 정보를 조회합니다.")
    @GetMapping("/sticker")
    public ApiResponse<NoticeResponse.Sticker> getStickerInfo(
            @Parameter(hidden = true) @JwtUser User jwtUser) {
        NoticeResponse.Sticker result = noticeService.getStickerInfo(jwtUser);
        return new ApiResponse<>(ApiResultCode.SUCCESS, result);
    }

    @Operation(summary = "스티커 정보 수정", description = "파트너 ID와 일치하는 가장 낮은 ID의 스티커 정보를 수정합니다.")
    @PutMapping("/sticker")
    public ApiResponse<Boolean> updateStickerInfo(
            @RequestBody NoticeRequest.StickerUpdate request,
            @Parameter(hidden = true) @JwtUser User jwtUser) {
        NoticeResponse.Sticker sticker = noticeService.getStickerInfo(jwtUser);
        if (sticker != null && sticker.getId() != null) {
            NoticeRequest.Update update = new NoticeRequest.Update();
            update.setId(sticker.getId());
            update.setNoticeCd(NoticeCd.NOTICE.getCode());
            update.setNoticeCntn(request.getNoticeCntn());
            update.setUpdUser(jwtUser.getLoginId());
            update.setTitle(sticker.getTitle());
            noticeService.updateNotice(update, jwtUser);
        } else {
            User user = userService.selectUserById(jwtUser.getId());
            NoticeRequest.Create create = new NoticeRequest.Create();
            create.setNoticeCd(NoticeCd.NOTICE.getCode());
            create.setNoticeCntn(request.getNoticeCntn());
            create.setUpdUser(jwtUser.getLoginId());
            create.setPartnerId(user.getPartnerId());
            create.setTitle("공지사항");
            noticeService.createNotice(create, jwtUser);
        }
        return new ApiResponse<>(ApiResultCode.SUCCESS);
    }
}
