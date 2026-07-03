package com.shop.api.frontWeb.controller;

import com.shop.api.annotation.GuestUser;
import com.shop.core.annotations.NotAuthRequired;
import com.shop.core.biz.notice.dao.NoticeDao;
import com.shop.core.biz.notice.vo.response.NoticeResponse;
import com.shop.core.biz.system.vo.response.ApiResponse;
import com.shop.core.entity.GuestToken;
import com.shop.core.enums.ApiResultCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/frontWeb/notice")
@Tag(name = "FrontNoticeController", description = "FO 공지사항 API")
@RequiredArgsConstructor
public class FrontNoticeController {

    private final NoticeDao noticeDao;

    @NotAuthRequired
    @GetMapping("/popupList")
    @Operation(summary = "게시중인 팝업 공지사항 목록")
    public ApiResponse<List<NoticeResponse.PopupNotice>> getPopupList(
            @Parameter(hidden = true) @GuestUser GuestToken guestUser
    ) {
        return new ApiResponse<>(ApiResultCode.SUCCESS,
                noticeDao.selectActivePopupNoticeList(guestUser.getPartnerId())
                        .stream().map(NoticeResponse.PopupNotice::fromEntity).collect(Collectors.toList()));
    }
}
