package com.binblur.core.entity;

import com.google.gson.GsonBuilder;
import com.binblur.core.entity.adapter.LocalDateSerializer;
import com.binblur.core.entity.adapter.LocalDateTimeSerializer;
import com.binblur.core.entity.adapter.LocalTimeSerializer;
import com.binblur.core.interfaces.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * <pre>
 * Description : 속성_관리 Entity
 * Company : home
 * Author : park
 * </pre>
 */
@Getter
@Setter
@EqualsAndHashCode
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "Company", description = "회사 Entity")
public class Company extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 3056713244958954521L;

    /** 아이디(PK) */
    private Integer id;

    /** 속성명 */
    private String compNm;

    /** 회사주소 */
    private String compAddr;

    /** 회사 관련 내용 */
    private String compCntn;

    /** 회사 레벨 코드 */
    private String compLevelCode;

    @Override
    public String toString() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateSerializer());
        gsonBuilder.registerTypeAdapter(LocalTime.class, new LocalTimeSerializer());
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer());

        return gsonBuilder.setPrettyPrinting().create().toJson(this);
    }
}

