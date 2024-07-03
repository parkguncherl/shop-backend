package com.binblur.core.biz.common.vo.request;

import com.google.gson.GsonBuilder;
import com.binblur.core.entity.adapter.LocalDateSerializer;
import com.binblur.core.entity.adapter.LocalDateTimeSerializer;
import com.binblur.core.entity.adapter.LocalTimeSerializer;
import com.binblur.core.interfaces.RequestFilter;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springdoc.core.annotations.ParameterObject;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * <pre>
 * Description : sms Request
 * Date : 2023/03/20 11:58 AM
 * Company : smart90
 * Author : luckeey
 * </pre>
 */
@Schema(name = "SmsRequest")
public class SmsRequest {

    @Getter
    @Setter
    @EqualsAndHashCode
    @Schema(name = "MenuRequestCreate", description = "Sms Create")
    public static class Create {

    }


    @Getter
    @Setter
    @EqualsAndHashCode
    @Schema(name = "SvrEventPagingFilter", description = "서버이벤트 페이징 필터")
    public static class SvrEventPagingFilter {

        @Schema(required = false)
        @Parameter(description = "스테이션 ID")
        private String stationId;

        @Schema(required = true)
        @Parameter(description = "포스트 ID")
        private Integer postId;

        @Schema(required = true)
        @Parameter(description = "프로파일 ID")
        private Integer profileId;

        @Override
        public String toString() {
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateSerializer());
            gsonBuilder.registerTypeAdapter(LocalTime.class, new LocalTimeSerializer());
            gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer());

            return gsonBuilder.setPrettyPrinting().create().toJson(this);
        }
    }

    @Getter
    @Setter
    @EqualsAndHashCode
    @Schema(name = "PostEventSmsHistoryPagingFilter", description = "충전기 이벤트 SMS 이력 페이징 필터")
    @ParameterObject
    public static class PostEventSmsHistoryPagingFilter implements RequestFilter {

        @Schema(description = "충전기_이벤트_로그_ID")
        private String eventLogId;
    }
}

