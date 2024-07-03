package com.smart.core.enums;

import com.smart.core.interfaces.CodeEnum;
import com.smart.core.interfaces.CodeEnumTypeHandler;
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
public enum LangType implements CodeEnum {

	ALL     	("A"		, "모든문자 다 허용 (한글 영문 숫자 특수)"),
	ENG     	("E"		, "한글있으면 에러(영문 숫자 특수)"),
	DIG     	("D"		, "숫자만 허용"),
	MAIL     	("M"		, "이메일"),
	PHONE     	("P"		, "전화번호-없이"),
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

	LangType(String code, String message) {
		this.code = code;
		this.message = message;
	}

	private static final Map<String, LangType> LOOKUP = new HashMap<>();

	static {
		for (LangType type : EnumSet.allOf(LangType.class)) {
			LOOKUP.put(type.code, type);
		}
	}

	@MappedTypes(LangType.class)
	public static class TypeHandler extends CodeEnumTypeHandler<LangType> {
		public TypeHandler() {
			super(LangType.class);
		}
	}

	public static LangType get(String code) {
		return LOOKUP.get(code);
	}

}

