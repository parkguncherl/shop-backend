package com.smart.core.enums;

import com.smart.core.interfaces.CodeEnum;
import com.smart.core.interfaces.CodeEnumTypeHandler;
import org.apache.ibatis.type.MappedTypes;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 * Description : 필수 타입
 * Date : 2023/03/13 17:35 PM
 * Company : smart90
 * Author : luckeey
 * </pre>
 */
public enum EsseType implements CodeEnum {

	ESS     	("E"		, "필수체크"),
	NUL     	("N"		, "필수 아닌값 체크"),
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

	EsseType(String code, String message) {
		this.code = code;
		this.message = message;
	}

	private static final Map<String, EsseType> LOOKUP = new HashMap<>();

	static {
		for (EsseType type : EnumSet.allOf(EsseType.class)) {
			LOOKUP.put(type.code, type);
		}
	}

	@MappedTypes(EsseType.class)
	public static class TypeHandler extends CodeEnumTypeHandler<EsseType> {
		public TypeHandler() {
			super(EsseType.class);
		}
	}

	public static EsseType get(String code) {
		return LOOKUP.get(code);
	}

}

