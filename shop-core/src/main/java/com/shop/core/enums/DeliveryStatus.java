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
 * Description: 배송 상태 (TB_CODE.CODE_UPPER = 10110)
 * </pre>
 */
public enum DeliveryStatus implements CodeEnum {

    READY    ("R", "배송준비", "READY"),
    SHIPPED  ("S", "배송중",  "SHIPPED"),
    DELIVERED("D", "배송완료", "DELIVERED"),
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

    DeliveryStatus(String code, String message, String enMessage) {
        this.code = code;
        this.message = message;
        this.enMessage = enMessage;
    }

    private static final Map<String, DeliveryStatus> LOOKUP = new HashMap<>();

    static {
        for (DeliveryStatus s : EnumSet.allOf(DeliveryStatus.class)) {
            LOOKUP.put(s.code, s);
        }
    }

    @MappedTypes(DeliveryStatus.class)
    public static class TypeHandler extends CodeEnumTypeHandler<DeliveryStatus> {
        public TypeHandler() {
            super(DeliveryStatus.class);
        }
    }

    public static DeliveryStatus get(String code) {
        return LOOKUP.get(code);
    }
}
