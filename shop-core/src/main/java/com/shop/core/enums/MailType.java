package com.shop.core.enums;

import com.shop.core.interfaces.CodeEnum;
import com.shop.core.interfaces.CodeEnumTypeHandler;
import org.apache.ibatis.type.MappedTypes;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 * Description: 체크컬럼 타입
 * Date: 2023/03/13 17:35 PM
 * Company: smart90
 * Author : luckeey
 * </pre>
 */
public enum MailType implements CodeEnum {

	CREATE_ID     	("1"		, "계정생성"),
	OTP_2FACTOR     ("2"		, "OTP 인증(로그인)"),
	DEL_ID     		("3"		, "계정삭제"),
	PRE_EXP     	("4"		, "휴면예고"),
	EXP_FINISH     	("5"		, "휴면돌입"),
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

    MailType(String code, String message) {
        this.code = code;
        this.message = message;
    }

    private static final Map<String, MailType> LOOKUP = new HashMap<>();

    static {
        for (MailType type : EnumSet.allOf(MailType.class)) {
            LOOKUP.put(type.getCode(), type);
        }
    }

    @MappedTypes(MailType.class)
    public static class TypeHandler extends CodeEnumTypeHandler<MailType> {

        public TypeHandler() {
            super(MailType.class);
        }
    }

    public static MailType get(String code) {
        return LOOKUP.get(code);
    }

}

