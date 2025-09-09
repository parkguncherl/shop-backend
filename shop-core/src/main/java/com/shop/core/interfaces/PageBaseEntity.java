package com.shop.core.interfaces;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * Description: 페이징 Response를 위한 공통 Entity
 * Date: 2025/01/09
 */
@Slf4j
@Setter
@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Schema(description = "기본 Entity")
public class PageBaseEntity {

    @Schema(description = "번호")
    protected Integer no;

    @Schema(description = "전체조회수량")
    protected Integer totalRowCount;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "등록일시")
    protected LocalDateTime creTm;

    @Schema(description = "등록자")
    protected String creUser;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "수정일시")
    protected LocalDateTime updTm;

    @Schema(description = "수정자")
    protected String updUser;

    @Schema(description = "삭제여부")
    protected String delYn;
}
