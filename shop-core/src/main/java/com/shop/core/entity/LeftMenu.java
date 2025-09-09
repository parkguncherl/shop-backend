package com.shop.core.entity;

import com.google.gson.GsonBuilder;
import com.shop.core.entity.adapter.LocalDateSerializer;
import com.shop.core.entity.adapter.LocalDateTimeSerializer;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <pre>
 * Description: 코드_관리 Entity
 * Date: 2023/02/06 12:07 PM
 * Company: smart
 * Author : luckeey
 * </pre>
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class LeftMenu implements Serializable {

    @Serial
    private static final long serialVersionUID = 3056713334958954521L;

    /** 메뉴_코드 */
    private String menuCd;

    /** 메뉴명 */
    private String menuNm;

    /** 메뉴_URI */
    private String menuUri;

    /** 아이콘클래스 */
    private String iconClassName;


    /** 하위메뉴리스트 */
    private List<LeftMenuSub> items;

    @Override
    public String toString() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateSerializer());
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer());

        return gsonBuilder.setPrettyPrinting().create().toJson(this);
    }
}
