package com.shop.core.biz.mypage.vo.request;

import com.shop.core.entity.Favorites;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * <pre>
 * Description: 사용자 Request
 * Date: 2023/03/15 14:45 PM
 * Company: smart90
 * Author: luckeey
 * </pre>
 */
@Schema(name = "FavoritesRequest")
public class FavoritesRequest {

    @Getter
    @Setter
    @EqualsAndHashCode(callSuper = false)
    @Schema(name = "FavoritesListRequest", description = "즐겨찾기 페이징 필터")
    public static class FavoritesListRequest {

        @Schema(description = "사용자 아이디")
        private Integer id;

        @Schema(description = "uri")
        private String menuUri;

        @Schema(description = "권한코드")
        private String authCd;
    }

    @Getter
    @Setter
    @EqualsAndHashCode(callSuper = false)
    @Schema(name = "ModFavoritesRequest", description = "즐겨찾기 수정")
    public static class ModFavoritesRequest {
        @Schema(description = "사용자 아이디")
        private Integer userId;

        @Schema(description = "즐겨찾기 등록여부")
        Boolean isFavorite = false;

        @Schema(description = "메뉴uri")
        private String menuUri;
    }

    @Getter
    @Setter
    @EqualsAndHashCode(callSuper = false)
    @Schema(name = "FavoritesMenuList", description = "즐겨찾기 일괄등록")
    public static class FavoritesMenuList {

        @Schema(description = "메뉴uri")
        private String[] menuUris;
    }
}
