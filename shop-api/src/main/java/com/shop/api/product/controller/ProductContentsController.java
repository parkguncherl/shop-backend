package com.shop.api.product.controller;

import com.shop.api.annotation.JwtUser;
import com.shop.api.product.service.ProductContentsService;
import com.shop.core.entity.User;
import com.shop.core.enums.ApiResultCode;
import com.shop.core.product.vo.request.ProductContentsRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.shop.core.biz.system.vo.response.ApiResponse;

/**
 * <pre>
 * Description: ProductContents Controller
 * Date: 2026/02/04
 * Author: park junsung
 * </pre>
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/productContents")
@Tag(name = "ProductContentsController", description = "상품컨텐츠 관련 API 정의")
public class ProductContentsController {

    private final ProductContentsService productContentsService;

    /**
     * 하나 혹은 다수의 신규 원단 데이터 추가
     *
     * @param insertProductContents
     * @return
     */
    @Operation(summary = "하나 혹은 다수의 신규 원단 데이터 추가", description = "하나 혹은 다수의 신규 원단 데이터를 추가합니다.")
    @PutMapping("/insertProductContents")
    public ApiResponse<Void> insertProductContents(
            @Parameter(hidden = true) @JwtUser User jwtUser,
            @RequestBody ProductContentsRequest.InsertProductContents insertProductContents
    ) {
        Integer insertedRowCnt = productContentsService.insertProductContents(insertProductContents, jwtUser);
        if (insertedRowCnt == null) {
            return new ApiResponse<>(ApiResultCode.FAIL_CREATE);
        }
        return new ApiResponse<>(ApiResultCode.SUCCESS);
    }
}
