package com.binblur.core.biz.system.vo.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.binblur.core.entity.User;
import com.binblur.core.enums.BooleanValueCode;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * <pre>
 * Description : 사용자 Response
 * Date : 2023/02/27 16:57 PM
 * Company : smart90
 * Author : sclee9946
 * </pre>
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class UserResponse {

    @Getter
    @Setter
    @EqualsAndHashCode
    @Schema(name = "UserResponsePaging", description = "계정 페이징 응답", type = "object")
    public static class Paging extends User {

        @Schema(description = "NO")
        @Parameter(description = "NO")
        private Integer no;

        @Schema(description = "상태_명")
        @Parameter(description = "상태_명")
        private String useNm;

        @Schema(description = "권한_명")
        @Parameter(description = "권한_명")
        private String authNm;

        @Schema(description = "국가_명")
        @Parameter(description = "국가_명")
        private String country;
    }

    @Getter
    @Setter
    @EqualsAndHashCode
    @Schema(name = "UserResponseSelect", description = "계정 응답", type = "object")
    public static class Select extends User {

    }

    @Getter
    @Setter
    @EqualsAndHashCode
    @Schema(name = "UserResponseSelectByLoginId", description = "계정 응답", type = "object")
    public static class SelectByLoginId extends User {

        @Schema(description = "otp_발행_경과시간(초)")
        @Parameter(description = "otp_발행_경과시간(초)")
        private Integer otpSecond;

        @Schema(description = "아이디 비밀번호 확인")
        @Parameter(description = "아이디 비밀번호 확인")
        private BooleanValueCode isExistIdPass;
    }


    @Getter
    @Setter
    @EqualsAndHashCode
    @Schema(name = "UserResponseSelectExfireUser", description = "계정 응답", type = "object")
    public static class SelectExfireUser extends User {

        @Schema(description = "최종접속후 6개월이 지남")
        @Parameter(description = "최종접속후 6개월이 지남")
        private BooleanValueCode isOver6Month;

        /**
         * 휴면일
         */
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        @Schema(description = "휴면일")
        private LocalDateTime expDateTime;


        /**
         * 미사용5개월시점
         */
        @Parameter(description = "미사용5개월시점인지")
        @Schema(description = "미사용5개월시점인지")
        private BooleanValueCode isMatchFiveMonthLater;
    }

}
