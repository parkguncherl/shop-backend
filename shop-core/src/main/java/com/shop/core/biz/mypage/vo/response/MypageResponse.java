package com.shop.core.biz.mypage.vo.response;

import com.shop.core.entity.Partner;
import com.shop.core.entity.PartnerPrint;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * <pre>
 * Description: 마이페이지 Response
 * Date: 2023/02/27 16:57 PM
 * Company: smart90
 * Author : luckeey
 * </pre>
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class MypageResponse {

    @Getter
    @Setter
    @EqualsAndHashCode
    @Schema(name = "SelectPartnerPrint", description = "마이페이지 응답", type = "object")
    public static class SelectPartnerPrint extends PartnerPrint {

        @Schema(description = "샘플동일상품 정보인쇄여부")
        private String samplePrnYn;

        @Schema(description = "혼용율 인쇄코드")
        private String compPrnCd;

    }

    @Getter
    @Setter
    @EqualsAndHashCode
    @Schema(name = "SelectPartnerPrintInfo", description = "마이페이지 응답", type = "object")
    public static class SelectPartnerPrintInfo {

        @Schema(description = "샘플동일상품 정보인쇄여부")
        private String samplePrnYn;

        @Schema(description = "혼용율 인쇄코드")
        private String compPrnCd;

    }
}
