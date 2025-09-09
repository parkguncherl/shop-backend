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
 * RZ		랙
 * HZ		행거
 * BZ		벌크
 * ASZ		자동설비
 * TSZ		임시보관
 * RTCZ		수선TC
 * PTCZ		제작TC
 * </pre>
 */
public enum ZoneCd implements CodeEnum {

	RZ	 ("RZ"		, "랙"),
	HZ	 ("HZ"		, "행거"),
	BZ	 ("BZ"		, "벌크"),
	TZ	 ("TZ"		, "임시"),
	AZ	 ("AZ"		, "자동설비"),
	TC	 ("TC"		, "TC"),
		;

	//TZ	임시
	//AZ	자동설비
	//TC	TC
	//HZ	행거
	//BZ	벌크
	//RZ	랙

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

	ZoneCd(String code, String message) {
		this.code = code;
		this.message = message;
	}

	ZoneCd(String code, String message, String enMessage) {
		this.code = code;
		this.message = message;
	}

	private static final Map<String, ZoneCd> LOOKUP = new HashMap<>();

	static {
		for (ZoneCd type : EnumSet.allOf(ZoneCd.class)) {
			LOOKUP.put(type.code, type);
		}
	}

	@MappedTypes(ZoneCd.class)
	public static class TypeHandler extends CodeEnumTypeHandler<ZoneCd> {
		public TypeHandler() {
			super(ZoneCd.class);
		}
	}

	public static ZoneCd get(String code) {
		return LOOKUP.get(code);
	}

}

