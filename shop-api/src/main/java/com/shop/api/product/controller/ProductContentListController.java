package com.shop.api.product.controller;

import com.shop.api.annotation.AccessLog;
import com.shop.api.annotation.JwtUser;
import com.shop.api.product.service.ProductContentListService;
import com.shop.core.biz.common.vo.request.PageRequest;
import com.shop.core.biz.common.vo.response.PageResponse;
import com.shop.core.biz.system.vo.response.ApiResponse;
import com.shop.core.entity.User;
import com.shop.core.enums.ApiResultCode;
import com.shop.core.product.vo.request.ProductContentListRequest;
import com.shop.core.product.vo.response.ProductContentListResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * <pre>
 * Description: ProductContentList Controller
 * Date: 2026/01/30 14:23
 * Author: park junsung
 * </pre>
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/productContentList")
@Tag(name = "ProductContentListController", description = "상품컨텐츠목록 관련 API 정의")
public class ProductContentListController {

    private final ProductContentListService productContentListService;

    /**
     * 상품컨텐츠목록 조회(페이징)
     *
     * @param productContentListFilter
     * @return 페이징 동작에 대응하여 조회된 상품컨텐츠목록
     */
    @AccessLog("상품컨텐츠목록 조회(페이징)")
    @GetMapping(value = "/productContentListPaging")
    @Operation(summary = "상품컨텐츠목록 조회(페이징)")
    public ApiResponse<PageResponse<ProductContentListResponse.ProductContent>> selectProdContentList(
            @Parameter(hidden = true) @JwtUser User jwtUser,
            @Parameter(name = "ProductContentListRequestProductContentListFilter", description = "상품컨텐츠목록 조회 필터", in = ParameterIn.QUERY) ProductContentListRequest.ProductContentListFilter productContentListFilter,
            @Parameter(name = "PageRequest", description = "상품컨텐츠목록 조회 페이징") PageRequest<ProductContentListRequest.ProductContentListFilter> pageRequest
    ) {
        pageRequest.setFilter(productContentListFilter);
        PageResponse<ProductContentListResponse.ProductContent> response = productContentListService.selectProdContentList(pageRequest, jwtUser);
        return new ApiResponse<>(ApiResultCode.SUCCESS, response);
    }

    /**
     * 신규 상품컨텐츠 추가
     *
     * @param insertProductContents
     * @return
     */
    @Operation(summary = "신규 상품컨텐츠 추가", description = "신규 상품컨텐츠를 추가합니다.")
    @PutMapping("/insertProductContents")
    public ApiResponse<Void> insertProductContents(
            @Parameter(hidden = true) @JwtUser User jwtUser,
            @RequestPart("main") ProductContentListRequest.InsertProductContents insertProductContents,
            @RequestPart(value = "files", required = false) List<MultipartFile> files
    ) {
        try {
            if (insertProductContents.getCommonRequestFileUploads() != null) {
                // 업로드 파일 할당
                insertProductContents.getCommonRequestFileUploads().setUploadFiles(files);
            }
            Integer insertedRowCnt = productContentListService.insertProductContents(insertProductContents, jwtUser);
            if (insertedRowCnt == null) {
                return new ApiResponse<>(ApiResultCode.FAIL_CREATE);
            }
            return new ApiResponse<>(ApiResultCode.SUCCESS);
        } catch (IOException exception) {
            return new ApiResponse<>(ApiResultCode.FAIL_CREATE);
        }
    }

    /**
     * 단일 Contents 데이터 및 연관된 상품정보를 삭제
     *
     * @param deleteProductContents
     * @return
     */
    @Operation(summary = "단일 Contents 데이터 및 연관된 상품정보 삭제", description = "단일 Contents 데이터 및 연관된 상품정보를 삭제합니다.")
    @PatchMapping("/deleteProductContents")
    public ApiResponse<Void> deleteProductContents(
            @Parameter(hidden = true) @JwtUser User jwtUser,
            @RequestBody ProductContentListRequest.DeleteProductContents deleteProductContents
    ) {
        try {
            productContentListService.deleteProductContents(deleteProductContents, jwtUser);
            return new ApiResponse<>(ApiResultCode.SUCCESS);
        } catch (IOException exception) {
            return new ApiResponse<>(ApiResultCode.FAIL_DELETE);
        }
    }
}
