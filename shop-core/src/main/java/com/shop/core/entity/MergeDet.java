package com.shop.core.entity;

import com.shop.core.entity.adapter.LocalDateSerializer;
import com.shop.core.entity.adapter.LocalDateTimeSerializer;
import com.shop.core.entity.adapter.LocalTimeSerializer;
import com.shop.core.interfaces.BaseEntity;
import com.google.gson.GsonBuilder;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/** 판매처통합상세 */
@Getter
@Setter
@EqualsAndHashCode
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "판매처통합상세 Entity")
public class MergeDet extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 225821297973032425L;

    /** 아이디(PK) */
    @Schema(description = "아이디(PK)")
    private Integer id;

    /** 통합이력ID(FK) */
    @Schema(description = "통합이력ID(FK)")
    private Integer mergeSellerID;

    /** 변경구분 */
    @Schema(description = "변경구분")
    private String changeTp;

    /** 변경ID */
    @Schema(description = "변경ID")
    private String changeId;

    @Override
    public String toString() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateSerializer());
        gsonBuilder.registerTypeAdapter(LocalTime.class, new LocalTimeSerializer());
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer());

        return gsonBuilder.setPrettyPrinting().create().toJson(this);
    }
}