package com.shop.core.biz.system.vo.request;

import com.shop.core.enums.MailType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.shop.core.entity.User;
import com.shop.core.entity.UserExpire;
import com.shop.core.enums.BooleanValueCode;
import com.shop.core.interfaces.RequestFilter;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * <pre>
 * Description: 사용자 Request
 * Date: 2023/03/15 14:45 PM
 * Company: smart90
 * Author: luckeey
 * </pre>
 */
@Schema(name = "UserRequest")
public class UserRequest {

    @Getter
    @Setter
    @EqualsAndHashCode(callSuper = false)
    @Schema(name = "UserRequestPagingFilter", description = "계정 페이징 필터")
    @ParameterObject
    public static class PagingFilter implements RequestFilter {

        @Schema(description = "아이디")
        @Parameter(description = "아이디")
        private String loginId;

        @Schema(description = "회원_명")
        @Parameter(description = "회원_명")
        private String userNm;

        @Schema(description = "권한코드")
        @Parameter(description = "권한코드")
        private String authCd;

        @Schema(description = "회원_휴대폰번호")
        @Parameter(description = "회원_휴대폰번호")
        private String phoneNo;

        @Schema(description = "회사_명")
        @Parameter(description = "회사_명")
        private String compNm;

        @Schema(description = "파트너명")
        @Parameter(description = "파트너명")
        private String partnerNm;

        @Schema(description = "사용자구분")
        @Parameter(description = "사용자구분")
        private String omsWmsTp;

        @Schema(description = "소속_명")
        @Parameter(description = "소속_명")
        private String belongNm;

        @Schema(description = "부서_명")
        @Parameter(description = "부서_명")
        private String deptNm;

        @Schema(description = "직책_명")
        @Parameter(description = "직책_명")
        private String positionNm;

        @Schema(description = "사용여부")
        @Parameter(description = "사용여부")
        private BooleanValueCode useYn;

        @Schema(description = "소유자_권한코드")
        @Parameter(description = "소유자_권한코드")
        private String myAuthCd;

        @Schema(description = "파트너_ID")
        @Parameter(description = "파트너_ID")
        private Integer partnerId;

        @Schema(description = "WORK_LOGIS_ID(FK)")
        @Parameter(description = "WORK_LOGIS_ID(FK)")
        private Integer workLogisId;
        /**
         * 검색 제외 아이디
         */
        private List<String> excludeIds;
    }

    @Getter
    @Setter
    @EqualsAndHashCode(callSuper = false)
    @Schema(name = "UserRequestCreate", description = "계정 생성 요청 파라미터")
    public static class Create implements RequestFilter {

        @Schema(description = "id")
        private Integer id;

        @Schema(description = "로그인_아이디")
        private String loginId;

        //@JsonIgnore : 주석풀면 등록 시 컬럼값 NULL 처리됨 (주의)
        @Schema(description = "로그인_비밀번호")
        private String loginPass;

        @Schema(description = "회원_명")
        private String userNm;

        @Schema(description = "권한코드")
        private String authCd;

        @Schema(description = "회원_휴대폰번호")
        private String phoneNo;

        @Schema(description = "회사명")
        private String compNm;

        @Schema(description = "소속_명")
        private String belongNm;

        @Schema(description = "부서_명")
        private String deptNm;

        @Schema(description = "직책_명")
        private String positionNm;

        @Schema(description = "유저이미지")
        private Integer fileId;

        @Schema(description = "연결_화주_아이디")
        private Integer partnerId;

        @Schema(description = "org_연결_화주_아이디")
        private Integer orgPartnerId;

        @Schema(description = "로그인_실패_회수")
        private Integer loginFailCnt;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        @Schema(description = "최근_로그인_일시")
        private LocalDateTime lastLoginDateTime;

        @Schema(description = "최초로그인여부")
        private BooleanValueCode firstLoginYn;

        @Schema(description = "otp_발행번호")
        private String otpNo;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        @Schema(description = "otp_발생일시")
        private LocalDateTime otpIssuDateTime;

        @Schema(description = "로그인한_유저_국가코드")
        private String languageCode;

        @Schema(description = "사용자타입")
        private String userType;

        @Schema(description = "등록자")
        private String creUser;

        @Schema(description = "사용여부")
        protected BooleanValueCode useYn;

        @Schema(description = "계정잠김여부")
        protected BooleanValueCode lockYn;

        @Schema(required = false)
        @Parameter(description = "영업일")
        private LocalDate workYmd;

