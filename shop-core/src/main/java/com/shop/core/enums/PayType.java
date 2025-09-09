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
 * Description: 접속 타입 속성값
 * Date: 2023/03/13 17:35 PM
 * Company: smart90
 * Author : luckeey
 * </pre>
 */
public enum PayType implements CodeEnum {

    REQUARE     	("1"		, "청구"),
    RECEIPT		    ("2"		, "영수"),
    RECEIPT_COMP	("3"		, "영수(완)"),
    ACCOUNT     	("4"		, "통장"),
    RETURN     	    ("9"		, "반품"),
    NONE_PAY		("0"		, "미입금"),
    ;

    private String code;
    private String message;

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        Locale locale = LocaleContextHolder.getLocale();
        return message;
    }

    PayType(String code, String message) {
        this.code = code;
        this.message = message;
    }

    PayType(String code, String message, String enMessage) {
        this.code = code;
        this.message = message;
    }

    private static final Map<String, PayType> LOOKUP = new HashMap<>();

    static {
        for (PayType type : EnumSet.allOf(PayType.class)) {
            LOOKUP.put(type.code, type);
        }
    }

    @MappedTypes(PayType.class)
    public static class TypeHandler extends CodeEnumTypeHandler<PayType> {
        public TypeHandler() {
            super(PayType.class);
        }
    }

    public static PayType get(String code) {
        return LOOKUP.get(code);
    }

}


