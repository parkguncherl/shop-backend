package com.shop.core.biz.platform.vo.request;

import com.shop.core.interfaces.RequestFilter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * <pre>
 * Description: 플랫폼(DDM CHOICE) 상품 조회 요청 dto
 * </pre>
 */
@Schema(name = "PlatformProductRequest", description = "플랫폼 상품 조회 요청 dto 정의")
public class PlatformProductRequest {

    @Getter
    @Setter
    @Schema(name = "PlatformProductRequestProductListFilter", description = "플랫폼 상품 목록 필터")
    public static class ProductListFilter implements RequestFilter {

        @Schema(description = "last id (무한스크롤 커서)")
        private Integer lastId;

        @Schema(description = "partner id (로그인 사용자 기준 서버 세팅)")
        private Integer partnerId;

        @Schema(description = "대분류(90010) 코드 - prod_type_code 접두 LIKE 로 하위 소분류 전체 조회")
        private String majorCode;

        @Schema(description = "소분류(90011) 코드 - prod_type_code 정확 일치(=) 조회")
        private String minorCode;

        @Schema(description = "정렬 기준: PRICE_ASC | PRICE_DESC | POPULAR (기본: 최신순)")
        private String sort;
    }
}
