package com.shop.core.entity;

import com.google.gson.GsonBuilder;
import com.shop.core.entity.adapter.LocalDateSerializer;
import com.shop.core.entity.adapter.LocalDateTimeSerializer;
import com.shop.core.entity.adapter.LocalTimeSerializer;
import com.shop.core.interfaces.BaseEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * <pre>
 * Description: 메뉴_권한 Entity
 * Date: 2023/03/08 10:57 AM
 * Company: smart90
 * Author : luckeey
 * </pre>
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class MenuAuth extends BaseEntity implements Serializable {

    @Serial
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
