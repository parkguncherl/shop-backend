package com.shop.core.frontWeb.vo.request;

import com.shop.core.entity.GuestToken;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

public class GuestTokenRequest {


    @Getter
    @Setter
    @EqualsAndHashCode(callSuper = false)
    @SuperBuilder
    @Schema(name = "GuestTokenRequestGuestTokenInfo", description = "Guest Token 정보", type = "object")
    public static class Issue extends GuestToken {
        String subDomain;
    }
}
