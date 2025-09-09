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
public enum PayMethodCd implements CodeEnum {

    ACCOUNT    	("A"		, "계좌이체"),
    CASH		("R"		, "현금"),
    EMPTY		("E"		, "공전표"), // 샘플등 으로 전표가 존재하지 않을때 하지만 이력으로 조회시 만들어야 하는경우
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

    PayMethodCd(String code, String message) {
        this.code = code;
        this.message = message;
    }

    PayMethodCd(String code, String message, String enMessage) {
        this.code = code;
        this.message = message;
    }

    private static final Map<String, PayMethodCd> LOOKUP = new HashMap<>();

    static {
        for (PayMethodCd type : EnumSet.allOf(PayMethodCd.class)) {
            LOOKUP.put(type.code, type);
        }
    }

    @MappedTypes(PayMethodCd.class)
    public static class TypeHandler extends CodeEnumTypeHandler<PayMethodCd> {
        public TypeHandler() {
            super(PayMethodCd.class);
        }
    }

    public static PayMethodCd get(String code) {
        return LOOKUP.get(code);
    }

}


