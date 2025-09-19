package com.shop.core.entity;

import com.google.gson.GsonBuilder;
import com.shop.core.entity.adapter.LocalDateSerializer;
import com.shop.core.entity.adapter.LocalDateTimeSerializer;
import com.shop.core.interfaces.BaseEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

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
public class Auth extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 3056713212258954521L;

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
