package com.shop.core.enums;

import com.shop.core.interfaces.CodeEnum;
import com.shop.core.interfaces.CodeEnumTypeHandler;
import org.apache.ibatis.type.MappedTypes;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 * Description: 접속 타입 속성값
 * Date: 2023/03/13 17:35 PM
 * Company: smart90
 * Author: luckeey
 * </pre>
 */
public enum GlobalConst implements CodeEnum {
    YES			        ("Y", "YES"),
    NO			        ("N", "NO"),
    TOP_MENU			("TOP", "TOP"),

    REP_EMAIL			("admin@shop.com", "대표 이메일"),
    // 코드 대분류정보
    MAGIC_PASSWORD      ("1230","만능비밀번호"),
    LOGIS_ADMIN	    	("570", "물류관리자"),
    ;

    private String code;
    private String message;

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    GlobalConst(String code, String message) {
        this.code = code;
        this.message = message;
    }

    private static final Map<String, GlobalConst> LOOKUP = new HashMap<>();

    static {
        for (GlobalConst type : EnumSet.allOf(GlobalConst.class)) {
            LOOKUP.put(type.code, type);
        }
    }

    @MappedTypes(GlobalConst.class)
    public static class TypeHandler extends CodeEnumTypeHandler<GlobalConst> {

        public TypeHandler() {
            super(GlobalConst.class);
        }
    }

    public static GlobalConst get(String code) {
        return LOOKUP.get(code);
    }
}
