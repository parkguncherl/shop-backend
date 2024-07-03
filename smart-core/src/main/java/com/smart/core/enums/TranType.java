package com.smart.core.enums;

import com.smart.core.interfaces.CodeEnum;
import com.smart.core.interfaces.CodeEnumTypeHandler;
import org.apache.ibatis.type.MappedTypes;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * <pre>
 * Description : 접속 타입 속성값
 * Date : 2023/03/13 17:35 PM
 * Company : smart90
 * Author : kdonghwa
 * </pre>
 */
public enum TranType implements CodeEnum {

	LOGIN     	("L"		, "로그인", "log in"),
	LOGOUT     	("O"		, "로그아웃", "log out"),
	INSERT		("I"		, "추가", "insert"),
	GET			("G"		, "조회", "search"),
	MODIFY		("M"		, "수정", "modify"),
	DELETE		("D"		, "삭제", "delete"),
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

	TranType(String code, String message) {
		this.code = code;
		this.message = message;
	}

	TranType(String code, String message, String enMessage) {
		this.code = code;
		this.message = message;
		this.enMessage = enMessage;
	}

	private static final Map<String, TranType> LOOKUP = new HashMap<>();

	static {
		for (TranType type : EnumSet.allOf(TranType.class)) {
			LOOKUP.put(type.code, type);
		}
	}

	@MappedTypes(TranType.class)
	public static class TypeHandler extends CodeEnumTypeHandler<TranType> {
		public TypeHandler() {
			super(TranType.class);
		}
	}

	public static TranType get(String code) {
		return LOOKUP.get(code);
	}

}

