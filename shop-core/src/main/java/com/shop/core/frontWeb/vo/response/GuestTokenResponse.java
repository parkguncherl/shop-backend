package com.shop.core.frontWeb.vo.response;

import com.shop.core.entity.GuestToken;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

public class GuestTokenResponse {

    @Getter
    @Setter
    @EqualsAndHashCode(callSuper = false)
    @Schema(name = "GuestTokenResponseGuestTokenInfo", description = "Guest Token 정보", type = "object")
    public static class GuestTokenInfo extends GuestToken {

    }
}