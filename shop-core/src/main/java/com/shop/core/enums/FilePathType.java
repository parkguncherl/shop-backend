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
public enum FilePathType implements CodeEnum {

	PARTNER     	("PARTNER"		, "화주"),
	PRODUCT     	("PRODUCT"		, "상품"),
	PRODUCT_CONTENTS     	("news"		, "상품 컨텐츠"),

	;

	private String code;
	private String message;
	private String enMessage;

	@Override
	public String getCode() {
		return code;
	}

	@Override
	public String getMessage() {
		Locale locale = LocaleContextHolder.getLocale();
		if (locale == Locale.ENGLISH) {
			return enMessage != null ? enMessage : message;
		}
		return message;
	}

	FilePathType(String code, String message) {
		this.code = code;
		this.message = message;
	}

	FilePathType(String code, String message, String enMessage) {
		this.code = code;
		this.message = message;
		this.enMessage = enMessage;
	}

	private static final Map<String, FilePathType> LOOKUP = new HashMap<>();

	static {
		for (FilePathType type : EnumSet.allOf(FilePathType.class)) {
			LOOKUP.put(type.code, type);
		}
	}

	@MappedTypes(FilePathType.class)
	public static class TypeHandler extends CodeEnumTypeHandler<FilePathType> {
		public TypeHandler() {
			super(FilePathType.class);
		}
	}

	public static FilePathType get(String code) {
		return LOOKUP.get(code);
	}

}

