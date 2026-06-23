package com.shop.core.entity;

import com.shop.core.interfaces.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "PageViewLog", description = "페이지 뷰 로그 Entity")
public class PageViewLog extends BaseEntity {

    @Schema(description = "아이디(PK)")
    private Long id;

    @Schema(description = "Guest ID")
    private String guestId;

    @Schema(description = "회원 ID")
    private Integer memberId;

    @Schema(description = "페이지 타입 (main, product, category, cart)")
    private String pageType;

    @Schema(description = "페이지 URL")
    private String pageUrl;

    @Schema(description = "이전 상품 ID (어떤 상품을 보다가 진입했는지)")
    private Integer befProductId;

    @Schema(description = "상품 ID")
    private Integer productId;

    @Schema(description = "카테고리 코드")
    private String categoryCd;

    @Schema(description = "머문 시간 (초)")
    private Integer staySeconds;

    @Schema(description = "스크롤 깊이 (%)")
    private Integer scrollDepth;

    @Schema(description = "최대 스크롤 위치 (%)")
    private Integer scrollMax;

    @Schema(description = "바로 나갔는지 (Y/N)")
    private String isBounce;

    @Schema(description = "디바이스 타입")
    private String deviceType;
}