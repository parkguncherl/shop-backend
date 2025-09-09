package com.shop.core.entity;

import com.shop.core.entity.adapter.LocalDateSerializer;
import com.shop.core.entity.adapter.LocalDateTimeSerializer;
import com.shop.core.entity.adapter.LocalTimeSerializer;
import com.shop.core.interfaces.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.gson.GsonBuilder;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/** 상품 */
@Getter
@Setter
@EqualsAndHashCode
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "상품 Entity")
public class Product extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = -4710305357251982306L;

    @Schema(description = "아이디(PK)")
    private Integer id;

    @Schema(description = "사용자상품코드")
    private String userProdCd;

    @Schema(description = "상품코드")
    private String prodCd;

    @Schema(description = "파트너ID(FK)")
    private Integer partnerId;

    @Schema(description = "상품명")
    private String prodNm;

    @Schema(description = "파일ID(FK)")
    private Integer fileId;

    @Schema(description = "이미지파일ID(FK)")
    private Integer imgFileId;

    @Schema(description = "규칙일치여부")
    private String formYn;


    @Override
    public String toString() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateSerializer());
        gsonBuilder.registerTypeAdapter(LocalTime.class, new LocalTimeSerializer());
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer());

        return gsonBuilder.setPrettyPrinting().create().toJson(this);
    }
}