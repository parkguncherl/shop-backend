package com.shop.core.enums;

import com.shop.core.interfaces.CodeEnum;
import com.shop.core.interfaces.CodeEnumTypeHandler;
import org.apache.ibatis.type.MappedTypes;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * <pre>
 * Description: 주문 상태 (TB_CODE.CODE_UPPER = 10100)
 * </pre>
 */
public enum OrderStatus implements CodeEnum {

    ORDER    ("O", "주문접수", "ORDER"),
    PAID     ("P", "결제완료", "PAID"),
    READY    ("R", "배송준비", "READY"),
    SHIPPED  ("S", "배송중",  "SHIPPED"),
    DELIVERED("D", "배송완료", "DELIVERED"),
    CANCEL   ("C", "취소",    "CANCEL"),
    ;

    private final String code;
    private final String message;
    private final String enMessage;

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        Locale locale = LocaleContextHolder.getLocale();
        if (locale == Locale.ENGLISH) {
            return enMessage != null ? enMessage : message;
        }
        return message;
    }

    OrderStatus(String code, String message, String enMessage) {
        this.code = code;
        this.message = message;
        this.enMessage = enMessage;
    }

    private static final Map<String, OrderStatus> LOOKUP = new HashMap<>();

    static {
        for (OrderStatus s : EnumSet.allOf(OrderStatus.class)) {
            LOOKUP.put(s.code, s);
        }
    }

    @MappedTypes(OrderStatus.class)
    public static class TypeHandler extends CodeEnumTypeHandler<OrderStatus> {
        public TypeHandler() {
            super(OrderStatus.class);
        }
    }

    public static OrderStatus get(String code) {
        return LOOKUP.get(code);
    }
}
