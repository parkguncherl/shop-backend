package com.shop.core.entity;

import com.shop.core.entity.adapter.LocalDateSerializer;
import com.shop.core.entity.adapter.LocalDateTimeSerializer;
import com.shop.core.entity.adapter.LocalTimeSerializer;
import com.shop.core.interfaces.BaseEntity;
import com.google.gson.GsonBuilder;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

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
@Schema(description = "당사정보 Entity")
public class Home extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1533462305024643988L;

    @Schema(description = "업체명")
    private String homeNm;

    @Schema(description = "당사팩스번호")
    private String homeFaxNo;

    @Schema(description = "당사주소")
    private String homeAddr;

    @Schema(description = "회사전화번호")
    private String homeTelNo;

    @Schema(description = "대표")
    private String ceoNm;

    @Schema(description = "회사연락처")
    private String repTelNo;

    @Schema(description = "사업자_번호")
    private String homeCompNo;

    @Schema(description = "회사이메일")
    private String homeEmail;

    @Schema(description = "회사입금계좌")
    private String homeAccount;

    @Override
    public String toString() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateSerializer());
        gsonBuilder.registerTypeAdapter(LocalTime.class, new LocalTimeSerializer());
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer());

        return gsonBuilder.setPrettyPrinting().create().toJson(this);
    }
}