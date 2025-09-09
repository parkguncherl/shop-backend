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
@Schema(description = "실사 Entity")
public class Inspect extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1175736457398771772L;

    @Schema(description = "아이디(PK)")
    private Integer id;

    @Schema(description = "실사구분")
    private String inspectCd;

    @Schema(description = "실사명")
    private String inspectTitle;

    @Schema(description = "실사상태")
    private String inspectStatCd;

    @Schema(description = "실사신청일자")
    private Integer logisId;

    @Override
    public String toString() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateSerializer());
        gsonBuilder.registerTypeAdapter(LocalTime.class, new LocalTimeSerializer());
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer());

        return gsonBuilder.setPrettyPrinting().create().toJson(this);
    }
}