package com.shop.core.biz.vendorProduct.vo.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <pre>
 * Description: 협력업체별 상품 목록 응답 VO
 * </pre>
 */
@Getter
@Setter
@NoArgsConstructor
@Schema(name = "VendorProductResponse", description = "협력업체 상품 목록 응답")
public class VendorProductResponse {

    @Schema(description = "상품 ID")
    private Integer id;

    @Schema(description = "상품명")
    private String prodNm;

    @Schema(description = "대표이미지 시스템 파일명 (썸네일용)")
    private String repSysFileNm;

    @Schema(description = "시즌 (예: S__W)")
    private String season;

    @Schema(description = "색상 (콤마 구분)")
    private String prodColors;

    @Schema(description = "사이즈 (콤마 구분)")
    private String prodSizes;
}
