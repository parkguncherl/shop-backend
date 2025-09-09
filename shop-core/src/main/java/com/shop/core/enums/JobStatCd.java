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
public enum JobStatCd implements CodeEnum {

	COMPLETE ("5"		, "완료"),
	BORYU	 ("9"		, "출고보류"),
	REQ		 ("1"		, "요청"), // 요청은 모아 놨다 한번에 요청한다.
	NOW		 ("3"		, "처리중"), // 반납음 반납요청시 바로 asn 에 넎는다.
    PARTICIAL("4"		, "부분출고"), // 매장분과 섞여서 출고 열어서 출고
    WRONG("8"		, "오출고"), // 매장분과 섞여서 출고 열어서 출고
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

	JobStatCd(String code, String message) {
		this.code = code;
		this.message = message;
	}

	JobStatCd(String code, String message, String enMessage) {
		this.code = code;
		this.message = message;
	}

	private static final Map<String, JobStatCd> LOOKUP = new HashMap<>();

	static {
		for (JobStatCd type : EnumSet.allOf(JobStatCd.class)) {
			LOOKUP.put(type.code, type);
		}
	}

	@MappedTypes(JobStatCd.class)
	public static class TypeHandler extends CodeEnumTypeHandler<JobStatCd> {
		public TypeHandler() {
			super(JobStatCd.class);
		}
	}

	public static JobStatCd get(String code) {
		return LOOKUP.get(code);
	}

}

