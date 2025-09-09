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

@Getter
@Setter
@EqualsAndHashCode
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "실사상세 Entity")
public class InspectDet extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = -4700771317357355455L;

    @Schema(description = "아이디(PK)")
    private Integer id;

    @Schema(description = "실사대상ID(FK)")
    private Integer inspectId;

    @Schema(description = "스큐ID")
    private Integer skuId;

    @Schema(description = "로케이션ID")
    private Integer locId;

    @Schema(description = "변경전수량")
    private Integer befCnt;

    @Schema(description = "변경후수량")
    private Integer aftCnt;

    @Schema(description = "변경여부")
    private String chgYn;

    @Schema(description = "skuNm")
    private String skuNm;

    @Override
    public String toString() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateSerializer());
        gsonBuilder.registerTypeAdapter(LocalTime.class, new LocalTimeSerializer());
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer());

        return gsonBuilder.setPrettyPrinting().create().toJson(this);
    }
}