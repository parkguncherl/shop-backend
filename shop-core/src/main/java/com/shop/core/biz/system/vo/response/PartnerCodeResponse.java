package com.shop.core.biz.system.vo.response;

import com.shop.core.entity.PartnerCode;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * <pre>
 * Description: 코드 Response
 * Date: 2023/02/16 14:58 PM
 * Company: smart90
 * Author: luckeey
 * </pre>
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class PartnerCodeResponse {

    @Getter
    @Setter
    @EqualsAndHashCode(callSuper = false)
    @Schema(name = "PartnerCodeResponsePaging", description = "코드 페이징 응답", type = "object")
    public static class Paging extends PartnerCode {

        @Schema(description = "NO")
        @Parameter(description = "NO")
        private Integer no;

        @Schema(description = "상위_코드_명")
        @Parameter(description = "상위_코드_명")
        private String codeUpperNm;

        @Schema(description = "하위_코드_수")
        @Parameter(description = "하위_코드_수")
        private Integer lowerCodeCnt;

    }

    @Getter
    @Setter
    @EqualsAndHashCode(callSuper = false)
    @Schema(name = "PartnerCodeResponseSelect", description = "코드 응답", type = "object")
    public static class Select extends PartnerCode {

    }

    @Getter
    @Setter
    @EqualsAndHashCode(callSuper = false)
    @Schema(name = "PartnerCodeDropDown", description = "코드 드롭다운 응답", type = "object")
    public static class PartnerCodeDropDown {

        @Schema(description = "코드_id")
        @Parameter(description = "코드_id")
        private Integer id;

        @Schema(description = "상위코드")
        @Parameter(description = "상위코드")
        private String codeUpper;

        @Schema(description = "코드")
        @Parameter(description = "코드")
        private String codeCd;

        @Schema(description = "코드_명")
        @Parameter(description = "코드_명")
        private String codeNm;
    }

    @Getter
    @Setter
    @EqualsAndHashCode(callSuper = false)
    @Schema(name = "PartnerCodeResponseLowerSelect", description = "하위 코드 응답", type = "object")
    public static class LowerSelect extends PartnerCode {

        @Schema(description = "상위_코드_명")
        private String codeUpperNm;

        @Schema(description = "수정자")
        private String updNm;

        @Schema(description = "등록자")
        private String creNm;

        public PartnerCode toEntity() {
            return PartnerCode.builder()
                    .partnerId(this.getPartnerId())
                    .id(this.getId())
                    .codeUpper(this.getCodeUpper())
                    .codeCd(this.getCodeCd())
                    .codeNm(this.getCodeNm())
                    .codeDesc(this.getCodeDesc())
                    .defCodeVal(this.getDefCodeVal())
                    .delYn(this.getDelYn())
                    .codeEtc(this.getCodeEtc())
                    .codeEtc1(this.getCodeEtc1())
                    .codeEtc2(this.getCodeEtc2())
                    .codeEtc3(this.getCodeEtc3())
                    .codeOrder(this.getCodeOrder())
                    .creUser(this.creUser)
                    .updUser(this.updUser)
                    .build();
        }

    }

    @Getter
    @Setter
    @EqualsAndHashCode(callSuper = false)
    @Schema(name = "PartnerCodeResponseExcel", description = "코드 엑셀 응답", type = "object")
    public static class Excel extends PartnerCode {

        @Schema(description = "NO")
        @Parameter(description = "NO")
        private Integer no;

        @Schema(description = "상위_코드_명")
        @Parameter(description = "상위_코드_명")
        private String codeUpperNm;

        @Schema(description = "하위_코드_수")
        @Parameter(description = "하위_코드_수")
        private Integer lowerCodeCnt;
    }

}
