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
 * Description : 주문_관리 Entity
 * Company : home
 * Author : park
 * </pre>
 */
@Getter
@Setter
@EqualsAndHashCode
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "Order", description = "주문 Entity")
public class Order extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 3056713244958954521L;

    /** 상품명 */
    private String skuNm;

    /** 상품코드 */
    private String skuCd;

    /** 사이즈 */
    private String skuSize;

    /** 색상 */
    private String skuColor;

    /** 판매가 */
    private String sellAmt;

    /** 재고수량 */
    private String inventoryAmt;

    @Override
    public String toString() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateSerializer());
        gsonBuilder.registerTypeAdapter(LocalTime.class, new LocalTimeSerializer());
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer());

        return gsonBuilder.setPrettyPrinting().create().toJson(this);
    }
}
