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
public enum StoreReqCd implements CodeEnum {

    REQUARE     	("1"		, "매장분요청"),
    RETURN		    ("2"		, "매장분반납"),
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

    StoreReqCd(String code, String message) {
        this.code = code;
        this.message = message;
    }

    StoreReqCd(String code, String message, String enMessage) {
        this.code = code;
        this.message = message;
    }

    private static final Map<String, StoreReqCd> LOOKUP = new HashMap<>();

    static {
        for (StoreReqCd type : EnumSet.allOf(StoreReqCd.class)) {
            LOOKUP.put(type.code, type);
        }
    }

    @MappedTypes(StoreReqCd.class)
    public static class TypeHandler extends CodeEnumTypeHandler<StoreReqCd> {
        public TypeHandler() {
            super(StoreReqCd.class);
        }
    }

    public static StoreReqCd get(String code) {
        return LOOKUP.get(code);
    }

}


