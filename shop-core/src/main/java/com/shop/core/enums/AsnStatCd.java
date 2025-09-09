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
 * Description: asn 타입
 * Date: 2023/03/13 17:35 PM
 * Company: smart90
 * Author : luckeey
 * 1	발주예정
 * 2	발주확정
 * 5	입하예정
 * 9	입하완료
 * </pre>
 */
public enum AsnStatCd implements CodeEnum {
	ASN_REQ     	("1"		, "발주예정"),
	ASN_COMPLETE    ("2"		, "발주확정"),
	FACTORY_OUT     ("3"		, "입하예정"),
	STOCK_REQ     	("9"		, "입하완료"),
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

	AsnStatCd(String code, String message) {
		this.code = code;
		this.message = message;
	}

	AsnStatCd(String code, String message, String enMessage) {
		this.code = code;
		this.message = message;
	}

	private static final Map<String, AsnStatCd> LOOKUP = new HashMap<>();

	static {
		for (AsnStatCd type : EnumSet.allOf(AsnStatCd.class)) {
			LOOKUP.put(type.code, type);
		}
	}

	@MappedTypes(AsnStatCd.class)
	public static class TypeHandler extends CodeEnumTypeHandler<AsnStatCd> {
		public TypeHandler() {
			super(AsnStatCd.class);
		}
	}

	public static AsnStatCd get(String code) {
		return LOOKUP.get(code);
	}

}

