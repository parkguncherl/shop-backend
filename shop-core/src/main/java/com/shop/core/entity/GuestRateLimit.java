package com.shop.core.entity;

import com.shop.core.interfaces.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "GuestRateLimit", description = "Guest Rate Limit Entity")
public class GuestRateLimit {

    @Schema(description = "아이디(PK)")
    private Long id;

    @Schema(description = "Guest ID")
    private String guestId;

    @Schema(description = "분단위 키(yyyyMMddHHmm)")
    private String minuteKey;

    @Schema(description = "요청 횟수")
    private Integer count;

    @Schema(description = "생성일")
    private LocalDateTime creTm;

}