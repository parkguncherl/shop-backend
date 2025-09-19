package com.shop.core.entity;

import com.google.gson.GsonBuilder;
import com.shop.core.entity.adapter.LocalDateSerializer;
import com.shop.core.entity.adapter.LocalDateTimeSerializer;
import com.shop.core.entity.adapter.LocalTimeSerializer;
import com.shop.core.interfaces.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * <pre>
 * Description: 공지사항 Entity
 * Company: shop
 * Author: park
 * </pre>
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "Notice", description = "공지사항 Entity")
public class Notice extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 171965629871820157L;

    /** 아이디(PK) */
    @Schema(description = "아이디(PK)")
    private Integer id;

    /** 파트너id(FK) */
    @Schema(description = "파트너id")
    private Integer partnerId;

    /** 공지사항 코드 */
    @Schema(description = "공지사항 코드")
    private String noticeCd;

    /** 제목 */
    @Schema(description = "제목")
    private String title;

    /** 공지사항 내용 */
    @Schema(description = "공지사항 내용")
    private String noticeCntn;

    /** 이동 URI */
    @Schema(description = "이동 URI")
    private String moveUri;

    /** 권한 코드 */
    @Schema(description = "권한 코드")
    private String authCds;

    /** 조회수 */
    @Schema(description = "조회수")
    private String readCnt;


    @Override
    public String toString() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateSerializer());
        gsonBuilder.registerTypeAdapter(LocalTime.class, new LocalTimeSerializer());
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer());

        return gsonBuilder.setPrettyPrinting().create().toJson(this);
    }
}
