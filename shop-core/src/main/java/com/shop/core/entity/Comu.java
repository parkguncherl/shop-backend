package com.shop.core.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(name = "Comu", description = "고객 상담 entity")
public class Comu {

    private Long id;

    @Schema(description = "상담 유형 코드 (code_upper=10130)")
    private String comuType;

    @Schema(description = "연관 이벤트 ID (주문 ID)")
    private Long eventId;

    @Schema(description = "등록자 (닉네임 or 이메일)")
    private String creUser;

    private LocalDateTime creTm;
    private String delYn;
}
