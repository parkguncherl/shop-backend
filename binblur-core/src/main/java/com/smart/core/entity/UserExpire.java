package com.binblur.core.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.gson.GsonBuilder;
import com.binblur.core.entity.adapter.LocalDateSerializer;
import com.binblur.core.entity.adapter.LocalDateTimeSerializer;
import com.binblur.core.entity.adapter.LocalTimeSerializer;
import com.binblur.core.enums.BooleanValueCode;
import com.binblur.core.interfaces.BaseEntity;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

/**
 * <pre>
 * Description : 계정_만료 Entity
 * Date : 2023/01/26 12:35 PM
 * Company : smart90
 * Author : harry
 * </pre>
 */
@Getter
@Setter
@EqualsAndHashCode
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "계정_만료 Entity")
public class UserExpire extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -1820666602919044604L;

    /**
     * 아이디(PK)
     */
    @Schema(description = "아이디")
    private Integer id;

    /**
     * 사용자_아이디
     */
    @Schema(description = "사용자_아이디")
    private Integer userId;

    /**
     * 로그인_아이디
     */
    @Schema(description = "로그인_아이디")
    private String loginId;

    /**
     * 로그인_비밀번호
     */
    //@JsonIgnore : 주석풀면 등록 시 컬럼값 NULL 처리됨 (주의)
    @Schema(description = "로그인_비밀번호")
    private String loginPass;

    /**
     * 회원_명
     */
    @Schema(description = "회원_명")
    private String userNm;

    /**
     * 권한코드
     */
    @Schema(description = "권한코드")
    private String authCd;

    /**
     * 회원_휴대폰번호
     */
    @Schema(description = "회원_휴대폰번호")
    private String phoneNo;

    /**
     * 소속_명
     */
    @Schema(description = "소속_명")
    private String belongNm;

    /**
     * 부서_명
     */
    @Schema(description = "부서_명")
    private String deptNm;

    /**
     * 직책_명
     */
    @Schema(description = "직책_명")
    private String positionNm;

    /**
     * 로그인_실패_회수
     */
    @Schema(description = "로그인_실패_회수")
    private Integer loginFailCnt;

    /**
     * 최근_로그인_일시
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "최근_로그인_일시")
    private LocalDateTime lastLoginDateTime;

    /**
     * 최초로그인여부
     */
    @Schema(description = "최초로그인여부")
    private BooleanValueCode firstLoginYn;

    /**
     * otp_발행번호
     */
    @Schema(description = "otp_발행번호")
    private String otpNo;

    /**
     * otp_발생일시
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "otp_발생일시")
    private LocalDateTime otpIssuDateTime;

    /**
     * 로그인한_유저_국가코드
     */
    @Schema(description = "로그인한_유저_국가코드")
    private String languageCode;

    /**
     * 사용여부
     */
    @Schema(description = "사용여부")
    private BooleanValueCode useYn;

    /**
     * 계정잠김여부
     */
    @Schema(description = "계정잠김여부")
    private BooleanValueCode lockYn;

    /**
     * 최근_비밀번호변경_일시
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "최근_비밀번호변경_일시")
    private LocalDateTime lastPassChgTm;

    @Schema(required = false)
    @Parameter(description = "OTP_실패_횟수")
    private Integer otpFailCnt;

    @Override
    public String toString() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateSerializer());
        gsonBuilder.registerTypeAdapter(LocalTime.class, new LocalTimeSerializer());
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer());

        return gsonBuilder.setPrettyPrinting().create().toJson(this);
    }

    /**
     * 이전 값과 현재 값 비교
     *
     * @param target
     * @return
     */
    public Boolean equals(UserExpire target) {
        if (loginId.equals(target.loginId) && userNm.equals(target.userNm)
            && authCd.equals(target.authCd) && phoneNo.equals(target.phoneNo)
            && Optional.ofNullable(belongNm).equals(Optional.ofNullable(target.belongNm)) && Optional.ofNullable(deptNm).equals(Optional.ofNullable(target.deptNm))
            && Optional.ofNullable(positionNm).equals(Optional.ofNullable(target.positionNm)) && loginFailCnt.equals(target.loginFailCnt)
            && lastLoginDateTime.equals(target.lastLoginDateTime) && firstLoginYn.equals(target.firstLoginYn)
            && Optional.ofNullable(otpNo).equals(Optional.ofNullable(target.otpNo)) && otpIssuDateTime.equals(target.otpIssuDateTime)
            && languageCode.equals(target.languageCode) && useYn.equals(target.useYn) && lockYn.equals(target.lockYn)
            && otpFailCnt.equals(target.otpFailCnt)) {
            return true;
        }
        return false;
    }
}
