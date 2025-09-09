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
 * Description: order Entity
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
@Schema(name = "WrongDet", description = "오출고 상세 Entity")
public class WrongDet extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 3056713241158954521L;

    @Schema(description = "id", nullable = false)
    private Integer id;

    @Schema(description = "오출고 id")
    private Integer wrongId;

    @Schema(description = "처리구분")
    private String wrongTranCd;

    @Schema(description = "작업상세 id")
    private Integer jobDetId;

    @Schema(description = "스큐Id")
    private Integer skuId;

    @Schema(description = "작업수량")
    private Integer jobCnt;

    @Schema(description = "변경수량")
    private Integer chgCnt;

    @Schema(description = "사유")
    private String wrongReason;

    @Override
    public String toString() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateSerializer());
        gsonBuilder.registerTypeAdapter(LocalTime.class, new LocalTimeSerializer());
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer());
        return gsonBuilder.setPrettyPrinting().create().toJson(this);
    }
}


