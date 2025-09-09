package com.shop.core.vo.response;

import com.google.gson.GsonBuilder;
import com.shop.core.entity.adapter.LocalDateSerializer;
import com.shop.core.entity.adapter.LocalDateTimeSerializer;
import com.shop.core.enums.ApiResultCode;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <pre>
 * Description: API 기본 Response
 * Date: 2023/01/26 12:35 PM
 * Company: smart
 * Author : luckeey
 * </pre>
 */
@Slf4j
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class ApiResponse<T> {

    private int resultCode;
    private String resultMessage;

    private T body;

    public ApiResponse() {

    }

    public ApiResponse(ApiResultCode apiResultCode) {
        this.resultCode = apiResultCode.getResultCode();
        this.resultMessage = apiResultCode.getResultMessage();
        this.body = null;
    }

    public ApiResponse(T body) {
        this.resultCode = ApiResultCode.getResultCode(ApiResultCode.SUCCESS.getResultCode());
        this.resultMessage = ApiResultCode.getResultMessage(ApiResultCode.SUCCESS.getResultCode());
        this.body = body;
    }

    public void setApiResultCode(ApiResultCode apiResultCode) {
        this.resultCode = apiResultCode.getResultCode();
        this.resultMessage = apiResultCode.getResultMessage();
    }

    public void setBody(T body) {
        this.body = body;
    }

    public ApiResponse(ApiResultCode apiResultCode, T body) {
        this.resultCode = apiResultCode.getResultCode();
        this.resultMessage = apiResultCode.getResultMessage();
        this.body = body;
    }

    @Override
    public String toString() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateSerializer());
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer());

        return gsonBuilder.setPrettyPrinting().create().toJson(this);
    }
}
