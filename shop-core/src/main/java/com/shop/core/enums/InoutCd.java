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
 * Description: 입금관련 발생구분코드
 * Date: 2024/08/13 17:35 PM
 * Company: smart90
 * Author : luckeey
 * </pre>
 */
public enum InoutCd implements CodeEnum {

	ORDER     	("O"		, "주문"),
	DEPOSIT		("D"		, "입금"),
	WATING		("W"		, "보류"),
	ONLY_SAMPLE	("S"		, "샘플"), // 모든계약이 다 샘플
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

	InoutCd(String code, String message) {
		this.code = code;
		this.message = message;
	}

	InoutCd(String code, String message, String enMessage) {
		this.code = code;
		this.message = message;
	}

	private static final Map<String, InoutCd> LOOKUP = new HashMap<>();

	static {
		for (InoutCd type : EnumSet.allOf(InoutCd.class)) {
			LOOKUP.put(type.code, type);
		}
	}

	@MappedTypes(InoutCd.class)
	public static class TypeHandler extends CodeEnumTypeHandler<InoutCd> {
		public TypeHandler() {
			super(InoutCd.class);
		}
	}

	public static InoutCd get(String code) {
		return LOOKUP.get(code);
	}

}

