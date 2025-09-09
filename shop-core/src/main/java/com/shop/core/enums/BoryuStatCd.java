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
public enum BoryuStatCd implements CodeEnum {

	REQ		 ("1"		, "요청"), // 요청은 모아 놨다 한번에 요청한다.
	MOD		 ("2"		, "수정"), // 반납음 반납요청시 바로 asn 에 넎는다.
	CANCEL	 ("9"		, "취소"),
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

	BoryuStatCd(String code, String message) {
		this.code = code;
		this.message = message;
	}

	BoryuStatCd(String code, String message, String enMessage) {
		this.code = code;
		this.message = message;
	}

	private static final Map<String, BoryuStatCd> LOOKUP = new HashMap<>();

	static {
		for (BoryuStatCd type : EnumSet.allOf(BoryuStatCd.class)) {
			LOOKUP.put(type.code, type);
		}
	}

	@MappedTypes(BoryuStatCd.class)
	public static class TypeHandler extends CodeEnumTypeHandler<BoryuStatCd> {
		public TypeHandler() {
			super(BoryuStatCd.class);
		}
	}

	public static BoryuStatCd get(String code) {
		return LOOKUP.get(code);
	}

}

