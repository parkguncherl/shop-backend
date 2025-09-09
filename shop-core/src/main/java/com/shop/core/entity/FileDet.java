package com.shop.core.entity;

import com.google.gson.GsonBuilder;
import com.shop.core.entity.adapter.LocalDateSerializer;
import com.shop.core.entity.adapter.LocalDateTimeSerializer;
import com.shop.core.entity.adapter.LocalTimeSerializer;
import com.shop.core.interfaces.BaseEntity;
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
@EqualsAndHashCode(callSuper = false)
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "파일관리 Entity")
public class FileDet extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = -7404284196980959337L;

    /**
     * 아이디(PK)
     */
    @Schema(description = "아이디")
    private Integer id;

    /**
     * 아이디(PK)
     */
    @Schema(description = "파일아이디")
    private Integer fileId;

    /**
     * 파일_시퀀스
     */
    private Integer fileSeq;

    /**
     * 파일_경로
     */
    private String bucketName;

    /**
     * 시스템_파일명
     */
    private String sysFileNm;

    /**
     * 확장자
     */
    private String fileExt;

    /**
     * 원본_파일명
     */
    private String fileNm;

    /**
     * 파일_사이즈
     */
    private Integer fileSize;

    @Override
    public String toString() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateSerializer());
        gsonBuilder.registerTypeAdapter(LocalTime.class, new LocalTimeSerializer());
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer());

        return gsonBuilder.setPrettyPrinting().create().toJson(this);
    }
}
