package com.binblur.core.interfaces;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.binblur.core.enums.BooleanValueCode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * <pre>
 * Description : 기본 Entity
 * Date : 2023/01/26 12:35 PM
 * Company : smart90
 * Author : harry
 * </pre>
 */
@Slf4j
@Setter
@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Schema(description = "기본 Entity")
public class BaseEntity {

    /** 등록일시 */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "등록일시")
    protected LocalDateTime createDateTime;

    /** 등록자 */
    @Schema(description = "등록자")
    protected String createUser;

    /** 수정일시 */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "수정일시")
    protected LocalDateTime updateDateTime;

    /** 수정자 */
    @Schema(description = "수정자")
    protected String updateUser;

    /** 삭제여부 */
    @Schema(description = "삭제여부")
    protected BooleanValueCode deleteYn;

    /** 페이징 조회에만 사용 */
    // 접근자가 무시되게끔 하는 본 에너테이션은 비활성화
    // @JsonIgnore
    protected Long totalRowCount;
}
