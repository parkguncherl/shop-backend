package com.shop.core.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(name = "ComuDet", description = "고객 상담 메시지 entity")
public class ComuDet {

    private Long id;

    @Schema(description = "상담 ID (tb_comu.id)")
    private Long comuId;

    @Schema(description = "질문 여부 Y=고객 질문 / N=관리자 답변")
    private String reqYn;

    @Schema(description = "메시지 내용")
    private String comuCntn;

    @Schema(description = "첨부 파일 ID (tb_file.id)")
    private Integer fileId;

    @Schema(description = "등록자 (닉네임 or 이메일)")
    private String creUser;

    @Schema(description = "읽음 여부 Y=읽음 / N=안 읽음")
    private String readYn;

    private LocalDateTime creTm;
    private String delYn;
}
