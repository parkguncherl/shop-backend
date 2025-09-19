package com.shop.core.biz.mypage.vo.response;

import com.shop.core.entity.Code;
import com.shop.core.entity.Favorites;
import com.shop.core.entity.User;
import com.shop.core.enums.BooleanValueCode;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * <pre>
 * Description: 사용자 Response
 * Date: 2023/02/27 16:57 PM
 * Company: smart90
 * Author: luckeey
 * </pre>
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class FavoritesResponse {

    @Getter
    @Setter
    @EqualsAndHashCode
    @Schema(name = "SelectFavorites", description = "즐겨찾기 응답", type = "object")
    public static class SelectFavorites {
        @Schema(required = false)
        @Parameter(description = "메뉴uri")
        private String menuUri;

        @Schema(required = false)
        @Parameter(description = "메뉴명")
        private String menuNm;
    }
}
