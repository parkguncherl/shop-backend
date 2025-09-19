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
public enum NoticeCd implements CodeEnum {
	NOTICE     	("20110"		, "공지사항"),
	MENUAL     	("20120"		, "메뉴얼");

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

	NoticeCd(String code, String message) {
		this.code = code;
		this.message = message;
	}

	NoticeCd(String code, String message, String enMessage) {
		this.code = code;
		this.message = message;
	}

	private static final Map<String, NoticeCd> LOOKUP = new HashMap<>();

	static {
		for (NoticeCd type : EnumSet.allOf(NoticeCd.class)) {
			LOOKUP.put(type.code, type);
		}
	}

	@MappedTypes(NoticeCd.class)
	public static class TypeHandler extends CodeEnumTypeHandler<NoticeCd> {
		public TypeHandler() {
			super(NoticeCd.class);
		}
	}

	public static NoticeCd get(String code) {
		return LOOKUP.get(code);
	}

}

