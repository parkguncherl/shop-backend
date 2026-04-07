package com.shop.core.biz.partner.vo.response;

import com.shop.core.entity.Partner;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class PartnerResponse {

    @Getter
    @Setter
    @EqualsAndHashCode(callSuper = false)
    @Schema(name = "PartnerResponsePaging", description = "화주계정 페이징 응답", type = "object")
    public static class Paging extends Partner {

        @Schema(description = "상위_파트너_회사명")
        private String upperPartnerNm;

        @Schema(description = "센터명")
        private String logisNm;
    }


    @Getter
    @Setter
    @EqualsAndHashCode(callSuper = false)
    @Schema(name = "PartnerResponseSelect", description = "화주계정 응답", type = "object")
    public static class Select extends Partner {
        @Schema(description = "상위_파트너_회사명")
        private String upperPartnerNm;
    }


    @Getter
    @Setter
    @EqualsAndHashCode(callSuper = false)
    @Schema(name = "PartnerResponseSelect", description = "화주계정 응답", type = "object")
    public static class MyPartner {

        @Schema(description = "아이디(PK)")
        private Integer id;

        @Schema(description = "상위파트너ID(FK)")
        private Integer upperPartnerId;

        @Schema(description = "회사명")
        private String partnerNm;

        @Schema(description = "회사영문명")
        private String partnerEngNm;

        @Schema(description = "상위_파트너_회사명")
        private String upperPartnerNm;
    }

    @Getter
    @Setter
    @EqualsAndHashCode(callSuper = false)
    @Schema(name = "PartnerResponseForSearching", description = "화주계정 응답(검색)", type = "object")
    public static class ForSearching extends Partner {
    }
}
