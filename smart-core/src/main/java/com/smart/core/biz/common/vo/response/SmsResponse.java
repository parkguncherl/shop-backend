package com.smart.core.biz.common.vo.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.smart.core.enums.BooleanValueCode;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * <pre>
 * Description : Sms Response
 * Date : 2023/04/10 05:58 PM
 * Company : smart90
 * Author : kdonghwa
 * </pre>
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@Schema(name = "SmsResponse")
public class SmsResponse {

    @Getter
    @Setter
    @EqualsAndHashCode
    @Schema(name = "SmsResponseSvrEventPaging", description = "서버이벤트별 SMS 페이징 응답", type = "object")
    public static class SvrEventPaging {

        @Schema(required = false)
        @Parameter(description = "NO")
        private Integer no;

        @Schema(required = false)
        @Parameter(description = "전체 카운트")
        protected Long totalRowCount;

        @Schema(required = false)
        @Parameter(description = "SMS ID")
        private Integer id;

        @Schema(required = false)
        @Parameter(description = "포스트명")
        private String postNm;

        @Schema(required = false)
        @Parameter(description = "포스트SN")
        private String postSn;

        @Schema(required = false)
        @Parameter(description = "충전소명")
        private String stationNm;

        @Schema(required = false)
        @Parameter(description = "SMS타입(S0008)")
        private String smsCd;

        @Schema(required = false)
        @Parameter(description = "SMS결과메시지")
        private String smsRsltMsg;

        @Schema(required = false)
        @Parameter(description = "발송자")
        private String sendUser;

        @Schema(required = false)
        @Parameter(description = "수신자명")
        private String receiverUserNm;

        @Schema(required = false)
        @Parameter(description = "수신자전화번호")
        private String receiverTel;

        @Schema(required = false)
        @Parameter(description = "발송자명")
        private String sendUserNm;

        @Schema(required = false)
        @Parameter(description = "SMS 내용")
        private String smsCntn;

        @Schema(required = false)
        @Parameter(description = "재발송여부")
        private BooleanValueCode retryYn;

        @Schema(required = false)
        @Parameter(description = "발송일시")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
        protected LocalDateTime smsSendDt;
    }

    @Getter
    @Setter
    @EqualsAndHashCode
    @Schema(name = "SmsResponsePostEventPaging", description = "충전기 이벤트별 SMS 페이징 응답", type = "object")
    public static class PostEventPaging {

        @Schema(required = false)
        @Parameter(description = "NO")
        private Integer no;

        @Schema(required = false)
        @Parameter(description = "전체 카운트")
        protected Long totalRowCount;

        @Schema(required = false)
        @Parameter(description = "SMS ID")
        private Integer id;

        @Schema(required = false)
        @Parameter(description = "포스트명")
        private String postNm;

        @Schema(required = false)
        @Parameter(description = "포스트SN")
        private String postSn;

        @Schema(required = false)
        @Parameter(description = "충전소명")
        private String stationNm;

        @Schema(required = false)
        @Parameter(description = "SMS타입(S0008)")
        private String smsCd;

        @Schema(required = false)
        @Parameter(description = "SMS결과메시지")
        private String smsRsltMsg;

        @Schema(required = false)
        @Parameter(description = "발송자")
        private String sendUser;

        @Schema(required = false)
        @Parameter(description = "수신자명")
        private String receiverUserNm;

        @Schema(required = false)
        @Parameter(description = "수신자전화번호")
        private String receiverTel;

        @Schema(required = false)
        @Parameter(description = "발송자명")
        private String sendUserNm;

        @Schema(required = false)
        @Parameter(description = "SMS 내용")
        private String smsCntn;

        @Schema(required = false)
        @Parameter(description = "재발송여부")
        private BooleanValueCode retryYn;

        @Schema(required = false)
        @Parameter(description = "발송일시")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
        protected LocalDateTime smsSendDt;
    }
}
