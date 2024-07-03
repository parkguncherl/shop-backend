package com.smart.core.entity;

import com.google.gson.GsonBuilder;
import com.smart.core.entity.adapter.LocalDateSerializer;
import com.smart.core.entity.adapter.LocalDateTimeSerializer;
import com.smart.core.entity.adapter.LocalTimeSerializer;
import com.smart.core.interfaces.BaseEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * <pre>
 * Description : 메뉴_권한 Entity
 * Date : 2023/03/08 10:57 AM
 * Company : smart90
 * Author : sclee9946
 * </pre>
 */
@Getter
@Setter
@EqualsAndHashCode
public class MenuAuth extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 6432573770857950340L;

    /** 아이디(PK) */
    private Integer id;

    /** 권한_코드 */
    private String authCd;

    /** 메뉴_코드 */
    private String menuCd;

    /** 메뉴_조회권한_여부 */
    private String menuReadYn;

    /** 메뉴_수정권한_여부 */
    private String menuUpdYn;

    /** 메뉴_엑셀다운권한_여부 */
    private String menuExcelYn;

    @Override
    public String toString() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateSerializer());
        gsonBuilder.registerTypeAdapter(LocalTime.class, new LocalTimeSerializer());
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer());

        return gsonBuilder.setPrettyPrinting().create().toJson(this);
    }
}
