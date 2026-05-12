package com.shop.api.frontWeb.controller;

import com.shop.api.annotation.AccessLog;
import com.shop.api.annotation.JwtUser;
import com.shop.api.biz.system.service.CodeService;
import com.shop.api.biz.system.service.PartnerCodeService;
import com.shop.api.common.service.CommonService;
import com.shop.core.annotations.NotAuthRequired;
import com.shop.core.biz.common.vo.request.CommonRequest;
import com.shop.core.biz.system.vo.request.CodeRequest;
import com.shop.core.biz.system.vo.request.PartnerCodeRequest;
import com.shop.core.biz.system.vo.response.ApiResponse;
import com.shop.core.biz.system.vo.response.CodeResponse;
import com.shop.core.biz.system.vo.response.PartnerCodeResponse;
import com.shop.core.entity.FileDet;
import com.shop.core.entity.PartnerCode;
import com.shop.core.entity.User;
import com.shop.core.enums.ApiResultCode;
import com.shop.core.frontWeb.vo.request.WebCommonRequest;
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
    private final CommonService commonService;

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
     * partnerCode 목록 조회
     *
     * @param partnerUpperCode
     * @return 조회된 ProductInfoList
     */
    @AccessLog("web-partnerCode 목록 조회")
    @GetMapping(value = "/partnerCode/{partnerUpperCode}")
    @Operation(summary = "web-partnerCode 목록 조회")
    @NotAuthRequired
    public ApiResponse<List<PartnerCodeResponse.LowerSelect>> partnerCodeList(@PathVariable String partnerUpperCode) {
        PartnerCodeRequest.PartnerCodeDropDown partnerCodeRequest = new PartnerCodeRequest.PartnerCodeDropDown();
        partnerCodeRequest.setCodeUpper(partnerUpperCode);
        partnerCodeRequest.setPartnerId(1);
        List<PartnerCodeResponse.LowerSelect> response = partnerCodeService.selectLowerCodeByCodeUpperForPartnerCodeMng(partnerCodeRequest);
        return new ApiResponse<>(ApiResultCode.SUCCESS, response);
    }

    /**
     * web-partnerCode 조회
     *
     * @param partnerCodeByUkFilter
     * @return 조회된 PartnerCode
     */
    @AccessLog("web-partnerCode 조회")
    @GetMapping(value = "/partnerCodeByUk")
    @Operation(summary = "web-partnerCode 조회")
    @NotAuthRequired
    public ApiResponse<PartnerCode> partnerCodeByUk(
            @Parameter(description = "partnerCode 고유 키 조합 조회 필터") WebCommonRequest.partnerCodeByUkFilter partnerCodeByUkFilter
    ) {
        // 필수값 체크
        if (StringUtils.isEmpty(partnerCodeByUkFilter.getCodeUpper()) || StringUtils.isEmpty(partnerCodeByUkFilter.getCodeCd())) {
            return new ApiResponse<>(ApiResultCode.NO_REQUIRED_VALUE);
        }

        partnerCodeByUkFilter.setPartnerId(1);

        PartnerCode response = partnerCodeService.selectPartnerCodeByUk(partnerCodeByUkFilter.getPartnerId(), partnerCodeByUkFilter.getCodeUpper(), partnerCodeByUkFilter.getCodeCd());
        return new ApiResponse<>(ApiResultCode.SUCCESS, response);
    }

    /**
     * s3(cloudFlare) 파일정보
     *
     * @param fileKey
     * @return String
     */
    @AccessLog("파일url 적용")
    @GetMapping("/getFileUrl")
    @NotAuthRequired  // ← 추가
    @Operation(summary = "파일의 실 url 조회")
    public ApiResponse<String> getFileUrl(CommonRequest.FileKey fileKey) {
        String resultUrl = commonService.getFileUrl(fileKey.getFileKey());
        return new ApiResponse<>(resultUrl);
    }

    /**
     * 파일_목록_조회 (by ID)
     *
     * @param jwtUser
     * @param fileId
     * @return
     */
    @GetMapping(value = "/fileList/{fileId}")
    @NotAuthRequired  // ← 추가
    @Operation(summary = "파일 목록 조회")
    public ApiResponse<List<FileDet>> selectFileList(
            @Parameter(hidden = true) @JwtUser User jwtUser,
            @PathVariable Integer fileId
    ) {
        // 파일_목록_조회 (by ID)
        List<FileDet> fileList = commonService.selectFileList(fileId);

        return new ApiResponse<>(ApiResultCode.SUCCESS, fileList);
    }

}
