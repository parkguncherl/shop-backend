package com.shop.core.biz.system.vo.response;

import com.google.gson.GsonBuilder;
import com.shop.core.entity.JwtAuthToken;
import com.shop.core.entity.User;
import com.shop.core.entity.adapter.LocalDateSerializer;
import com.shop.core.entity.adapter.LocalDateTimeSerializer;
import com.shop.core.entity.adapter.LocalTimeSerializer;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;


/**
 * <pre>
 * Description: 로그인 Response
 * Date: 2023/01/26 12:35 PM
 * Company: smart90
 * Author : luckeey
 * </pre>
 */
@Slf4j
@Setter
@Getter
@EqualsAndHashCode(callSuper = false)
public class LoginResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = 3251910227196534222L;

    private User user;

    private JwtAuthToken token;

    @Override
    public String toString() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateSerializer());
        gsonBuilder.registerTypeAdapter(LocalTime.class, new LocalTimeSerializer());
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer());

        return gsonBuilder.setPrettyPrinting().create().toJson(this);
    }
}
