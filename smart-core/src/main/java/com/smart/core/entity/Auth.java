package com.smart.core.entity;

import com.google.gson.GsonBuilder;
import com.smart.core.entity.adapter.LocalDateSerializer;
import com.smart.core.entity.adapter.LocalDateTimeSerializer;
import com.smart.core.interfaces.BaseEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

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
public class Auth extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 3056713244958954521L;

    /** 아이디(PK) */
    private Integer id;

    /** 메뉴 */
    private String menuCd;

    /** 권한 */
    private String authCd;

    /** 권한 name */
    private String authNm;

    /** 읽기권한 */
    private String menuReadYn;

    /** 읽기권한 */
    private String menuAllAuthYn;

    /** 수정권한 */
    private String menuUpdYn;

    /** 엑셀전환 권한 */
    private String menuExcelYn;

    @Override
    public String toString() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateSerializer());
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer());

        return gsonBuilder.setPrettyPrinting().create().toJson(this);
    }
}
