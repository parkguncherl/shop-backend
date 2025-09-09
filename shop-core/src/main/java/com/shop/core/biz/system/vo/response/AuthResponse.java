package com.shop.core.biz.system.vo.response;

import com.shop.core.entity.Auth;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

public class AuthResponse {

    @Getter
    @Setter
    @EqualsAndHashCode(callSuper = false)
    @Schema(name = "AuthResponseEntity", description = "권한 응답")
    public static class Entity extends Auth {

    }

    @Getter
    @Setter
    @EqualsAndHashCode(callSuper = false)
    @Schema(name = "AuthResponseMenuAuth", description = "권한 응답")
    public static class MenuAuth {

        @Schema(required = false)
        @Parameter(description = "상위메뉴명")
        private String upMenuNm;

        @Schema(required = false)
        @Parameter(description = "메뉴명")
        private String menuNm;

        @Schema(required = false)
        @Parameter(description = "읽기권한")
        private String menuReadYn;

        @Schema(required = false)
        @Parameter(description = "수정권한")
        private String menuUpdYn;

        @Schema(required = false)
        @Parameter(description = "엑셀전환 권한")
        private String menuExcelYn;
    }
}
