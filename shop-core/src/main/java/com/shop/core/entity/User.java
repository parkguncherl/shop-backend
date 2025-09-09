package com.shop.core.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.gson.GsonBuilder;
import com.shop.core.entity.adapter.LocalDateSerializer;
import com.shop.core.entity.adapter.LocalDateTimeSerializer;
import com.shop.core.entity.adapter.LocalTimeSerializer;
import com.shop.core.enums.BooleanValueCode;
import com.shop.core.interfaces.BaseEntity;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

/**
 * <pre>
 * Description: 회원 Entity
 * Date: 2023/01/26 12:35 PM
 * Company: smart
 * Author : luckeey
 * </pre>
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "User", description = "계정 Entity")
public class User extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 3321251041886434025L;

    /**
     * 아이디(PK)
     */
    @Schema(description = "아이디")
    private Integer id;

    @Schema(description = "로그인_아이디")
    private String loginId;

    @Schema(description = "사용자 타입(oms ,wms ,admin)")
    private String userType;

    @Schema(description = "WORK_LOGIS_ID(FK)")
    private Integer workLogisId;

    @Schema(description = "작업물류센터멍")
    private String workLogisNm;

    //@JsonIgnore : 주석풀면 등록 시 컬럼값 NULL 처리됨 (주의)
    @Schema(description = "로그인_비밀번호")
    private String loginPass;

    @Schema(description = "회원_명")
    private String userNm;

    @Schema(description = "파트너아이디")
    private Integer partnerId;

    @Schema(description = "original 파트너아이디")
    private Integer orgPartnerId;

    @Schema(description = "권한코드")
    private String authCd;

    @Schema(description = "회원_휴대폰번호")
    private String phoneNo;

    @Schema(description = "파트너회사명")
    private String partnerNm;

    @Schema(description = "origin 파트너회사명")
    private String orgPartnerNm;

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

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "최근_로그인_일시")
    private LocalDateTime lastLoginDateTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "최근_로그인_일시")
    private LocalDateTime lastLoginTm;

    @Schema(description = "영업일 추가 시간")
    @Parameter(description = "영업일추가시간")
    private Integer addTime;

    @Schema(description = "최초로그인여부")
    private BooleanValueCode firstLoginYn;

    @Schema(description = "페이지권한설정")
    private Boolean isPageAuth;
/*

    @Schema(description = "otp_발행번호")
    private String otpNo;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "otp_발생일시")
    private LocalDateTime otpIssuDateTime;

    @Schema(description = "유저_국가코드")
    private String languageCode;

    @Schema(description = "로그인한_유저_국가코드")
    private String loginLanguage;
*/

    @Schema(description = "사용여부")
    private BooleanValueCode useYn;

    @Schema(description = "계정잠김여부")
    private BooleanValueCode lockYn;

    /**
     * 비번 6개월이상이어서 변경 여부
     */
    @Schema(description = "비번변경여부")
    private BooleanValueCode isChgPass;

    /**
     * 계정만료일(로그인일시 + 180일)
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "계정만료일")
    private LocalDateTime expireDate;

    /**
     * 최근_비밀번호변경_일시
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "최근_비밀번호변경_일시")
    private LocalDateTime lastPassChgTm;

    /**
     * OTP_실패_횟수
     * */
    @Schema(required = false)
    @Parameter(description = "OTP_실패_횟수")
    private Integer otpFailCnt;

    @Schema(description = "영업일자")
    private LocalDate workYmd;

    @Schema(description = "최초영업일자")
    private LocalDate firstWorkYmd;

    @Schema(description = "모바일기기로 로그인")
    private String isMobileLogin;

    @Override
    public String toString() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateSerializer());
        gsonBuilder.registerTypeAdapter(LocalTime.class, new LocalTimeSerializer());
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer());

        return gsonBuilder.setPrettyPrinting().create().toJson(this);
    }
}
