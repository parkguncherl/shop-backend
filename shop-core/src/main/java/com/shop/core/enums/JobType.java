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
 * A	주문작업
 * E	오다
 * D	샘플작업
 * C	매장요청
 * B	미송작업
 * </pre>
 */
public enum JobType implements CodeEnum {

	JUMUN ("A"		, "주문작업"),
//	ORDER	 ("E"		, "오다"),
	SAMPLE		 ("D"		, "샘플작업"),
	MAJANG		 ("C"		, "매장요청"),
	MISONG		 ("B"		, "미송작업"),
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

	JobType(String code, String message) {
		this.code = code;
		this.message = message;
	}

	JobType(String code, String message, String enMessage) {
		this.code = code;
		this.message = message;
	}

	private static final Map<String, JobType> LOOKUP = new HashMap<>();

	static {
		for (JobType type : EnumSet.allOf(JobType.class)) {
			LOOKUP.put(type.code, type);
		}
	}

	@MappedTypes(JobType.class)
	public static class TypeHandler extends CodeEnumTypeHandler<JobType> {
		public TypeHandler() {
			super(JobType.class);
		}
	}

	public static JobType get(String code) {
		return LOOKUP.get(code);
	}

}

