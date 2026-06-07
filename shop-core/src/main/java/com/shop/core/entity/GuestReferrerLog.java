package com.shop.core.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(name = "GuestReferrerLog", description = "게스트 유입 경로 이력 Entity")
public class GuestReferrerLog {

    @Schema(description = "PK")
    private Long id;

    @Schema(description = "게스트 토큰 ID (tb_guest_token.id)")
    private Long guestTokenId;

    @Schema(description = "유입 경로 (google | instagram | naver | direct 등)")
    private String utmSource;

    @Schema(description = "UTM Medium")
    private String utmMedium;

    @Schema(description = "UTM Campaign")
    private String utmCampaign;

    @Schema(description = "UTM Content")
    private String utmContent;

    @Schema(description = "유입 URL")
    private String refererUrl;

    @Schema(description = "랜딩 URL")
    private String landingUrl;

    @Schema(description = "방문 일시")
    private LocalDateTime visitedAt;
}