        @Schema(description = "WORK_LOGIS_ID(FK)")
        @Parameter(description = "WORK_LOGIS_ID(FK)")
        private Integer workLogisId;

        public User toEntity() {
            return User.builder()
                .loginId(getLoginId())
                .loginPass(getLoginPass())
                .userNm(getUserNm())
                .partnerId(getPartnerId())
                .orgPartnerId(getOrgPartnerId())
                .authCd(getAuthCd())
                .phoneNo(getPhoneNo())
                .partnerNm(getCompNm())
                .belongNm(getBelongNm())
                .deptNm(getDeptNm())
                .positionNm(getPositionNm())
                .fileId(getFileId())
                .loginFailCnt(getLoginFailCnt())
                .lastLoginDateTime(getLastLoginDateTime())
                .firstLoginYn(getFirstLoginYn())
                //.otpNo(getOtpNo())
                //.otpIssuDateTime(getOtpIssuDateTime())
                //.languageCode(getLanguageCode())
                .creUser(getCreUser())
                .useYn(getUseYn())
                .lockYn(getLockYn())
                .workYmd(getWorkYmd())
                .workLogisId(getWorkLogisId())
                .userType(getUserType())
                .build();
        }
    }

    @Getter
    @Setter
    @EqualsAndHashCode(callSuper = false)
    @Schema(name = "UserRequestUpdate", description = "계정 수정 요청 파라미터")
    public static class Update implements RequestFilter {

        @Schema(description = "아이디")
        private Integer id;

        @Schema(description = "로그인_아이디")
        private String loginId;

        @Schema(description = "LOGIS_ID(FK)")
        private Integer logisId;

        //@JsonIgnore : 주석풀면 등록 시 컬럼값 NULL 처리됨 (주의)
        @Schema(description = "로그인_비밀번호")
        private String loginPass;

        @Schema(description = "회원_명")
        private String userNm;

        @Schema(description = "권한코드")
        private String authCd;

        @Schema(description = "회원_휴대폰번호")
        private String phoneNo;

        @Schema(description = "소속_명")
        private String belongNm;

        @Schema(description = "부서_명")
        private String deptNm;

        @Schema(description = "직책_명")
        private String positionNm;

        @Schema(description = "유저이미지")
        private Integer fileId;

        @Schema(description = "로그인_실패_회수")
        private Integer loginFailCnt;

        @Schema(description = "모바일 로그인 여부")
        private String isMobileLogin;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        @Schema(description = "최근_로그인_일시")
        private LocalDateTime lastLoginDateTime;

        @Schema(description = "최초로그인여부")
        private BooleanValueCode firstLoginYn;

        @Schema(description = "otp_발행번호")
        private String otpNo;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        @Schema(description = "otp_발생일시")
        private LocalDateTime otpIssuDateTime;

        @Schema(description = "로그인한_유저_국가코드")
        private String languageCode;

        @Schema(description = "파트너id")
        private Integer partnerId;

        @Schema(description = "Origin 파트너id")
        private Integer orgPartnerId;

        @Schema(description = "로그인한_유저_국가코드")
        private String loginLanguage;

        @Schema(description = "수정자")
        private String updUser;

        @Schema(description = "사용여부")
        protected BooleanValueCode useYn;

        @Schema(description = "계정잠김여부")
        protected BooleanValueCode lockYn;

        @Schema(required = false)
        @Parameter(description = "OTP_실패_횟수")
        private Integer otpFailCnt;

        @Schema(required = false)
        @Parameter(description = "영업일")
        private LocalDate workYmd;

        @Schema(description = "WORK_LOGIS_ID(FK)")
        @Parameter(description = "WORK_LOGIS_ID(FK)")
        private Integer workLogisId;

        public User toEntity() {
            return User.builder()
                .id(getId())
                .loginId(getLoginId())
                .loginPass(getLoginPass())
                .userNm(getUserNm())
                .authCd(getAuthCd())
                .phoneNo(getPhoneNo())
                .belongNm(getBelongNm())
                .deptNm(getDeptNm())
                .positionNm(getPositionNm())
                .fileId(getFileId())
                .loginFailCnt(getLoginFailCnt())
                .lastLoginDateTime(getLastLoginDateTime())
                .firstLoginYn(getFirstLoginYn())
                //.otpNo(getOtpNo())
                //.otpIssuDateTime(getOtpIssuDateTime())
                //.languageCode(getLanguageCode())
                //.loginLanguage(getLoginLanguage())
                .updUser(getUpdUser())
                .useYn(getUseYn())
                .lockYn(getLockYn())
                .otpFailCnt(getOtpFailCnt())
                .partnerId(getPartnerId())
                .orgPartnerId(getOrgPartnerId())
                .workYmd(getWorkYmd())
                .workLogisId(getWorkLogisId())
                .build();
        }
    }

