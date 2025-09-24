package com.shop.core.biz.system.vo.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.shop.core.entity.User;
import com.shop.core.enums.BooleanValueCode;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * <pre>
 * Description: 사용자 Response
 * Date: 2023/02/27 16:57 PM
 * Company: smart90
 * Author: luckeey
 * </pre>
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class UserResponse {

    @Getter
    @Setter
    @EqualsAndHashCode(callSuper = false)
    @Schema(name = "UserResponsePaging", description = "계정 페이징 응답", type = "object")
    public static class Paging extends User {

        @Schema(description = "NO")
        @Parameter(description = "NO")
        private Integer no;

        @Schema(description = "소유권한개수")
        @Parameter(description = "소유권한개수")
        private Integer userAuthCnt;

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
    @EqualsAndHashCode(callSuper = false)
    @Schema(name = "UserResponseSelect", description = "계정 응답", type = "object")
    public static class Select extends User {

    }

    @Getter
    @Setter
    @EqualsAndHashCode(callSuper = false)
    @Schema(name = "UserResponseSelectByLoginId", description = "계정 응답", type = "object")
    public static class SelectByLoginId extends User {

        @Schema(description = "아이디 비밀번호 확인")
        @Parameter(description = "아이디 비밀번호 확인")
        private BooleanValueCode isExistIdPass;

        /**
         * 권한명
         * */
        @Schema(description = "권한명")
        @Parameter(description = "권한명")
        private String authNm;

        @Schema(description = "사용자타입명")
        @Parameter(description = "사용자타입명")
        private String userTypeNm;

        /**
         * 영업일 추가시간
         * */
        @Schema(description = "영업일 추가 시간")
        @Parameter(description = "영업일추가시간")
        private Integer addTime;

        /**
         * 1: 화주
         * 2: 화주가 도매로 로그인
         * 3: 도매
         * */
        @Schema(description = "나의 가능한 기능들")
        private String myCustomRoles;

        /** 등록일시 */
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        @Schema(description = "등록일시")
        private LocalDateTime creTm;

        /** 등록자 */
        @Schema(description = "등록자")
        private String creUserNm;

        /** 수정일시 */
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        @Schema(description = "수정일시")
        private LocalDateTime updTm;

        /** 수정자 */
        @Schema(description = "수정자")
        private String updUserNm;
    }


    @Getter
    @Setter
    @EqualsAndHashCode(callSuper = false)
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
