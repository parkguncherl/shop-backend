package com.binblur.api.biz.system.controller;


import com.google.gson.GsonBuilder;
import com.binblur.api.properties.GlobalProperties;
import com.binblur.core.biz.system.vo.response.ApiResponse;
import com.binblur.core.entity.adapter.LocalDateSerializer;
import com.binblur.core.entity.adapter.LocalDateTimeSerializer;
import com.binblur.core.entity.adapter.LocalTimeSerializer;
import com.binblur.core.enums.ApiResultCode;
import com.binblur.core.exception.EspRuntimeException;
import com.slack.api.Slack;
import com.slack.api.webhook.Payload;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Map;


/**
 * <pre>
 * Description : 에러 Controller
 * Date : 2023/01/26 12:35 PM
 * Company : smart90
 * Author : harry
 * </pre>
 */
@Slf4j
@ControllerAdvice
@Controller
@Hidden
@RequestMapping("${server.error.path:/error}")
public class ErrorController extends AbstractErrorController {

    @Autowired
    private GlobalProperties globalProperties;

    public ErrorController(ErrorAttributes errorAttributes) {
        super(errorAttributes);
    }


    /**
     * 일반 에러페이지 처리
     * @param e
     * @return
     */
    @ExceptionHandler({Exception.class})
    @ResponseBody
    protected ApiResponse<Map<String, Object>> handleSllException(Exception e) {
        ApiResponse<Map<String, Object>> response = new ApiResponse<>();
        if (e instanceof SQLException || e instanceof DataAccessException) {
            response.setResultCode(ApiResultCode.DB_ERROR.getResultCode());
            response.setResultMessage(ApiResultCode.DB_ERROR.getResultMessage());
        } else if (e instanceof EspRuntimeException){
            response.setResultCode(((EspRuntimeException)e).getResultCode().getResultCode());
            response.setResultMessage(e.getMessage());
        } else {
            response.setResultCode(ApiResultCode.INTERNAL_SERVER_ERROR.getResultCode());
            response.setResultMessage(e.getMessage());
        }

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        String stackTrace = sw.toString();

        if (!(e instanceof EspRuntimeException)) {
            // 케피코 요청으로 공통 메시지로 변경 처리함
            // sendErrorMessage(response, stackTrace);
            sendErrorMessage(response, "서버에러가 발생하였습니다. 서버로그를 확인해 주세요");
        }

        log.error(e.getMessage(), e );

        return response;
    }


    /**
     * 서버 에러페이지 처리
     * @param request
     * @return
     */
    @RequestMapping
    @ResponseBody
    public ApiResponse<Map<String, Object>> error(HttpServletRequest request) {
        Map<String, Object> errorInfo = this.getErrorAttributes(request, ErrorAttributeOptions.of(ErrorAttributeOptions.Include.STACK_TRACE));
        log.error("<======= ERROR JSON: {}", errorInfo );

        ApiResponse<Map<String, Object>> response = new ApiResponse<>();
        response.setResultCode(ApiResultCode.INTERNAL_SERVER_ERROR.getResultCode());
        response.setBody(errorInfo);

        // 케피코 요청으로 공통 메시지로 변경 처리함
        // sendErrorMessage(response, errorInfo.toString());
        sendErrorMessage(response, "서버에러가 발생하였습니다. 서버로그를 확인해 주세요");

        return response;
    }


    /**
     * Slack 에러 메시지 전송
     * @param errorResponse
     */
    private void sendErrorMessage(ApiResponse<Map<String, Object>> errorResponse, String errorMessage){
        try {
            if (globalProperties.getSlackSendErrorMessageYn()) {

                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateSerializer());
                gsonBuilder.registerTypeAdapter(LocalTime.class, new LocalTimeSerializer());
                gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer());
                errorMessage = gsonBuilder.setPrettyPrinting().create().toJson(errorResponse) + "\n" + errorMessage;

                Slack slack = Slack.getInstance();
                Payload payload = Payload.builder().text(errorMessage).build();

                slack.send(globalProperties.getSlackWebHooksUrl(), payload);
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

}
