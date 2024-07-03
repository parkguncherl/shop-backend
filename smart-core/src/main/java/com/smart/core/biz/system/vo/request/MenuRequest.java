package com.smart.core.biz.system.vo.request;

import com.smart.core.entity.Menu;
import com.smart.core.interfaces.RequestFilter;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.multipart.MultipartFile;

/**
 * <pre>
 * Description : 메뉴 Request
 * Date : 2023/02/20 11:58 AM
 * Company : smart90
 * Author : sclee9946
 * </pre>
 */
@Schema(name = "MenuRequest")
public class MenuRequest {

    @Getter
    @Setter
    @EqualsAndHashCode
    @Schema(name = "MenuRequestPagingFilter", description = "Menu 페이징 필터")
    @ParameterObject
    public static class PagingFilter implements RequestFilter {

        @Schema(description = "상위코드", required = false)
        @Parameter(description = "상위코드")
        private String upMenuCd;
    }

    @Getter
    @Setter
    @EqualsAndHashCode
    @Schema(name = "MenuRequestCreate", description = "Menu 생성")
    public static class Create extends Menu {

        @Schema(description = "상위코드", required = false)
        private String upMenuCd;

        /**
         * 전체권한
         */
        @Schema(description = "전체권한", required = false)
        private String auths;

        /**
         * 권한
         */
        @Schema(description = "권한", required = false)
        private String authCd;

        /**
         * 국가코드
         */
        @Schema(description = "국가코드", required = false)
        private String countryCode;

        public Menu toEntity() {
            return Menu.builder()
                .upMenuCd(upMenuCd)
                .upMenuNm(getUpMenuNm())
                .menuCd(getMenuCd())
                .menuOrder(getMenuOrder())
                .menuNm(getMenuNm())
                .menuEngNm(getMenuEngNm())
                .menuUri(getMenuUri())
                .build();
        }
    }

    @Getter
    @Setter
    @EqualsAndHashCode
    @Schema(name = "MenuRequestUpdate", description = "Menu 수정")
    public static class Update extends Menu {

        @Schema(description = "상위코드", required = false)
        private String upMenuCd;

        /**
         * 전체권한
         */
        @Schema(description = "전체권한", required = false)
        private String auths;

        /**
         * 권한
         */
        @Schema(description = "권한", required = false)
        private String authCd;

        /**
         * 국가코드
         */
        @Schema(description = "국가코드", required = false)
        private String countryCode;
    }

    @Getter
    @Setter
    @EqualsAndHashCode
    @Schema(name = "MenuRequestDelete", description = "Menu 삭제")
    public static class Delete extends Menu {

    }

    @Getter
    @Setter
    @EqualsAndHashCode
    @Schema(name = "MenuUpload", description = "Menu Upload")
    public static class FileUpload {

        MultipartFile uploadFile;
    }

    @Getter
    @Setter
    @EqualsAndHashCode
    @Schema(name = "MenuRequestExcelDynamicParam", description = "메뉴 엑셀 동적 파라미터 요청")
    public static class ExcelDynamicParam {

        @Schema(required = true)
        @Parameter(description = "엑셀 헤더 값(S0002)")
        private String selectQuery;

        @Schema(required = true)
        @Parameter(description = "동적 코드 값(S0002)")
        private String inQuery;
    }
}

