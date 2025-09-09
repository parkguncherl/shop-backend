package com.shop.core.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.gson.GsonBuilder;
import com.shop.core.entity.adapter.LocalDateSerializer;
import com.shop.core.entity.adapter.LocalDateTimeSerializer;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <pre>
 * Description: JWT 인증 Token 엔티티.
 * Date: 2023/01/26 12:35 PM
 * Company: smart
 * Author : luckeey
 * </pre>
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class JwtAuthToken {

    /** access 토큰 */
    private String accessToken;

    /** access 토큰 Expire 시간 */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime accessTokenExpireDate;

    /** refresh 토큰 */
    private String refreshToken;

    /** refresh 토큰 Expire 시간 */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime refreshTokenExpireDate;

    /** error 발생 시 message */
    private String errMessage;

    public JwtAuthToken() {
    }

    public JwtAuthToken(String errMessage) {
        this.errMessage = errMessage;
    }

    @Override
    public String toString() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateSerializer());
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer());

        return gsonBuilder.setPrettyPrinting().create().toJson(this);
    }
}
