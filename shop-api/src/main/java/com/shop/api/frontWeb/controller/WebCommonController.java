package com.shop.api.frontWeb.controller;

import com.shop.api.annotation.AccessLog;
import com.shop.api.biz.system.service.CodeService;
import com.shop.api.biz.system.service.PartnerCodeService;
import com.shop.core.annotations.NotAuthRequired;
import com.shop.core.biz.system.vo.request.CodeRequest;
import com.shop.core.biz.system.vo.request.PartnerCodeRequest;
import com.shop.core.biz.system.vo.response.ApiResponse;
import com.shop.core.biz.system.vo.response.CodeResponse;
import com.shop.core.biz.system.vo.response.PartnerCodeResponse;
import com.shop.core.enums.ApiResultCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <pre>
 * Description: 코드_관리 Controller
 * Date: 2023/02/06 11:56 AM
 * Company: smart90
 * Author: luckeey
 * </pre>
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/frontWeb/webCommon")
@Tag(name = "WebCommonController", description = "web 공통 관련 API")
public class WebCommonController {
    private final CodeService codeService;
    private final PartnerCodeService partnerCodeService;

    /**
     * 하위_코드_조회 (by codeUpper)
     *
     * @param codeUpper
     * @return
     */
    @GetMapping(value = "/lower/{codeUpper}")
    @Operation(summary = "하위 코드 조회")
    @NotAuthRequired
    public ApiResponse<List<CodeResponse.LowerSelect>> selectLowerCodeByCodeUpper(
            @Parameter(description = "상위_코드") @PathVariable String codeUpper
    ) {
        // 필수값 체크
        if (StringUtils.isEmpty(codeUpper)) {
            return new ApiResponse<>(ApiResultCode.NO_REQUIRED_VALUE);
        }

        // 하위_코드_조회 (by codeUpper)
        List<CodeResponse.LowerSelect> codeList = codeService.selectLowerCodeByCodeUpperForCodeMng(codeUpper);

        return new ApiResponse<>(codeList);
    }

    /**
     * 하위_코드_조회 (by codeUpper)
     *
     * @param codeUk
     * @return
     */
    @GetMapping(value = "/getCodeName")
    @Operation(summary = "코드명 조회")
    @NotAuthRequired
    public ApiResponse<String> selectCodeName(
            @Parameter(description = "상위_코드") CodeRequest.CodeUk codeUk
    ) {

        // 하위_코드_조회 (by codeUpper)
        String codeName = codeService.selectCodeName(codeUk);

        return new ApiResponse<>(codeName);
    }



    /**
     * 상품목록 조회 비인증
     *
     * @param partnerUpperCode
     * @return 조회된 ProductInfoList
     */
    @AccessLog("web-카테고리목록 조회")
    @GetMapping(value = "/partnerCode/{partnerUpperCode}")
    @Operation(summary = "web-partnerCode 조회")
    @NotAuthRequired
    public ApiResponse<List<PartnerCodeResponse.PartnerCodeDropDown>> partnerCodeList(@PathVariable String partnerUpperCode) {
        PartnerCodeRequest.PartnerCodeDropDown partnerCodeRequest = new PartnerCodeRequest.PartnerCodeDropDown();
        partnerCodeRequest.setCodeUpper(partnerUpperCode);
        partnerCodeRequest.setPartnerId(1);
        List<PartnerCodeResponse.PartnerCodeDropDown> response = partnerCodeService.selectLowerCodeByPartnerCodeUpper(partnerCodeRequest);
        return new ApiResponse<>(ApiResultCode.SUCCESS, response);
    }

}
