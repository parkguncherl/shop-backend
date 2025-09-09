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

/**
 * <pre>
 * Description: sample log Entity
 * Company: home
 * Author : park
 * </pre>
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "SampleLog", description = "샘플로그 Entity")
public class SampleLog extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 3051333954958954521L;

    @Schema(description = "아이디(PK)")
    private Integer id;

    @Schema(description = "주문상세id(FK)")
    private Integer orderDetId;

    @Schema(description = "샘플건수")
    private Integer sampleCnt;

    @Schema(description = "회수건수")
    private Integer returnCnt;

    @Schema(description = "판매건수")
    private Integer remainCnt;

    @Schema(description = "회수취소여부")
    private String retriveCancelYn;

    @Schema(description = "삭제여부")
    private String delYn;

    @Override
    public String toString() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateSerializer());
        gsonBuilder.registerTypeAdapter(LocalTime.class, new LocalTimeSerializer());
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer());
        return gsonBuilder.setPrettyPrinting().create().toJson(this);
    }
}


