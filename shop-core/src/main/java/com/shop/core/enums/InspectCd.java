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
 * Description: 재고실사
 * Date: 2025/06/09 17:35 PM
 * Company: smart90
 * Author : luckeey
 * </pre>
 */
public enum InspectCd implements CodeEnum {

	OUT     	("1"		, "출고실사"),
	INVEN     	("2"		, "재고실사"),
	ERROR		("3"		, "오출고조정"),
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

	InspectCd(String code, String message) {
		this.code = code;
		this.message = message;
	}

	InspectCd(String code, String message, String enMessage) {
		this.code = code;
		this.message = message;
	}

	private static final Map<String, InspectCd> LOOKUP = new HashMap<>();

	static {
		for (InspectCd type : EnumSet.allOf(InspectCd.class)) {
			LOOKUP.put(type.code, type);
		}
	}

	@MappedTypes(InspectCd.class)
	public static class TypeHandler extends CodeEnumTypeHandler<InspectCd> {
		public TypeHandler() {
			super(InspectCd.class);
		}
	}

	public static InspectCd get(String code) {
		return LOOKUP.get(code);
	}

}

