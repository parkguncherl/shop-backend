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
 * Description: 장바구니 상태 (TB_CODE.CODE_UPPER = 10120)
 * </pre>
 */
public enum CartStatus implements CodeEnum {

    ACTIVE  ("A", "담긴상태", "Active"),
    ORDERED ("O", "주문",    "Ordered"),
    ;

    private String code;
    private String message;
    private String enMessage;

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

    CartStatus(String code, String message, String enMessage) {
        this.code = code;
        this.message = message;
        this.enMessage = enMessage;
    }

    private static final Map<String, CartStatus> LOOKUP = new HashMap<>();

    static {
        for (CartStatus s : EnumSet.allOf(CartStatus.class)) {
            LOOKUP.put(s.code, s);
        }
    }

    @MappedTypes(CartStatus.class)
    public static class TypeHandler extends CodeEnumTypeHandler<CartStatus> {
        public TypeHandler() {
            super(CartStatus.class);
        }
    }

    public static CartStatus get(String code) {
        return LOOKUP.get(code);
    }
}
