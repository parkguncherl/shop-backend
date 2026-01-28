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
import java.util.Optional;

/**
 * <pre>
 * Description: 코드_관리 Entity
 * Date: 2023/02/06 12:07 PM
 * Company: smart
 * Author: luckeey
 * </pre>
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "코드 Entity")
public class Code extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 3056713244668954521L;

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
