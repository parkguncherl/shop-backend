package com.smart.core.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.gson.GsonBuilder;
import com.smart.core.entity.adapter.LocalDateSerializer;
import com.smart.core.entity.adapter.LocalDateTimeSerializer;
import com.smart.core.entity.adapter.LocalTimeSerializer;
import com.smart.core.enums.BooleanValueCode;
import com.smart.core.interfaces.BaseEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * <pre>
 * Description : SMS Entity
 * Date : 2023/03/08 11:23 AM
 * Company : smart90
 * Author : sclee9946
 * </pre>
 */
@Getter
@Setter
@EqualsAndHashCode
public class Sms extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -6986705454367215426L;

    /** 아이디(PK) */
    private Integer id;

    /** 전송 번호 mt_tran 과 연결된 key  */
    private Integer tranNo;

    /** SMS_코드 */
    private String smsCd;

    /** SMS_재전송여부 */
    private BooleanValueCode retryYn;

    /** SMS_전송_연락처 */
    private String smsFromTel;

    /** SMS_대상_연락처 */
    private String smsToTel;

    /** SMS_제목 */
    private String smsTitle;

    /** SMS_내용 */
    private String smsCntn;

    /** 서버이벤트 ID */
    private Integer svrEventId;

    /** 이벤트 ID */
    private Integer eventId;

    /** 이벤트 로그 ID */
    private Integer eventLogId;

    /** 충전소 ID */
    private Integer stationId;

    /** 포스트 ID */
    private Integer postId;

    /** 프로파일 ID */
    private Integer profileId;

    /** 수신 사용자 ID */
    private Integer recvId;

    /** SMS_전송월 */
    private String smsMon;

    /** SMS_전송_일시 */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime smsSendDt;

    /** SMS_결과_코드 */
    private String smsRsltCd;

    /** SMS_결과_메시지 */
    private String smsRsltMsg;

    /** SMS_에이전트_결과_메시지 */
    private String rsltCode;

    @Override
    public String toString() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateSerializer());
        gsonBuilder.registerTypeAdapter(LocalTime.class, new LocalTimeSerializer());
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer());

        return gsonBuilder.setPrettyPrinting().create().toJson(this);
    }
}
