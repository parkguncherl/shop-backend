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
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/** 스큐 */
@Getter
@Setter
@EqualsAndHashCode
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "스큐 Entity")
public class Money extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 8572972195565163551L;

    @Schema(description = "아이디(PK)")
    private Integer id;

    @Schema(description = "파트너id")
    private Integer partnerId;

    @Schema(description = "영업일")
    private LocalDate workYmd;

    @Schema(description = "백만원")
    private Integer won1000000;

    @Schema(description = "십만원")
    private Integer won100000;

    @Schema(description = "오만원")
    private Integer won50000;

    @Schema(description = "만원")
    private Integer won10000;

    @Schema(description = "오천원")
    private Integer won5000;

    @Schema(description = "천원")
    private Integer won1000;

    /*@Schema(description = "오백원")
    private Integer won500;

    @Schema(description = "백원")
    private Integer won100;*/

    @Schema(description = "기타금액")
    private BigDecimal wonEtc;

    @Schema(description = "돈통총금액")
    private BigDecimal wonTot;

    @Schema(description = "정산백만원")
    private Integer sett1000000;

    @Schema(description = "정산십만원")
    private Integer sett100000;

    @Schema(description = "정산오만원")
    private Integer sett50000;

    @Schema(description = "정산만원")
    private Integer sett10000;

    @Schema(description = "정산오천원")
    private Integer sett5000;

    @Schema(description = "정산천원")
    private Integer sett1000;

    /*@Schema(description = "정산오백원")
    private Integer sett500;

    @Schema(description = "정산백원")
    private Integer sett100;*/

    @Schema(description = "정산기타금액")
    private BigDecimal settEtc;

    @Schema(description = "정산총금액")
    private BigDecimal settTot;

    @Override
    public String toString() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateSerializer());
        gsonBuilder.registerTypeAdapter(LocalTime.class, new LocalTimeSerializer());
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer());

        return gsonBuilder.setPrettyPrinting().create().toJson(this);
    }
}