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
 * Author: luckeey
 * </pre>
 */
public enum OrderDetCd implements CodeEnum {

	NOT_SAIL     	("80"		, "미출"),
	RETURN     	("40"		, "반품"),
	SAMPLE		("50"		, "샘플"),
	DO0M_TRAN		("99"		, "미송"),
	SAIL		("90"		, "판매");

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

	OrderDetCd(String code, String message) {
		this.code = code;
		this.message = message;
	}

	OrderDetCd(String code, String message, String enMessage) {
		this.code = code;
		this.message = message;
	}

	private static final Map<String, OrderDetCd> LOOKUP = new HashMap<>();

	static {
		for (OrderDetCd type : EnumSet.allOf(OrderDetCd.class)) {
			LOOKUP.put(type.code, type);
		}
	}

	@MappedTypes(OrderDetCd.class)
	public static class TypeHandler extends CodeEnumTypeHandler<OrderDetCd> {
		public TypeHandler() {
			super(OrderDetCd.class);
		}
	}

	public static OrderDetCd get(String code) {
		return LOOKUP.get(code);
	}

}

