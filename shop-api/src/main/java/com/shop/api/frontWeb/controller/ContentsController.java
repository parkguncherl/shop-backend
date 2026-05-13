package com.shop.api.frontWeb.controller;


import com.shop.api.annotation.AccessLog;
import com.shop.api.frontWeb.service.ContentsService;
import com.shop.core.annotations.NotAuthRequired;
import com.shop.core.biz.common.vo.request.PageRequest;
import com.shop.core.biz.common.vo.response.PageResponse;
import com.shop.core.biz.system.vo.response.ApiResponse;
import com.shop.core.enums.ApiResultCode;
import com.shop.core.frontWeb.vo.request.ContentsRequest;
import com.shop.core.frontWeb.vo.response.ContentsResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <pre>
 * Description: frontWeb 이하 컨텐츠 Controller
 * Date: 2026/05/12
 * Author: park junsung
 * </pre>
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/frontWeb/contents")
@Tag(name = "ContentsController", description = "frontWeb 이하 컨텐츠 관련 API")
public class ContentsController {

    private final ContentsService contentsService;

    /**
     * frontWeb 이하 컨텐츠 목록 조회
     *
     * @param contentsInfoListFilter
     * @return 조회된 ContentsInfo 목록 페이징
     */
    @AccessLog("frontWeb 이하 컨텐츠 목록 조회(페이징)")
    @GetMapping(value = "/contentsInfoListPaging")
    @Operation(summary = "frontWeb 이하 컨텐츠 목록 조회(페이징)")
    @NotAuthRequired
    public ApiResponse<PageResponse<ContentsResponse.ContentsInfo>> selectContentsInfoListPaging(
            @Parameter(name = "ContentsRequestContentsInfoListFilter", description = "컨텐츠 목록 필터", in = ParameterIn.QUERY) ContentsRequest.ContentsInfoListFilter contentsInfoListFilter,
            @Parameter(name = "PageRequest", description = "컨텐츠 목록 조회 페이징") PageRequest<ContentsRequest.ContentsInfoListFilter> pageRequest
    ) {
        pageRequest.setFilter(contentsInfoListFilter);
        PageResponse<ContentsResponse.ContentsInfo> response = contentsService.selectContentsInfoListPaging(pageRequest);
        return new ApiResponse<>(ApiResultCode.SUCCESS, response);
    }
}
