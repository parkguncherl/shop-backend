package com.shop.core.entity;

import com.google.gson.GsonBuilder;
import com.shop.core.entity.adapter.LocalDateSerializer;
import com.shop.core.entity.adapter.LocalDateTimeSerializer;
import com.shop.core.entity.adapter.LocalTimeSerializer;
import com.shop.core.enums.TranType;
import com.shop.core.interfaces.BaseEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * <pre>
 * Description: 코드_관리 Entity
 * Date: 2023/02/06 12:07 PM
 * Company: smart
 * Author: luckeey
 * </pre>
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class Contact extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 3056713244958955521L;

    /** 아이디(PK) */
    private Integer id;

    /** 사용자_ID(FK) */
    private Integer userId;

    /**
     * 거래구분 */
    private String tranType;

    /** URI */
    private String uri;

    /** URI_명 */
    private String uriNm;

    /** 입력_변수_내용 */
    private String inputParamCntn;

    /** 파트너아이디 */
    private Integer partnerId;

    /** 접속_IP */
    private String contactIp;
    @Override
    public String toString() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateSerializer());
        gsonBuilder.registerTypeAdapter(LocalTime.class, new LocalTimeSerializer());
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer());

        return gsonBuilder.setPrettyPrinting().create().toJson(this);
    }
}
