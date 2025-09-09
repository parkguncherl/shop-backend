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
@Schema(description = "위치 Entity")
public class Loc extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 6241526086965303161L;

    @Schema(description = "아이디")
    private Integer id;

    @Schema(description = "화주ID")
    private Integer partnerId;

    @Schema(description = "ZONE ID")
    private Integer zoneId;

    @Schema(description = "LOC X")
    private Integer locX;

    @Schema(description = "LOC Y")
    private Integer locY;

    @Schema(description = "위치명")
    private String locAlias;

    @Schema(description = "위치설명")
    private String locDesc;

    @Override
    public String toString() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateSerializer());
        gsonBuilder.registerTypeAdapter(LocalTime.class, new LocalTimeSerializer());
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer());

        return gsonBuilder.setPrettyPrinting().create().toJson(this);
    }
}