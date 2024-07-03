package com.smart.core.biz.system.vo.request;

import com.google.gson.GsonBuilder;
import com.smart.core.entity.Contact;
import com.smart.core.entity.adapter.LocalDateSerializer;
import com.smart.core.entity.adapter.LocalDateTimeSerializer;
import com.smart.core.entity.adapter.LocalTimeSerializer;
import com.smart.core.enums.TranType;
import com.smart.core.interfaces.RequestFilter;
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
 * Description : 접속로그 Request
 * Date : 2023/03/13 11:58 AM
 * Company : smart90
 * Author : kdonghwa
 * </pre>
 */
@Schema(name = "ContactRequest")
public class ContactRequest {
    @Getter
    @Setter
    @EqualsAndHashCode
    @Schema(name = "ContactRequestPagingFilter", description = "접속로그 페이징 필터", type = "object")
    @ParameterObject
    public class PagingFilter implements RequestFilter {
        @Schema(required = false)
        @Parameter(description = "로그인 ID")
        private String loginId;

        @Schema(required = false)
        @Parameter(description = "회원명")
        private String userNm;

        @Schema(required = false)
        @Parameter(description = "URI_명")
        private String uriNm;

        @Schema(required = false)
        @Parameter(description = "거래구분")
        private TranType tranType;

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
    @Schema(name = "ContactRequestCreate", description = "접속로그 생성 요청 파라미터")
    public class Create extends Contact {
        @Schema(description = "상위코드", required = false)
        private String upMenuCd;

        /** 전체권한 */
        @Schema(description = "전체권한", required = false)
        private String auths;

        /** 권한 */
        @Schema(description = "권한", required = false)
        private String authCd;

        /** 코드_국가코드 */
        @Schema(description = "코드_국가코드", required = false)
        private String countryCode;
    }

    @Getter
    @Setter
    @EqualsAndHashCode
    @Schema(name = "ContactRequestUpdate", description = "접속로그 수정 요청 파라미터")
    public class Update extends Contact {
    }

    @Getter
    @Setter
    @EqualsAndHashCode
    @Schema(name = "ContactRequestDelete", description = "접속로그 삭제 요청 파라미터")
    public class Delete extends Contact {
    }
}

