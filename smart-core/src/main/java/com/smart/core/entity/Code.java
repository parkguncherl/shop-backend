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
import java.util.Optional;

/**
 * <pre>
 * Description : 코드_관리 Entity
 * Date : 2023/02/06 12:07 PM
 * Company : smart
 * Author : sclee9946
 * </pre>
 */
@Getter
@Setter
@EqualsAndHashCode
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "코드 Entity")
public class Code extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 3056713244958954521L;

    /** 아이디 */
    private Integer id;

    /** 상위_코드 */
    private String codeUpper;

    /** 코드 */
    private String codeCd;

    /** 코드_명 */
    private String codeNm;

    /** 코드_설명 */
    private String codeDesc;

    /** 코드_기타_정보 */
    private String codeEtc1;

    /** 코드_영문_정보 */
    private String codeEtc2;
    

    /** 코드_순서 */
    private Integer codeOrder;

    /**
     * 코드_삭제여부
     */
    @Schema(description = "코드_삭제여부")
    private String delYn;

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
    public Boolean equals(Code target) {
        if (codeNm.equals(target.codeNm) && codeEtc1.equals(target.codeEtc1)
                && Optional.ofNullable(codeDesc).equals(Optional.ofNullable(target.codeDesc))
                && Optional.ofNullable(codeEtc1).equals(Optional.ofNullable(target.codeEtc1))) {
            return true;
        }
        return false;
    }
}
