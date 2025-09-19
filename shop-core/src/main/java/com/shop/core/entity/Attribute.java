package com.shop.core.entity;

import com.google.gson.GsonBuilder;
import com.shop.core.entity.adapter.LocalDateSerializer;
import com.shop.core.entity.adapter.LocalDateTimeSerializer;
import com.shop.core.entity.adapter.LocalTimeSerializer;
import com.shop.core.interfaces.BaseEntity;
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
 * Description: 속성_관리 Entity
 * Company: home
 * Author: park
 * </pre>
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "Attribute", description = "속성 Entity")
public class Attribute extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 3012713244958954521L;

    /** 아이디(PK) */
    private Integer id;

    /** 속성명 */
    private String attrNm;

    /** 속성의 영문명 */
    private String attrEngNm;

    /** 속성타입(숫자, 문자, 날짜, json) */
    private String attrType;

    /** 속성 유형(단일, 다중, 범위) */
    private String attrCat;

    /** 속성설명 */
    private String attrDesc;

    /** 상위코드 */
    private String codeUpper;

    @Override
    public String toString() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateSerializer());
        gsonBuilder.registerTypeAdapter(LocalTime.class, new LocalTimeSerializer());
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer());

        return gsonBuilder.setPrettyPrinting().create().toJson(this);
    }
}
