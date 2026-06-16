package com.shop.core.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(name = "Review", description = "상품 리뷰 entity")
public class Review {

    private Long id;
    private Long socialAccountId;
    private Long orderItemId;
    private Long productId;
    private Long productDetId;
    private Integer rating;
    private String content;
    private Long fileId;
    private String isBlinded;
    private String delYn;
    private LocalDateTime creTm;
    private LocalDateTime uptTm;
}
