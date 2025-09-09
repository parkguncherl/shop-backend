package com.shop.core.wms.ipgo.vo.request;

import com.shop.core.entity.InvenChg;
import com.shop.core.wms.ipgo.vo.response.InvenMoveResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * 재고이동 요청 클래스
 */
public class InvenMoveRequest {

    @Getter
    @Setter
    @Schema(name = "InventoryMovePagingFilter", description = "재고이동목록 페이징 필터")
    public static class PagingFilter {

        @Schema(description = "LOGIS_ID(FK)")
        private Integer logisId;

        @Schema(description = "상품명(SKU) 검색")
        private String skuNm;

        @Schema(description = "화주ID")
        private int partnerId;
    }

    @Getter
    @Setter
    @Schema(name = "InventoryMoveRequestCreate", description = "재고이동 생성 요청 파라미터")
    public static class Create extends InvenMoveResponse.Paging {

        @Schema(description = "이동수량")
        private Integer aftCnt;

        @Schema(description = "이동위치")
        private String aftLoc;

        @Schema(description = "이동사유코드")
        private String invenChgCd;

        @Schema(description = "이동사유비고")
        private String chgEtc;
    }


    @Getter
    @Setter
    @Schema(name = "InventoryChangeRequest", description = "재고변경 요청 파라미터")
    public static class InvenChange extends InvenChg {
    }

}