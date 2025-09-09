package com.shop.core.wms.inven.vo.request;

import com.shop.core.entity.InspectDet;
import com.shop.core.interfaces.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class InspectInfoRequest {

    @Getter
    @Setter
    @Schema(description = "재고 실사 정보 조회 (페이징) 필터")
    public static class PagingFilter {
        @Schema(description = "창고ID")
        private Integer logisId;

        @Schema(description = "파트너 ID")
        private Integer partnerId;

        @Schema(description = "스큐명")
        private String skuNm;

        @Schema(description = "존")
        private String zoneId;
    }

    @Getter
    @Setter
    @Schema(name = "InspectionInfoCreateRequest", description = "재고 실사 정보 생성")
    public static class InsertInspectionInfo {

        @Schema(description = "실사제목")
        private String inspectTitle;

        @Schema(description = "물류창고id")
        private Integer logisId;

        @Schema(description = "실사목록")
        private List<InspectDet> inspectDetList;

    }

    @Getter
    @Setter
    @Schema(name = "InspectionInfoUpdateRequest", description = "재고 실사 정보 업데이트")
    public static class UpdateInspect {

        @Schema(description = "inspectId")
        private Integer inspectId;

        @Schema(description = "inspectStatCd")
        private String inspectStatCd;

        @Schema(description = "실사목록")
        private List<InspectDet> inspectDetList;
    }

}