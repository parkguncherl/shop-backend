package com.shop.core.biz.system.vo.request;

import com.google.gson.GsonBuilder;
import com.shop.core.entity.adapter.LocalDateSerializer;
import com.shop.core.entity.adapter.LocalDateTimeSerializer;
import com.shop.core.entity.adapter.LocalTimeSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * <pre>
 * Description: 로그인 Request
 * Date: 2023/01/26 12:35 PM
 * Company: smart90
 * Author : luckeey
 * </pre>
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class LoginRequest {

    @Schema(description = "로그인아이디(이메일)", example = "aaa@bbb.com")
    private String loginId;

    @Schema(description = "로그인비밀번호", example = "XXXXXXXXXX")
    private String password;

    @Schema(description = "OTP 코드", example = "123456")
    private String otpNo;

    @Schema(description = "모바일 여부", example = "Y")
    private String isMobileLogin;

    @Schema(description = "비밀번호재입력", example = "XXXXXXXXXX")
    private String rePassword;

    @Schema(description = "수정비밀번호", example = "XXXXXXXXXX")
    private String modPassword;

    @Schema(description = "수정비밀번호재입력", example = "XXXXXXXXXX")
    private String reModpassword;
    @Schema(description = "비번찾기용 전화번호", example = "01024252121")
    private String phoneNo;
    @Schema(description = "비밀번호변경타입", example = "F")
    private String changeType;

    @Override
    public String toString() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateSerializer());
        gsonBuilder.registerTypeAdapter(LocalTime.class, new LocalTimeSerializer());
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer());

        return gsonBuilder.setPrettyPrinting().create().toJson(this);
    }
}
