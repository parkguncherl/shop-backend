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
public enum OrderCd implements CodeEnum {

	SAMPLE_SAIL     	("7"		, "샘플판매"),
	ONLY_SAMPLE     	("5"		, "샘플"),
	NOT_SAIL		("8"		, "미출"),
	REQ_SAIL		("2"		, "요청"), // 요청은 모아 놨다 한번에 요청한다.
	RETURN_SAIL		("1"		, "반납"), // 반납음 반납요청시 바로 asn 에 넎는다.
	SAIL		("9"		, "판매"),
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

	OrderCd(String code, String message) {
		this.code = code;
		this.message = message;
	}

	OrderCd(String code, String message, String enMessage) {
		this.code = code;
		this.message = message;
	}

	private static final Map<String, OrderCd> LOOKUP = new HashMap<>();

	static {
		for (OrderCd type : EnumSet.allOf(OrderCd.class)) {
			LOOKUP.put(type.code, type);
		}
	}

	@MappedTypes(OrderCd.class)
	public static class TypeHandler extends CodeEnumTypeHandler<OrderCd> {
		public TypeHandler() {
			super(OrderCd.class);
		}
	}

	public static OrderCd get(String code) {
		return LOOKUP.get(code);
	}

}

