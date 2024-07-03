package com.smart.api.biz.system.controller;

import com.smart.api.annotation.AccessLog;
import com.smart.api.annotation.JwtUser;
import com.smart.api.biz.system.service.AttributeService;
import com.smart.core.biz.common.vo.request.PageRequest;
import com.smart.core.biz.common.vo.response.PageResponse;
import com.smart.core.biz.system.vo.request.AttributeRequest;
import com.smart.core.biz.system.vo.request.MenuRequest;
import com.smart.core.biz.system.vo.response.ApiResponse;
import com.smart.core.biz.system.vo.response.AttributeResponse;
import com.smart.core.entity.User;
import com.smart.core.enums.ApiResultCode;
import com.smart.core.enums.EsseType;
import com.smart.core.utils.CommUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <pre>
 * Description : 속성접근 Controller
 * Date :
 * Company : "Home
 * Author : park
 * </pre>
 */
@Slf4j
@RestController
@RequestMapping("/attribute")
@Tag(name = "AttributeController", description = "속성조회 관련 API")
public class AttributeController {

    @Autowired
    private AttributeService attributeService;

    /**
     * 속성_목록_조회 (페이징)
     *
     * @param filter
     * @param pageRequest
     * @return
     */
    @AccessLog("속성관리 목록 조회")
    @GetMapping(value = "/paging")
    @Operation(summary = "속성관리 목록 조회 (페이징)")
    public ApiResponse<PageResponse<AttributeResponse.Paging>> selectAttributeListPaging(
            @Parameter(name = "AttributeRequestPagingFilter", description = "속성 목록 조회 (페이징) 필터", in = ParameterIn.PATH) AttributeRequest.PagingFilter filter,
            @Parameter(name = "PageRequest", description = "속성 목록 조회 페이징") PageRequest<AttributeRequest.PagingFilter> pageRequest
    ) {
        pageRequest.setFilter(filter);

        PageResponse<AttributeResponse.Paging> response = attributeService.selectAttributePaging(pageRequest);

        return new ApiResponse<>(ApiResultCode.SUCCESS, response);
    }

    /**
     * 속성_생성
     *
     * @param attributeRequest
     * @return
     */
    /*
    @AccessLog("속성 생성")
    @PostMapping("")
    @Operation(summary = "속성 생성")
    public ApiResponse insertAttribute(
            @Parameter(description = "속성 생성 Request") @RequestBody AttributeRequest.Insert attributeRequest
    ) {
        Integer saveCount = attributeService.insertAttribute(attributeRequest);
        if (saveCount == 0) {
            return new ApiResponse<>(ApiResultCode.FAIL_UPDATE);
        }
        return new ApiResponse<>(ApiResultCode.SUCCESS);
    } */

    /**
     * 속성_수정
     *
     * @param attributeRequest
     * @return
     */
    @AccessLog("속성 수정")
    @PutMapping("")
    @Operation(summary = "속성 수정")
    public ApiResponse modifyAttribute(
            @Parameter(description = "속성 수정 Request")
            @RequestBody AttributeRequest.Modified attributeRequest
    ) {
        Integer updateCount = attributeService.updateAttribute(attributeRequest);
        Integer insertCount = attributeService.insertAttribute(attributeRequest);
        if (updateCount == 0 && insertCount == 0) {
            return new ApiResponse<>(ApiResultCode.FAIL_UPDATE);
        }
        //log.debug("=====> 메뉴 수정 성공");
        return new ApiResponse<>(ApiResultCode.SUCCESS);
    }

}
