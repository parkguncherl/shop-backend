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

    @Schema(description = "만료일시")
    private LocalDateTime expireDate;

    @Schema(description = "생성일")
    private LocalDateTime creTm;

}