    @Getter
    @Setter
    @EqualsAndHashCode(callSuper = false)
    @Schema(name = "UserRequestDelete", description = "계정 삭제 요청 파라미터")
    public static class Delete implements RequestFilter {

        @Schema(description = "아이디")
        private Integer id;

        @Schema(description = "로그인_아이디")
        private String loginId;

        @Schema(description = "수정자")
        private String updUser;

        public User toEntity() {
            return User.builder()
                .id(getId())
                .updUser(getUpdUser())
                .build();
        }
    }

    @Getter
    @Setter
    @EqualsAndHashCode(callSuper = false)
    @Schema(name = "UserRequestEmail", description = "계정 메일 요청 파라미터")
    public static class Email implements RequestFilter {

        @Schema(description = "로그인_아이디")
        private String loginId;

        //@JsonIgnore : 주석풀면 등록 시 컬럼값 NULL 처리됨 (주의)
        @Schema(description = "로그인_비밀번호")
        private String loginPass;

        @Schema(description = "로그인_아이디")
        private MailType mailType;

        public User toEntity() {
            return User.builder()
                .loginId(getLoginId())
                .loginPass(getLoginPass())
                .build();
        }
    }

    @Getter
    @Setter
    @EqualsAndHashCode(callSuper = false)
    @Schema(name = "UserRequestPasswordInit", description = "계정 비밀번호초기화 요청 파라미터")
    public static class PasswordInit implements RequestFilter {

        @Schema(description = "아이디")
        private Integer id;

        @Schema(description = "로그인_아이디")
        private String loginId;

        //@JsonIgnore : 주석풀면 등록 시 컬럼값 NULL 처리됨 (주의)
        @Schema(description = "로그인_비밀번호")
        private String loginPass;

        @Schema(description = "회원_휴대폰번호")
        private String phoneNo;

        @Schema(description = "수정자")
        private String updUser;

        public User toEntity() {
            return User.builder()
                .id(getId())
                .loginId(getLoginId())
                .loginPass(getLoginPass())
                .phoneNo(getPhoneNo())
                .updUser(getUpdUser())
                .build();
        }
    }

    @Getter
    @Setter
    @EqualsAndHashCode(callSuper = false)
    @Schema(name = "UserRequestUnLock", description = "계정 잠금해제 요청 파라미터")
    public static class UnLock implements RequestFilter {

        @Schema(description = "아이디")
        private Integer id;

        @Schema(description = "로그인_아이디")
        private String loginId;

        //@JsonIgnore : 주석풀면 등록 시 컬럼값 NULL 처리됨 (주의)
        @Schema(description = "로그인_비밀번호")
        private String loginPass;

        @Schema(description = "회원_명")
        private String userNm;

        @Schema(description = "회원_휴대폰번호")
        private String phoneNo;

        @Schema(description = "수정자")
        private String updUser;

        public User toEntity() {
            return User.builder()
                .id(getId())
                .loginId(getLoginId())
                .loginPass(getLoginPass())
                .userNm(getUserNm())
                .phoneNo(getPhoneNo())
                .updUser(getUpdUser())
                .build();
        }

        public UserExpire toUserExpire() {
            return UserExpire.builder()
                .userId(getId())
                .build();
        }
    }

    @Getter
    @Setter
    @EqualsAndHashCode(callSuper = false)
    @Schema(name = "UserRequestValidatePassword", description = "현재 비밀번호 확인 요청 파라미터")
    public static class ValidatePassword {

        @Schema(description = "아이디")
        private Integer id;

        @Schema(description = "로그인 아이디")
        private String loginId;

        @Schema(description = "현재 비밀번호")
        private String currentPass;
    }

    @Getter
    @Setter
    @EqualsAndHashCode(callSuper = false)
    @Schema(name = "UserRequestUpdatePassword", description = "비밀번호 변경 요청 파라미터")
    public static class UpdatePassword {

        @Schema(description = "아이디")
        private Integer id;

        @Schema(description = "로그인 아이디")
        private String loginId;

        @Schema(description = "변경할 비밀번호")
        private String nextPass;
    }
}
