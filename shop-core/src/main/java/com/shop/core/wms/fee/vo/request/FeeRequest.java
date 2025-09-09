package com.shop.core.wms.fee.vo.request;

import com.shop.core.entity.PartnerFee;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * 적치정보 요청 클래스
 */
public class FeeRequest {

    @Getter
    @Setter
    @Schema(name = "partnerFeeRequestForList", description = "수수료조회")
    public static class PartnerFeeRequestForList {

        @Schema(description = "화주ID(FK)")
        private Integer partnerId;

        @Schema(description = "이력구분")
        private String histYn;
    }



    @Getter
    @Setter
    @Schema(name = "PartnerFeeRequestInfo", description = "수수료조회")
    public static class PartnerFeeRequestInfo extends PartnerFee {
    }

}