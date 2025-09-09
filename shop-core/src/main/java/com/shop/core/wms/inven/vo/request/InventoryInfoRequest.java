package com.shop.core.wms.inven.vo.request;

import com.shop.core.interfaces.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

public class InventoryInfoRequest {

    @Getter
    @Setter
    @Schema(description = "재고 정보 조회 (페이징) 필터")
    public static class PagingFilter {
        @Schema(description = "창고ID")
        private Integer logisId;

        @Schema(description = "파트너 ID")
        private Integer partnerId;

        @Schema(description = "스큐명")
        private String skuNm;

        @Schema(description = "존")
        private Integer zoneId;

        @Schema(description = "조회구분")
        private String searchType;
    }

    @Getter
    @Setter
    @Schema(name = "InventoryinfoUpdateRequest", description = "재고 위치 정보 업데이트")
    public static class UpdateInvenLocation extends BaseEntity {

        @Schema(description = "LOCATION ID")
        private Integer locId;

        @Schema(description = "SKU ID")
        private Integer skuId;

        @Schema(description = "SKU 명")
        private String skuNm;

        // 변경을 위한 컬럼들
        @Schema(description = "toZone")
        private Integer toZone;

        @Schema(description = "toLoc")
        private Integer toLoc;

        @Schema(description = "chgCnt")
        private Integer chgCnt;

    }
    @Getter
    @Setter
    @Schema(description = "재고 정보 조회 (페이징) 필터")
    public static class InventoryLocationFilter {
        @Schema(description = "창고ID")
        private Integer logisId;

        @Schema(description = "파트너 ID")
        private Integer partnerId;

        @Schema(description = "스큐명")
        private String skuNm;

        @Schema(description = "존")
        private Integer zoneId;
    }

    @Getter
    @Setter
    @Schema(description = "재고 정보 조회 (페이징) 필터")
    public static class InventoryLocationDetFilter {
        @Schema(description = "창고ID")
        private Integer logisId;

        @Schema(description = "파트너 ID")
        private Integer partnerId;

        @Schema(description = "location id")
        private Integer locId;
    }

}