package com.shop.api.biz.system.controller;


import com.google.gson.GsonBuilder;
import com.shop.api.properties.GlobalProperties;
import com.shop.core.biz.system.vo.response.ApiResponse;
import com.shop.core.entity.adapter.LocalDateSerializer;
import com.shop.core.entity.adapter.LocalDateTimeSerializer;
import com.shop.core.entity.adapter.LocalTimeSerializer;
import com.shop.core.enums.ApiResultCode;
import com.shop.core.exception.CustomRuntimeException;
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
 * Description: 에러 Controller
 * Date: 2023/01/26 12:35 PM
 * Author: luckeey
 * </pre>
 */
@Slf4j
@ControllerAdvice
@Controller
@Hidden
@RequestMapping("${server.error.path:/error}")
public class ErrorController extends AbstractErrorController {

    public ErrorController(ErrorAttributes errorAttributes) {
        super(errorAttributes);
    }

    @Autowired
    private GlobalProperties globalProperties;

    /**
     * 일반 에러페이지 처리
     * @param e 발생한 예외
     * @return API 응답 결과
     */
    @ExceptionHandler({Exception.class})
    @ResponseBody
    protected ApiResponse<Map<String, Object>> handleBinblurException(Exception e, HttpServletRequest request) {
        ApiResponse<Map<String, Object>> response = new ApiResponse<>();

        // 에러 타입별 응답 코드 및 메시지 설정
        if (e instanceof SQLException || e instanceof DataAccessException) {
            response.setResultCode(ApiResultCode.DB_ERROR.getResultCode());
            response.setResultMessage(ApiResultCode.DB_ERROR.getResultMessage());
        } else if (e instanceof CustomRuntimeException) {
            response.setResultCode(((CustomRuntimeException)e).getResultCode().getResultCode());
            response.setResultMessage(e.getMessage());
        } else {
            response.setResultCode(ApiResultCode.INTERNAL_SERVER_ERROR.getResultCode());
            response.setResultMessage(e.getMessage());
        }

        // 스택 트레이스 생성
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        String stackTrace = sw.toString();

        // CustomRuntimeException이 아닌 경우에만 슬랙 메시지 전송 (파일 위치 정보 포함)
        if (!(e instanceof CustomRuntimeException)) {
            // Exception 객체를 함께 전달하여 파일 위치 정보 포함
            sendErrorMessage(response, stackTrace, e, request);
        }

        log.error(e.getMessage(), e);

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

        // 일단 막아놓음
        // sendErrorMessage(response, errorInfo.toString());
        // sendErrorMessage(response, "서버에러가 발생하였습니다. 서버로그를 확인해 주세요");

        return response;
    }


    /**
     * Slack 에러 메시지 전송
     * @param errorResponse
     */

    private void sendErrorMessage(ApiResponse<Map<String, Object>> errorResponse, String errorMessage, Exception exception, HttpServletRequest request) {
        try {
            if (globalProperties.getSlackSendErrorMessageYn()) {
                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateSerializer());
                gsonBuilder.registerTypeAdapter(LocalTime.class, new LocalTimeSerializer());
                gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer());

                String jsonErrorResponse = gsonBuilder.setPrettyPrinting().create().toJson(errorResponse);

                // 에러 파일 위치 정보 추출
                String fileLocation = getErrorFileLocation(exception);

                // 메시지 구성
                StringBuilder messageBuilder = new StringBuilder();
                String requestUrl = request.getRequestURL().toString();
                String queryString = request.getQueryString();
                if (queryString != null) {
                    requestUrl += "?" + queryString;
                }
                messageBuilder.append("🚨 **에러 발생** 🚨\n");
                messageBuilder.append("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");

                if (fileLocation != null && !fileLocation.isEmpty()) {
                    messageBuilder.append("📍 **에러 위치**: ").append(fileLocation).append("\n\n");
                }

                messageBuilder.append("🌐 **요청 URL**: ").append(requestUrl).append("\n\n");
                messageBuilder.append("**에러 응답**:\n```json\n");
                messageBuilder.append(jsonErrorResponse);
                messageBuilder.append("\n```\n\n");
                messageBuilder.append("**에러 메시지**:\n```\n");
                messageBuilder.append(errorMessage);
                messageBuilder.append("\n```");

                String finalMessage = messageBuilder.toString();

                Slack slack = Slack.getInstance();
                Payload payload = Payload.builder().text(finalMessage).build();
                slack.send(globalProperties.getSlackWebHooksUrl(), payload);
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    private String getErrorFileLocation(Exception exception) {
        if (exception == null) {
            // 현재 스택 트레이스에서 호출자 위치 찾기
            StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
            for (StackTraceElement element : stackTrace) {
                // 자신의 패키지 내 클래스 찾기 (com.yourpackage로 변경하세요)
                if (element.getClassName().startsWith("com.yourpackage")
                        && !element.getMethodName().equals("getErrorFileLocation")
                        && !element.getMethodName().equals("sendErrorMessage")) {
                    return formatFileLocation(element);
                }
            }
        } else {
            // Exception에서 스택 트레이스 추출
            StackTraceElement[] stackTrace = exception.getStackTrace();
            if (stackTrace != null && stackTrace.length > 0) {
                // 첫 번째 스택 트레이스 요소 (에러 발생 지점)
                return formatFileLocation(stackTrace[0]);
            }
        }
        return null;
    }


    /**
     * 파일 위치 정보 포맷팅
     */
    private String formatFileLocation(StackTraceElement element) {
        StringBuilder location = new StringBuilder();

        // 클래스명에서 패키지 제거하고 간단하게 표시
        String className = element.getClassName();
        String simpleClassName = className.substring(className.lastIndexOf('.') + 1);

        location.append(simpleClassName);
        location.append(".").append(element.getMethodName());
        location.append("(").append(element.getFileName());

        if (element.getLineNumber() > 0) {
            location.append(":").append(element.getLineNumber());
        }

        location.append(")");

        return location.toString();
    }
}
