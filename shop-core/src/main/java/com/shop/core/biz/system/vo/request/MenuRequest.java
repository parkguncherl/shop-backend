package com.shop.core.biz.system.vo.request;

import com.shop.core.biz.system.vo.response.MenuResponse;
import com.shop.core.entity.Menu;
import com.shop.core.interfaces.RequestFilter;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <pre>
 * Description: 메뉴 Request
 * Date: 2023/02/20 11:58 AM
 * Company: smart90
 * Author : luckeey
 * </pre>
 */
@Schema(name = "MenuRequest")
public class MenuRequest {

    @Getter
    @Setter
    @EqualsAndHashCode(callSuper = false)
    @Schema(name = "MenuRequestPagingFilter", description = "Menu 페이징 필터")
    @ParameterObject
    public static class PagingFilter implements RequestFilter {

        @Schema(description = "상위코드", required = false)
        @Parameter(description = "상위코드")
        private String upMenuCd;
    }

    @Getter
    @Setter
    @EqualsAndHashCode(callSuper = false)
    @Schema(name = "MenuRequestCreate", description = "Menu 생성")
    public static class Create extends Menu {

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
                .upMenuCd(getUpMenuCd())
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
    @EqualsAndHashCode(callSuper = false)
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
    @EqualsAndHashCode(callSuper = false)
    @Schema(name = "MenuRequestDelete", description = "Menu 삭제")
    public static class Delete extends Menu {

    }

    @Getter
    @Setter
    @EqualsAndHashCode(callSuper = false)
    @Schema(name = "MenuUpload", description = "Menu Upload")
    public static class FileUpload {

        MultipartFile uploadFile;
    }

    @Getter
    @Setter
    @EqualsAndHashCode(callSuper = false)
    @Schema(name = "MenuRequestExcelDynamicParam", description = "메뉴 엑셀 동적 파라미터 요청")
    public static class ExcelDynamicParam {

        @Schema(required = true)
        @Parameter(description = "엑셀 헤더 값(10020)")
        private String selectQuery;

        @Schema(required = true)
        @Parameter(description = "동적 코드 값(10020)")
        private String inQuery;
    }

    @Getter
    @Setter
    @EqualsAndHashCode(callSuper = false)
    @Schema(name = "MenuRequestWithAuth", description = "특정 사용자의 메뉴 인가 관련 정보 수정에 필요한 정보들의 집합")
    public static class WithAuth {

        @Parameter(description = "사용자 id")
        private Integer userId;

        @Parameter(description = "인가 정보 목록")
        private List<MenuRequest.WithAuthElement> withAuthList;
    }

    @Getter
    @Setter
    @EqualsAndHashCode(callSuper = false)
    @Schema(name = "MenuRequestWithAuthElement", description = "특정 사용자의 메뉴 인가 관련 정보 수정")
    public static class WithAuthElement extends Menu {

        @Parameter(description = "인가 여부")
        private String authYn;

        @Parameter(description = "하위메뉴 목록")
        private List<MenuRequest.WithAuthSubElement> items;
    }

    @Getter
    @Setter
    @EqualsAndHashCode(callSuper = false)
    @Schema(name = "MenuRequestWithAuthSubElement", description = "특정 사용자의 메뉴 인가 관련 정보 수정(실제로 수정될 하위 요소들)")
    public static class WithAuthSubElement extends Menu {

        @Parameter(description = "인가 여부")
        private String authYn;

    }


}

