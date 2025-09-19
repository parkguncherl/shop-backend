package com.shop.core.interfaces;

import com.shop.core.enums.BooleanValueCode;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * <pre>
 * Description: 기본 Entity
 * Date: 2023/01/26 12:35 PM
 * Company: smart90
 * Author: luckeey
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
    //@JsonIgnore  // JSON 직렬화/역직렬화에서 완전히 제외
    protected LocalDateTime creTm;

    /** 등록자 */
    @Schema(description = "등록자")
    //@JsonIgnore  // JSON 직렬화/역직렬화에서 완전히 제외
    protected String creUser;

    /** 수정일시 */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "수정일시")
    //@JsonIgnore  // JSON 직렬화/역직렬화에서 완전히 제외
    protected LocalDateTime updTm;

    /** 수정자 */
    @Schema(description = "수정자")
    //@JsonIgnore  // JSON 직렬화/역직렬화에서 완전히 제외
    protected String updUser;

    /** 삭제여부 */
    @Schema(description = "삭제여부")
    protected BooleanValueCode deleteYn;

    /** 페이징 조회에만 사용 */
    // 접근자가 무시되게끔 하는 본 에너테이션은 비활성화
    // @JsonIgnore
    protected Integer totalRowCount;

    /**
     * 목록에서의 순번을 나타내는 필드입니다.
     * 클라이언트 측에서 항목의 순서를 표시하는 데 사용될 수 있습니다.
     */
    @Schema(description = "번호")
    protected Integer no;

    @Schema(description = "삭제여부")
    @JsonIgnore  // JSON 직렬화/역직렬화에서 완전히 제외
    protected String delYn;

}
