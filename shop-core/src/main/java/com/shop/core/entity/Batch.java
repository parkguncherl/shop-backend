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
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "배치 Entity")
public class Batch extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 8551756517112871231L;

    @Schema(description = "아이디(PK)")
    private Integer id;

    @Schema(description = "배치유형")
    private String batchTp;

    @Schema(description = "시작일시")
    private LocalDateTime startTm;

    @Schema(description = "종료일시")
    private LocalDateTime endTm;

    @Schema(description = "대상건")
    private Integer targetCnt;

    @Schema(description = "처리건")
    private Integer tranCnt;

    @Schema(description = "미처리건")
    private Integer failCnt;

    @Schema(description = "결과코드")
    private String rsltCd;

    @Schema(description = "결과메시지")
    private String message;


    @Override
    public String toString() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateSerializer());
        gsonBuilder.registerTypeAdapter(LocalTime.class, new LocalTimeSerializer());
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer());

        return gsonBuilder.setPrettyPrinting().create().toJson(this);
    }
}