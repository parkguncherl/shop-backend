package com.shop.api.product.controller;

import com.shop.api.common.service.CommonService;
import com.shop.api.product.service.ProductMngService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <pre>
 * Description: ProductMng Controller
 * Date: 2026/01/30 14:23
 * Author: park junsung
 * </pre>
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/productMng")
@Tag(name = "ProductMngController", description = "상품관리 관련 API 정의")
public class ProductMngController {

    private final CommonService commonService;
    private final ProductMngService productMngService;

    /**
     * 개별_파일_조회
     */
//    @GetMapping(value = "/file")
//    @Operation(summary = "개별 파일 조회")
//    public ApiResponse<CommonResponse.SelectFile> selectFileByUk(
//            @Parameter(name = "CommonRequestSelectFile", description = "개별 파일 조회 Request", in = ParameterIn.PATH) CommonRequest.SelectFile commonRequest
//    ) {
//        // 필수값 체크
//        if (StringUtils.isEmpty(commonRequest.getId().toString())) {
//            return new ApiResponse<>(ApiResultCode.NO_REQUIRED_VALUE);
//        }
//
//        FileDet file = new FileDet();
//        file.setId(commonRequest.getId());
//        file.setFileSeq(1);
//
//        // 파일_조회 (by Uk)
//        CommonResponse.SelectFile selectFile = commonService.selectFileDet(file);
//
//        return new ApiResponse<>(ApiResultCode.SUCCESS, selectFile);
//    }
}
