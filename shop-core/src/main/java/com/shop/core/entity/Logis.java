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
@Schema(description = "창고 Entity")
public class Logis extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = -7841559252697554138L;

    @Schema(description = "아이디(PK)")
    private Integer id;

    @Schema(description = "물류창고KEY")
    private String logisKey;

    @Schema(description = "물류창고명")
    private String logisNm;

    @Schema(description = "물류창고위치")
    private String logisAddr;

    @Schema(description = "물류창고설명")
    private String logisDesc;

    @Schema(description = "회사 전화 번호")
    private String logisTelNo;

    @Schema(description = "담당자 명")
    private String personNm;

    @Schema(description = "담당자 전화 번호")
    private String personTelNo;

    @Schema(description = "상세 정보")
    private String centerInfo;

    @Override
    public String toString() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateSerializer());
        gsonBuilder.registerTypeAdapter(LocalTime.class, new LocalTimeSerializer());
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer());

        return gsonBuilder.setPrettyPrinting().create().toJson(this);
    }
}