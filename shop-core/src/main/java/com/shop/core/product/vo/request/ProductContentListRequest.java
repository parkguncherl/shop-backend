package com.shop.core.product.vo.request;

import com.shop.core.biz.common.vo.request.CommonRequest;
import com.shop.core.interfaces.RequestFilter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Schema(name = "ProductContentListRequest", description = "상품컨텐츠 목록 영역 요청")
public class ProductContentListRequest {

    @Getter
    @Setter
    @Schema(name = "ProductContentListRequestProductContentListFilter", description = "상품컨텐츠 목록 필터")
    public static class ProductContentListFilter implements RequestFilter {

        @Schema(description = "파트너 id")
        private Integer partnerId;

        @Schema(description = "컨텐츠 유형(전역 상수로 지정)")
        private String newsType;

        @Schema(description = "제목")
        private String newsTitle;


        @Schema(description = "lastId")
        private Integer lastId;
    }
}
