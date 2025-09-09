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
 * </pre>
 */
public enum AsnType implements CodeEnum {

	NORMAL_ASN     	("1"		, "정상발주"),
	MARKET_ASN     	("2"		, "매장반납"),
	ETC_ASN     	("3"		, "기타발주"),
	ETC_RETAIL_ASN 	("4"		, "기타매장반납"),
	REPAIR_ASN		("9"		, "수선발주"),
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

	AsnType(String code, String message) {
		this.code = code;
		this.message = message;
	}

	AsnType(String code, String message, String enMessage) {
		this.code = code;
		this.message = message;
	}

	private static final Map<String, AsnType> LOOKUP = new HashMap<>();

	static {
		for (AsnType type : EnumSet.allOf(AsnType.class)) {
			LOOKUP.put(type.code, type);
		}
	}

	@MappedTypes(AsnType.class)
	public static class TypeHandler extends CodeEnumTypeHandler<AsnType> {
		public TypeHandler() {
			super(AsnType.class);
		}
	}

	public static AsnType get(String code) {
		return LOOKUP.get(code);
	}

}

