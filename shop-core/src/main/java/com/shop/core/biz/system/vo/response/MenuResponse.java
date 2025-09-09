package com.shop.core.biz.system.vo.response;

import com.shop.core.entity.LeftMenuSub;
import com.shop.core.entity.Menu;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class MenuResponse {

    @Getter
    @Setter
    @EqualsAndHashCode(callSuper = false)
    @Schema(name = "MenuResponsePaging", description = "메뉴 페이징 응답")
    public static class Paging extends Menu {

        @Schema(required = false)
        @Parameter(description = "전체 카운트")
        protected Integer totalRowCount;

        @Schema(required = false)
        @Parameter(description = "NO")
        private Integer no;

        @Schema(required = false)
        @Parameter(description = "상위 코드 명")
        private String menuUpperNm;

        @Schema(required = false)
        @Parameter(description = "하위 메뉴 수")
        private Integer lowerMenuCnt;

        @Schema(required = false)
        @Parameter(description = "권한")
        private String auths;
    }

    @Getter
    @Setter
    @EqualsAndHashCode(callSuper = false)
    @Schema(name = "MenuResponseExcelDynamicParam", description = "메뉴 엑셀 동적 파라미터 응답")
    public static class ExcelDynamicParam {

        @Schema(required = true)
        @Parameter(description = "엑셀 헤더 명(10020)")
        private String excelHeader;

        @Schema(required = true)
        @Parameter(description = "엑셀 컬럼(10020)")
        private String excelColumn;

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
    @Schema(name = "MenuResponseWithAuth", description = "특정 사용자에게 인가 여부가 담긴 상위 메뉴 및 하위메뉴 목록 응답")
    public static class WithAuth extends Menu {

        @Parameter(description = "인가 여부")
        private String authYn;

        @Parameter(description = "하위메뉴 목록")
        private List<WithAuthSub> items;
    }

    @Getter
    @Setter
    @EqualsAndHashCode(callSuper = false)
    @Schema(name = "MenuResponseWithAuthSub", description = "특정 사용자에 관한 인가 여부가 담긴 (하위)메뉴 정보 응답")
    public static class WithAuthSub extends Menu {

        @Parameter(description = "인가 여부")
        private String authYn;
    }
}
