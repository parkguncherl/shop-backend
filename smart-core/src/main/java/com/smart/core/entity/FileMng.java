package com.smart.core.entity;

import com.google.gson.GsonBuilder;
import com.smart.core.entity.adapter.LocalDateSerializer;
import com.smart.core.entity.adapter.LocalDateTimeSerializer;
import com.smart.core.entity.adapter.LocalTimeSerializer;
import com.smart.core.interfaces.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

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
@Schema(description = "파일관리 Entity")
public class FileMng extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -7404284196980959337L;

    /**
     * 아이디(PK)
     */
    @Schema(description = "아이디")
    private Integer id;

    /**
     * 파일_시퀀스
     */
    private Integer fileSeq;

    /**
     * 파일_경로
     */
    private String filePath;

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
    private Long fileSize;

    @Override
    public String toString() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateSerializer());
        gsonBuilder.registerTypeAdapter(LocalTime.class, new LocalTimeSerializer());
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer());

        return gsonBuilder.setPrettyPrinting().create().toJson(this);
    }
}
