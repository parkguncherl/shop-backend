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

/** 프린터설정 */
@Getter
@Setter
@EqualsAndHashCode
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "프린터설정 Entity")
public class PartnerPrint extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 928931189307576352L;

    @Schema(description = "아이디(PK)")
    private Integer id;

    @Schema(description = "파트너ID(FK)")
    private Integer partnerId;

    @Schema(description = "파일ID(FK)")
    private Integer fileId;

    @Schema(description = "이미지인쇄여부")
    private String logoprintyn;

    @Schema(description = "로고위치")
    private String logoLocCd;

    @Schema(description = "타이틀_여부")
    private String titleYn;

    @Schema(description = "타이틀_관리")
    private String titleMng;

    @Schema(description = "타이틀_일반")
    private String titleNor;

    @Schema(description = "상단_여부")
    private String topYn;

    @Schema(description = "상단_관리")
    private String topMng;

    @Schema(description = "상단_일반")
    private String topNor;

    @Schema(description = "하단_여부")
    private String bottomYn;

    @Schema(description = "하단_관리")
    private String bottomMng;

    @Schema(description = "하단_일반")
    private String bottomNor;

    @Override
    public String toString() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateSerializer());
        gsonBuilder.registerTypeAdapter(LocalTime.class, new LocalTimeSerializer());
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer());

        return gsonBuilder.setPrettyPrinting().create().toJson(this);
    }
}