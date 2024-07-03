package com.binblur.core.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.gson.GsonBuilder;
import com.binblur.core.entity.adapter.LocalDateSerializer;
import com.binblur.core.entity.adapter.LocalDateTimeSerializer;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <pre>
 * Description : 인증 Token 엔티티.
 * Date : 2023/01/26 12:35 PM
 * Company : smart
 * Author : luckeey
 * </pre>
 */
@Getter
@Setter
@EqualsAndHashCode
public class AuthToken implements Serializable {

    private static final long serialVersionUID = 1787282049186484025L;

    /** 아이디 */
    private Long id;

    /** 회원_아이디 */
    private Integer userId;

    /** Access_토큰 */
    private String accessToken;

    /** Access_토큰_만료일시 */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime accessTokenExpireDateTime;

    /** Refresh_토큰 */
    private String refreshToken;

    /** Refresh_토큰_만료일시 */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime refreshTokenExpireDateTime;

    /** 등록일시 */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createDateTime;

    @Override
    public String toString() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateSerializer());
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer());

        return gsonBuilder.setPrettyPrinting().create().toJson(this);
    }
}
