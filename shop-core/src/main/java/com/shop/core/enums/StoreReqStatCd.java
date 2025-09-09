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
public enum StoreReqStatCd implements CodeEnum {

/*
    N	취소
    C	완료
    R	요청
    S	미처리
*/

    NOT     	("N"		, "취소"),
    COMPLETE	("C"		, "완료"),
    REQUIRE     ("R"		, "요청"),
    //SUB_TRAN	("S"		, "미처리"), // 미처리는 업무 프로세스상 없애도 될거 같다.
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

    StoreReqStatCd(String code, String message) {
        this.code = code;
        this.message = message;
    }

    StoreReqStatCd(String code, String message, String enMessage) {
        this.code = code;
        this.message = message;
    }

    private static final Map<String, StoreReqStatCd> LOOKUP = new HashMap<>();

    static {
        for (StoreReqStatCd type : EnumSet.allOf(StoreReqStatCd.class)) {
            LOOKUP.put(type.code, type);
        }
    }

    @MappedTypes(StoreReqStatCd.class)
    public static class TypeHandler extends CodeEnumTypeHandler<StoreReqStatCd> {
        public TypeHandler() {
            super(StoreReqStatCd.class);
        }
    }

    public static StoreReqStatCd get(String code) {
        return LOOKUP.get(code);
    }

}


