package com.shop.core.receipt.vo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;

@Getter
@Setter
@Schema(description = "영수증 요청")
public class ReceiptRequest {

    @Getter
    @Setter
    @EqualsAndHashCode(callSuper = false)
    @Schema(name = "ReceiptRequestCreateUpdate", description = "영수증 설정 생성/수정 요청 파라미터")
    public static class CreateUpdate {
        @Schema(description = "파일 ID")
        private Integer fileId;

        @Schema(description = "로고 내용")
        private String logoCntn;

        @Schema(description = "로고 위치")
        private String logoLocCd;

        @Schema(description = "타이틀 여부")
        private Boolean titleYn;

        @Schema(description = "타이틀 관리")
        private String titleMng;

        @Schema(description = "타이틀 일반")
        private String titleNor;

        @Schema(description = "상단 여부")
        private Boolean topYn;

        @Schema(description = "상단 관리")
        private String topMng;

        @Schema(description = "상단 일반")
        private String topNor;

        @Schema(description = "하단 여부")
        private Boolean bottomYn;

        @Schema(description = "하단 관리")
        private String bottomMng;

        @Schema(description = "하단 일반")
        private String bottomNor;
    }
}