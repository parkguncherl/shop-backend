package com.shop.core.biz.system.vo.request;

import com.shop.core.entity.Auth;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

/**
 * <pre>
 * Description: 권한 Request
 * Date: 2023/02/20 11:58 AM
 * Company: smart90
 * Author : luckeey
 * </pre>
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class AuthRequest {

    @Getter
    @Setter
    @Schema(name = "AuthRequestCreate", description = "Auth Create")
    public static class Create extends Auth {

        /**
         * 권한목록
         */
        @Schema(description = "권한 목록", required = false)
        private ArrayList<Auth> authList;
    }

    @Getter
    @Setter
    @EqualsAndHashCode(callSuper = false)
    @Schema(name = "AuthRequestMenuAuth", description = "메뉴 권한 플래그 요청 파라미터")
    public static class MenuAuth {

        @Schema(required = false)
        @Parameter(description = "사용자 ID")
        private Integer userId;

        @Schema(required = false)
        @Parameter(description = "권한코드")
        private String authCd;

        @Schema(required = false)
        @Parameter(description = "메뉴 URI")
        private String menuUri;
    }
}
