package com.smart.api.biz.system.controller;

import com.smart.api.annotation.AccessLog;
import com.smart.api.biz.system.service.CompanyService;
import com.smart.core.biz.common.vo.request.PageRequest;
import com.smart.core.biz.common.vo.response.PageResponse;
import com.smart.core.biz.system.vo.request.CompanyRequest;
import com.smart.core.biz.system.vo.response.ApiResponse;
import com.smart.core.biz.system.vo.response.CompanyResponse;
import com.smart.core.enums.ApiResultCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
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
@RequestMapping("/company")
@Tag(name = "CompanyController", description = "회사 목록 조회 관련 API")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    /**
     * 회사_목록_조회 (페이징)
     *
     * @param filter
     * @param pageRequest
     * @return
     */
    @AccessLog("회사관리 목록 조회")
    @GetMapping(value = "/paging")
    @Operation(summary = "회사 목록 조회 (페이징)")
    public ApiResponse<PageResponse<CompanyResponse.Paging>> selectCompanyPaging(
            @Parameter(name = "AttributeRequestPagingFilter", description = "회사 목록 조회 (페이징) 필터", in = ParameterIn.PATH) CompanyRequest.PagingFilter filter,
            @Parameter(name = "PageRequest", description = "회사 목록 조회 페이징") PageRequest<CompanyRequest.PagingFilter> pageRequest
    ) {
        pageRequest.setFilter(filter);

        PageResponse<CompanyResponse.Paging> response = companyService.selectCompanyPaging(pageRequest);

        return new ApiResponse<>(ApiResultCode.SUCCESS, response);
    }

    /**
     * 회사_수정
     *
     * @param companyRequest
     * @return
     */
    @AccessLog("회사 정보 수정")
    @PutMapping("")
    @Operation(summary = "회사 정보 수정")
    public ApiResponse updateCompany(
            @Parameter(description = "회사 정보 수정 Request") @RequestBody CompanyRequest.Update companyRequest
    ) {
        Integer saveCount = companyService.updateCompany(companyRequest);
        if (saveCount == 0) {
            return new ApiResponse<>(ApiResultCode.FAIL_UPDATE);
        }
        //log.debug("=====> 메뉴 수정 성공");
        return new ApiResponse<>(ApiResultCode.SUCCESS);
    }

    /**
     * 회사_등록
     *
     * @param companyRequest
     * @return
     */
    @AccessLog("회사 정보 등록")
    @PostMapping("")
    @Operation(summary = "회사 정보 등록")
    public ApiResponse insertCompany(
            @Parameter(description = "회사 정보 수정 Request") @RequestBody CompanyRequest.Insert companyRequest
    ) {
        Integer saveCount = companyService.insertCompany(companyRequest);
        if (saveCount == 0) {
            return new ApiResponse<>(ApiResultCode.FAIL_UPDATE);
        }
        //log.debug("=====> 메뉴 수정 성공");
        return new ApiResponse<>(ApiResultCode.SUCCESS);
    }

    @AccessLog("회사 정보 삭제")
    @DeleteMapping("")
    @Operation(summary = "회사 정보 삭제")
    public ApiResponse deleteCompany(
            @Parameter(description = "회사 정보 삭제 Request") @RequestBody CompanyRequest.Delete companyRequest
    ) {
        Integer saveCount = companyService.deleteCompany(companyRequest);
        if (saveCount == 0) {
            return new ApiResponse<>(ApiResultCode.FAIL_UPDATE);
        }
        //log.debug("=====> 메뉴 수정 성공");
        return new ApiResponse<>(ApiResultCode.SUCCESS);
    }

}

