package com.shop.core.entity;

import com.shop.core.entity.adapter.LocalDateSerializer;
import com.shop.core.entity.adapter.LocalDateTimeSerializer;
import com.shop.core.entity.adapter.LocalTimeSerializer;
import com.shop.core.interfaces.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.gson.GsonBuilder;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@EqualsAndHashCode
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "작업 Entity")
public class Job extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 8551756517112876015L;

    @Schema(description = "아이디(PK)")
    private Integer id;

    @Schema(description = "처리요청일")
    private LocalDate tranYmd;

    @Schema(description = "영업일")
    private LocalDate workYmd;

    @Schema(description = "파트너ID(FK)")
    private Integer partnerId;

    @Schema(description = "LOGIS_ID(FK)")
    private Integer logisId;

    @Schema(description = "주문ID(FK)")
    private Integer orderId;

    @Schema(description = "작업구분")
    private String jobType;

    @Schema(description = "작업상태")
    private String jobStatCd;

    @Schema(description = "전표번호")
    private Integer chitNo;

    @Schema(description = "소매처id(FK)")
    private Integer sellerId;

    @Schema(description = "처리수량")
    private Integer tranCnt;

    @Schema(description = "처리순서")
    private Integer tranSeq;

    @Schema(description = "비고")
    private String jobEtc;

    @Schema(description = "매장판매여부")
    private String onSiteYn;

    /** 출고일시 */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "출고일시")
    protected LocalDateTime outTm;

    @Schema(description = "보류상태코드")
    private String boryuStatCd;
    
    
    @Override
    public String toString() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateSerializer());
        gsonBuilder.registerTypeAdapter(LocalTime.class, new LocalTimeSerializer());
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer());

        return gsonBuilder.setPrettyPrinting().create().toJson(this);
    }
}