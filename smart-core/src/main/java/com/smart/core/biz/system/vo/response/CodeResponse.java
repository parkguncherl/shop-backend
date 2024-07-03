package com.smart.core.biz.system.vo.response;

import com.smart.core.entity.Code;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * <pre>
 * Description : 코드 Response
 * Date : 2023/02/16 14:58 PM
 * Company : smart90
 * Author : sclee9946
 * </pre>
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class CodeResponse {

    @Getter
    @Setter
    @EqualsAndHashCode
    @Schema(name = "CodeResponsePaging", description = "코드 페이징 응답", type = "object")
    public static class Paging extends Code {

        @Schema(description = "NO")
        @Parameter(description = "NO")
        private Integer no;

        @Schema(description = "상위_코드_명")
        @Parameter(description = "상위_코드_명")
        private String codeUpperNm;

        @Schema(description = "하위_코드_수")
        @Parameter(description = "하위_코드_수")
        private Integer lowerCodeCnt;

        @Schema(description = "기타정보(변환)")
        @Parameter(description = "기타정보(변환)")
        private String codeEtc;
    }

    @Getter
    @Setter
    @EqualsAndHashCode
    @Schema(name = "CodeResponseSelect", description = "코드 응답", type = "object")
    public static class Select extends Code {

    }

    @Getter
    @Setter
    @EqualsAndHashCode
    @Schema(name = "CodeDropDown", description = "코드 드롭다운 응답", type = "object")
    public static class CodeDropDown {

        @Schema(description = "코드")
        @Parameter(description = "코드")
        private String codeCd;

        @Schema(description = "코드_명")
        @Parameter(description = "코드_명")
        private String codeNm;
    }

    @Getter
    @Setter
    @EqualsAndHashCode
    @Schema(name = "CodeResponseLowerSelect", description = "하위 코드 응답", type = "object")
    public static class LowerSelect extends Code {

        @Schema(description = "NO")
        @Parameter(description = "NO")
        private Integer no;

        @Schema(description = "상위_코드_명")
        @Parameter(description = "상위_코드_명")
        private String codeUpperNm;

    }

    @Getter
    @Setter
    @EqualsAndHashCode
    @Schema(name = "CodeResponseExcel", description = "코드 엑셀 응답", type = "object")
    public static class Excel extends Code {

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
    @EqualsAndHashCode
    @Schema(name = "CodeResponseChart", description = "코드 프로파일 변수 차트 응답", type = "object")
    public static class Chart extends Code {

        @Schema(required = false)
        @Parameter(description = "기간구분코드")
        private String intervalType;
    }
}
