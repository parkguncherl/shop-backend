package com.shop.core.frontWeb.vo.request;

import com.shop.core.interfaces.RequestFilter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Schema(name = "ContentsRequest", description = "frontWeb 이하 컨텐츠 관련 요청 dto 정의")
public class ContentsRequest {

    @Getter
    @Setter
    @Schema(name = "ContentsRequestContentsInfoListFilter", description = "컨텐츠 목록 필터")
    public static class ContentsInfoListFilter implements RequestFilter {

        @Schema(description = "last id")
        private Integer lastId;
    }
}
