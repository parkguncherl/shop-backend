package com.shop.core.enums;

import com.shop.core.interfaces.CodeEnum;
import com.shop.core.interfaces.CodeEnumTypeHandler;
import org.apache.ibatis.type.MappedTypes;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 * Description: VAT 발행타입
 * Date: 2024/08/13 17:35 PM
 * Company: smart90
 * Author : luckeey
 * </pre>
 */
public enum VatIssuType implements CodeEnum {

	REQUIRE     	("R"		, "청구"),
	DEPOSIT     	("D"		, "입금"),
	ISSU     		("I"		, "발행")
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

	VatIssuType(String code, String message) {
		this.code = code;
		this.message = message;
	}

	private static final Map<String, VatIssuType> LOOKUP = new HashMap<>();

	static {
		for (VatIssuType type : EnumSet.allOf(VatIssuType.class)) {
			LOOKUP.put(type.code, type);
		}
	}

	@MappedTypes(VatIssuType.class)
	public static class TypeHandler extends CodeEnumTypeHandler<VatIssuType> {
		public TypeHandler() {
			super(VatIssuType.class);
		}
	}

	public static VatIssuType get(String code) {
		return LOOKUP.get(code);
	}

}

