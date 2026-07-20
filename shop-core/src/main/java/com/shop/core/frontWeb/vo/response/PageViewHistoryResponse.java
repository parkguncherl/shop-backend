package com.shop.core.frontWeb.vo.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

public class PageViewHistoryResponse {

    @Getter
    @Setter
    @Schema(name = "PageViewHistoryResponseRecentProduct", description = "최근 본 상품 응답 dto")
    public static class RecentProduct {

        @Schema(description = "상품 ID")
        private Integer id;

        @Schema(description = "상품명")
        private String prodNm;

        @Schema(description = "대표 이미지 파일명")
        private String sysFileNm;

        @Schema(description = "최근 조회 시각")
        private String lastViewTm;
    }
}
