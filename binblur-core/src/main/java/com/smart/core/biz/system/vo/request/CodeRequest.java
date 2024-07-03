package com.binblur.core.biz.system.vo.request;

import com.binblur.core.entity.Code;
import com.binblur.core.enums.SortTypeCode;
import com.binblur.core.interfaces.RequestFilter;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.multipart.MultipartFile;

/**
 * <pre>
 * Description : 코드 Request
 * Date : 2023/03/15 09:50 AM
 * Company : smart90
 * Author : sclee9946
 * </pre>
 */
@Schema(name = "CodeRequest")
public class CodeRequest {

    @Getter
    @Setter
    @EqualsAndHashCode
    @Schema(name = "CodeRequestPagingFilter", description = "코드 페이징 필터")
    @ParameterObject
    public static class PagingFilter implements RequestFilter {

        @Schema(description = "상위_코드")
        @Parameter(description = "상위_코드")
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
    @EqualsAndHashCode
    @Schema(name = "CodeDropDown", description = "코드 드롭다운 파라미터")
    @ParameterObject
    public static class CodeDropDown {

        public CodeDropDown() {
            this.sortColumn = "codeOrder";
            this.sortType = SortTypeCode.ASC;
        }

        @Schema(description = "상위_코드", required = true)
        @Parameter(description = "상위_코드")
        private String codeUpper;

        @Schema(description = "코드_명")
        @Parameter(description = "코드_명")
        private String codeNm;

        @Schema(description = "국가_코드")
        @Parameter(description = "국가_코드")
        private String countryCode;

        @Schema(description = "정렬 컬럼명")
        @Parameter(description = "정렬 컬럼명")
        protected String sortColumn;

        @Schema(description = "정렬 방법", defaultValue = "ASC")
        @Parameter(description = "정렬 방법")
        protected SortTypeCode sortType;
    }

    @Getter
    @Setter
    @EqualsAndHashCode
    @Schema(name = "CodeRequestCreate", description = "코드 생성 요청 파라미터")
    public static class Create implements RequestFilter {

        @Schema(description = "상위_코드")
        private String codeUpper;

        @Schema(description = "코드")
        private String codeCd;

        @Schema(description = "코드_명")
        private String codeNm;

        @Schema(description = "코드_설명")
        private String codeDesc;

        @Schema(description = "코드_기타_정보")
        private String codeEtc1;

        @Schema(description = "코드_기타_정보_영문")
        private String codeEtc2;

        @Schema(description = "코드_순서")
        private Integer codeOrder;

        @Schema(description = "등록자")
        protected String createUser;

        public Code toEntity() {
            return Code.builder()
                .codeUpper(getCodeUpper())
                .codeCd(getCodeCd())
                .codeNm(getCodeNm())
                .codeDesc(getCodeDesc())
                .codeEtc1(getCodeEtc1())
                .codeEtc2(getCodeEtc2())
                .codeOrder(getCodeOrder())
                .createUser(getCreateUser())
                .updateUser(getCreateUser())
                .build();
        }
    }

    @Getter
    @Setter
    @EqualsAndHashCode
    @Schema(name = "CodeRequestUpdate", description = "코드 수정 요청 파라미터")
    public static class Update implements RequestFilter {

        @Schema(description = "아이디")
        private Integer id;

        @Schema(description = "상위_코드")
        private String codeUpper;

        @Schema(description = "코드")
        private String codeCd;

        @Schema(description = "코드_명")
        private String codeNm;

        @Schema(description = "코드_설명")
        private String codeDesc;

        @Schema(description = "코드_기타_정보")
        private String codeEtc1;

        @Schema(description = "코드_기타_정보_Eng")
        private String codeEtc2;

        @Schema(description = "코드_순서")
        private Integer codeOrder;

        @Schema(description = "수정자")
        protected String updateUser;

        @Schema(description = "코드_삭제여부")
        private String delYn;

        public Code toEntity() {
            return Code.builder()
                .id(getId())
                .codeUpper(getCodeUpper())
                .codeCd(getCodeCd())
                .codeNm(getCodeNm())
                .codeDesc(getCodeDesc())
                .codeEtc1(getCodeEtc1())
                .codeEtc2(getCodeEtc2())
                .codeOrder(getCodeOrder())
                .updateUser(getUpdateUser())
                .delYn(getDelYn())
                .build();
        }
    }

    @Getter
    @Setter
    @EqualsAndHashCode
    @Schema(name = "CodeRequestDelete", description = "코드 삭제 요청 파라미터")
    public static class Delete implements RequestFilter {

        @Schema(description = "아이디", required = true)
        @Parameter(description = "아이디")
        private Integer id;

        @Schema(description = "상위_코드")
        private String codeUpper;

        @Schema(description = "코드")
        private String codeCd;

        public Code toEntity() {
            return Code.builder()
                .id(getId())
                .codeUpper(getCodeUpper())
                .codeCd(getCodeCd())
                .build();
        }
    }

    @Getter
    @Setter
    @EqualsAndHashCode
    @Schema(name = "CodeRequestUpsert", description = "코드 생성/수정 요청 파라미터")
    public static class Upsert implements RequestFilter {

        @Schema(description = "아이디")
        private Integer id;

        @Schema(description = "상위_코드")
        private String codeUpper;

        @Schema(description = "코드")
        private String codeCd;

        @Schema(description = "코드_명")
        private String codeNm;

        @Schema(description = "코드_설명")
        private String codeDesc;

        @Schema(description = "코드_기타_정보")
        private String codeEtc1;

        @Schema(description = "코드_순서")
        private Integer codeOrder;

        @Schema(description = "등록자")
        protected String createUser;

        @Schema(description = "수정자")
        protected String updateUser;

        public Code toEntity() {
            return Code.builder()
                .id(getId())
                .codeUpper(getCodeUpper())
                .codeCd(getCodeCd())
                .codeNm(getCodeNm())
                .codeDesc(getCodeDesc())
                .codeEtc1(getCodeEtc1())
                .codeOrder(getCodeOrder())
                .createUser(getCreateUser())
                .updateUser(getUpdateUser())
                .build();
        }
    }

    @Getter
    @Setter
    @EqualsAndHashCode
    @Schema(name = "CodeExcelUpload", description = "코드 엑셀 업로드 파라미터")
    public static class ExcelUpload {

        @Schema(description = "코드 엑셀 업로드 파일")
        MultipartFile uploadFile;
    }
}
