package com.shop.core.frontWeb.vo.request;

import com.shop.core.interfaces.RequestFilter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Schema(name = "WebCommonRequest", description = "web 공통 영역 요청 dto")
public class WebCommonRequest {

    @Getter
    @Setter
    @Schema(name = "WebCommonRequestPartnerCodeByUkFilter", description = "고유 키 조합을 통한 partnerCode 요청")
    public static class partnerCodeByUkFilter implements RequestFilter {

        @Schema(description = "partner id")
        private Integer partnerId;

        @Schema(description = "상위코드")
        private String codeUpper;

        @Schema(description = "코드")
        private String codeCd;

    }
}
