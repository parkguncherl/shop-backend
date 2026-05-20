package com.shop.core.entity;

import com.shop.core.interfaces.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "GuestToken", description = "Guest Token Entity")
public class GuestToken {

    @Schema(description = "아이디(PK)")
    private Long id;

    @Schema(description = "Guest ID")
    private String guestId;

    @Schema(description = "Guest Token")
    private String guestToken;

    @Schema(description = "클라이언트 IP")
    private String clientIp;

    @Schema(description = "User Agent")
    private String userAgent;

    @Schema(description = "디바이스 타입")
    private String deviceType;

    @Schema(description = "OS")
    private String os;

    @Schema(description = "브라우저")
    private String browser;

    @Schema(description = "유입 URL")
    private String refererUrl;

    @Schema(description = "착륙 URL")
    private String currentUrl;

    @Schema(description = "UTM Source")
    private String utmSource;

    @Schema(description = "UTM Medium")
    private String utmMedium;

    @Schema(description = "UTM Campaign")
    private String utmCampaign;

    @Schema(description = "UTM content") // 어떻게 클릭했는지 link_in_bio, story_ad. feed_ad 인스타에서
    private String utmContent;

    @Schema(description = "연결된 회원 ID")
    private Integer memberId;

    @Schema(description = "페이스북연결id")
    private String fbclid;

    @Schema(description = "만료일시")
    private LocalDateTime expireDate;
}