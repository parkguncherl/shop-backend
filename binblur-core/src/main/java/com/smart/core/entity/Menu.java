package com.binblur.core.entity;

import com.google.gson.GsonBuilder;
import com.binblur.core.entity.adapter.LocalDateSerializer;
import com.binblur.core.entity.adapter.LocalDateTimeSerializer;
import com.binblur.core.entity.adapter.LocalTimeSerializer;
import com.binblur.core.interfaces.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

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
@Schema(name = "Menu", description = "메뉴 Entity")
public class Menu extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 3056713244958954521L;

    /** 아이디(PK) */
    private Integer id;

    /** 상위_메뉴_코드 */
    private String upMenuCd;

    /** 상위_메뉴_명 */
    private String upMenuNm;

    /** 메뉴_코드 */
    private String menuCd;

    /** 메뉴_순서 */
    private Integer menuOrder;

    /** 메뉴명 */
    private String menuNm;

    /** 메뉴명 */
    private String menuEngNm;

    /** 메뉴_URI */
    private String menuUri;

    /** 권한 */
    private String authCd;

    @Override
    public String toString() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateSerializer());
        gsonBuilder.registerTypeAdapter(LocalTime.class, new LocalTimeSerializer());
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer());

        return gsonBuilder.setPrettyPrinting().create().toJson(this);
    }
}
