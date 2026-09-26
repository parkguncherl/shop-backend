package com.shop.core.biz.system.vo.request;

import com.shop.core.entity.UserCode;
import com.shop.core.interfaces.RequestFilter;
import com.shop.core.biz.system.vo.response.UserCodeResponse;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;

/**
 * <pre>
 * Description: 코드 Request
 * Date: 2023/03/15 09:50 AM
 * Company: smart90
 * Author: luckeey
 * </pre>
 */
@Schema(name = "UserCodeRequest")
public class UserCodeRequest {

    @Getter
    @Setter
    @EqualsAndHashCode(callSuper = false)
    @Schema(name = "UserCodeRequestPagingFilter", description = "코드 페이징 필터")
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
    @EqualsAndHashCode(callSuper = false)
    @Schema(name = "UserCodeDropDownRequest", description = "코드 드롭다운 파라미터")
    @ParameterObject
    public static class UserCodeDropDown {

        @Schema(description = "유저아이디")
        private Integer userId;

        @Schema(description = "상위_코드")
        private String codeUpper;

        @Schema(description = "코드_명")
        private String codeNm;

        @Schema(description = "코드")
        private String codeCd;

        @Schema(description = "정렬타입")
        private String orderType;

        @Schema(description = "검색조건키워드")
        private String searchKeyword;

        @Schema(description = "검색조건키워드 etc val")
        private String codeEtc;
    }

    @Getter
    @Setter
    @EqualsAndHashCode(callSuper = false)
    @Schema(name = "UserCodeRequestCreate", description = "코드 생성 요청 파라미터")
    public static class Create implements RequestFilter {

        @Schema(description = "생성타입")
        private String createType;

        @Schema(description = "유저코드 목록")
        private ArrayList<UserCodeResponse.LowerSelect> userCodeLowerSelectList;
    }


    @Getter
    @Setter
    @EqualsAndHashCode(callSuper = false)
    @Schema(name = "UserCodeRequestUpdateCodeVal", description = "코드 변경 요청 파라미터")
    public static class UpdateUserCodeVal {

        @Schema(description = "유저아이디")
        private Integer userId;

        @Schema(description = "코드상위코드")
        private String codeUpper;

        @Schema(description = "코드")
        private String codeCd;

        @Schema(description = "코드값")
        private String codeNm;
    }

    @Getter
    @Setter
    @EqualsAndHashCode(callSuper = false)
    @Schema(name = "UserCodeRequestUpdate", description = "코드 수정 요청 파라미터")
    public static class Update implements RequestFilter {

        @Schema(description = "아이디")
        private Integer id;

        @Schema(description = "유저아이디")
        private Integer userId;

        @Schema(description = "상위_코드")
        private String codeUpper;

        @Schema(description = "코드")
        private String codeCd;

        @Schema(description = "코드_명")
        private String codeNm;

        @Schema(description = "코드_설명")
        private String codeDesc;

        @Schema(description = "코드_기본값")
        private String defCodeVal;

        @Schema(description = "코드_순서")
        private Integer codeOrder;

        @Schema(description = "수정자")
        protected String updUser;

        @Schema(description = "코드_삭제여부")
        private String delYn;

        public UserCode toEntity() {
            return UserCode.builder()
                    .codeUpper(getCodeUpper())
                    .codeCd(getCodeCd())
                    .codeNm(getCodeNm())
                    .codeDesc(getCodeDesc())
                    .defCodeVal(getDefCodeVal())
                    .userId(getUserId())
                    .codeOrder(getCodeOrder())
                    .build();
        }

    }

    @Getter
    @Setter
    @EqualsAndHashCode(callSuper = false)
    @Schema(name = "UserCodeRequestDelete", description = "코드 삭제 요청 파라미터")
    public static class Delete implements RequestFilter {

        @Schema(description = "아이디", required = true)
        @Parameter(description = "아이디")
        private Integer id;

        @Schema(description = "상위_코드")
        private String codeUpper;

        @Schema(description = "코드")
        private String codeCd;

        public UserCode toEntity() {
            return UserCode.builder()
                .id(getId())
                .codeUpper(getCodeUpper())
                .codeCd(getCodeCd())
                .build();
        }
    }

    @Getter
    @Setter
    @EqualsAndHashCode(callSuper = false)
    @Schema(name = "UserCodeRequestSoftDelete", description = "코드 소프트삭제 요청 파라미터")
    public static class SoftDelete {

        @Schema(description = "아이디", requiredMode = Schema.RequiredMode.REQUIRED)
        @Parameter(description = "아이디")
        private Integer id;

        @Schema(description = "유저아이디")
        private Integer userId;

        @Schema(description = "수정자")
        protected String updUser;
    }

    @Getter
    @Setter
    @EqualsAndHashCode(callSuper = false)
    @Schema(name = "UserCodeRequestUpdateDefCodeVal", description = "코드 할인율변경(def_code_val)  요청 파라미터")
    public static class UpdateDefCodeVal {

        @Schema(description = "아이디", requiredMode = Schema.RequiredMode.REQUIRED)
        @Parameter(description = "아이디")
        private Integer id;

        @Schema(description = "기본값")
        private Integer defCodeVal;
    }

    @Getter
    @Setter
    @EqualsAndHashCode(callSuper = false)
    @Schema(name = "UserCodeRequestUpsert", description = "코드 생성/수정 요청 파라미터")
    public static class Upsert extends UserCode {
        public UserCode toEntity() {
            return UserCode.builder()
                .id(getId())
                .codeUpper(getCodeUpper())
                .codeCd(getCodeCd())
                .codeNm(getCodeNm())
                .codeDesc(getCodeDesc())
                .defCodeVal(getDefCodeVal())
                .userId(getUserId())
                .codeOrder(getCodeOrder())
                .codeEtc(getCodeEtc())
                .codeEtc1(getCodeEtc1())
                .codeEtc2(getCodeEtc2())
                .codeEtc3(getCodeEtc3())
                .creUser(getCreUser())
                .updUser(getUpdUser())
                .build();
        }
    }

    @Getter
    @Setter
    @EqualsAndHashCode(callSuper = false)
    @Schema(name = "UserCodeExcelUpload", description = "코드 엑셀 업로드 파라미터")
    public static class ExcelUpload {

        @Schema(description = "코드 엑셀 업로드 파일")
        MultipartFile uploadFile;
    }
}
