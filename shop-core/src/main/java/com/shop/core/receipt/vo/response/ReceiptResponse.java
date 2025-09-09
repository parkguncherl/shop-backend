package com.shop.core.receipt.vo.response;

import com.shop.core.entity.PartnerPrint;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@Schema(description = "영수증 응답")
public class ReceiptResponse {

    @Schema(description = "영수증 설정 ID")
    private Integer id;

    @Schema(description = "파트너 ID")
    private Integer partnerId;

    @Schema(description = "파일 ID")
    private Integer fileId;

    @Schema(description = "로고 내용")
    private String logoCntn;

    @Schema(description = "로고 위치")
    private String logoLocCd;

    @Schema(description = "타이틀 여부")
    private String titleYn;

    @Schema(description = "타이틀 관리")
    private String titleMng;

    @Schema(description = "타이틀 일반")
    private String titleNor;

    @Schema(description = "상단 여부")
    private String topYn;

    @Schema(description = "상단 관리")
    private String topMng;

    @Schema(description = "상단 일반")
    private String topNor;

    @Schema(description = "하단 여부")
    private String bottomYn;

    @Schema(description = "하단 관리")
    private String bottomMng;

    @Schema(description = "하단 일반")
    private String bottomNor;

    @Schema(description = "프린터 설정")
    private PartnerPrint partnerPrint;
}