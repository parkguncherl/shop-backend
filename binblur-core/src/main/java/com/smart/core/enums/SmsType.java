package com.binblur.core.enums;

import com.binblur.core.interfaces.CodeEnum;
import com.binblur.core.interfaces.CodeEnumTypeHandler;
import org.apache.ibatis.type.MappedTypes;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 * Description : 체크컬럼 타입
 * Date : 2023/03/13 17:35 PM
 * Company : smart90
 * Author : luckeey
 * </pre>
 */
public enum SmsType implements CodeEnum {

	UN_LOCK     	("1"		, "계정 잠금 해제"),
	FIND_PASS       ("2"		, "비밀번호 찾기"),
	PASS_INIT       ("3"		, "비밀번호 초기화"),
	ALERT           ("4"		, "시스템알림메시지"),
	EVENT           ("5"		, "이벤트알람"),
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

	SmsType(String code, String message) {
		this.code = code;
		this.message = message;
	}

	private static final Map<String, SmsType> LOOKUP = new HashMap<>();

	static {
		for (SmsType type : EnumSet.allOf(SmsType.class)) {
			LOOKUP.put(type.code, type);
		}
	}

	@MappedTypes(SmsType.class)
	public static class TypeHandler extends CodeEnumTypeHandler<SmsType> {
		public TypeHandler() {
			super(SmsType.class);
		}
	}

	public static SmsType get(String code) {
		return LOOKUP.get(code);
	}

}

