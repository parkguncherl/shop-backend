package com.shop.core.frontWeb.vo.request;

import com.shop.core.entity.GuestToken;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

public class GuestTokenRequest {

    @Getter
    @Setter
    @EqualsAndHashCode(callSuper = false)
    @Schema(name = "GuestTokenRequestIssue", description = "Guest Token 발급 요청", type = "object")
    public static class Issue extends GuestToken {
        @Schema(description = "기 발급토큰 (있으면 이중발급 방지용)")
        private String existingGuestToken;
    }
}
