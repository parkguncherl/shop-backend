package com.binblur.core.biz.system.vo.response;

import com.binblur.core.entity.Menu;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

public class MenuResponse {

    @Getter
    @Setter
    @EqualsAndHashCode(callSuper = false)
    @Schema(name = "MenuResponsePaging", description = "메뉴 페이징 응답")
    public static class Paging extends Menu {

        @Schema(required = false)
        @Parameter(description = "전체 카운트")
        protected Long totalRowCount;

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
        @Parameter(description = "엑셀 헤더 명(S0002)")
        private String excelHeader;

        @Schema(required = true)
        @Parameter(description = "엑셀 컬럼(S0002)")
        private String excelColumn;

        @Schema(required = true)
        @Parameter(description = "엑셀 헤더 값(S0002)")
        private String selectQuery;

        @Schema(required = true)
        @Parameter(description = "동적 코드 값(S0002)")
        private String inQuery;
    }
}
