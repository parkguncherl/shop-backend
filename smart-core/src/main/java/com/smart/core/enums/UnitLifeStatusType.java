package com.smart.core.enums;

import com.smart.core.interfaces.CodeEnum;
import com.smart.core.interfaces.CodeEnumTypeHandler;
import org.apache.ibatis.type.MappedTypes;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * <pre>
 * Description : 부품 수명상태 타입값
 * Date : 2023/03/13 17:35 PM
 * Company : smart90
 * Author : kdonghwa
 * </pre>
 */
public enum UnitLifeStatusType implements CodeEnum {

	GOOD     	("G", 0, 69, 	"양호", "good"),
	ALERT		("A", 70, 89, 	"경계", "alert"),
	IMMINENT	("I", 90, 99,	"임박", "imminent"),
	EXCEED		("E", 100, 99999,	"초과", "exceed"),
	;

	private String code;
	private Integer min;
	private Integer max;
	private String message;
	private String enMessage;

	@Override
	public String getCode() {
		return code;
	}

	public Integer getMin() {
		return min;
	}

	public Integer getMax() {
		return max;
	}

	@Override
	public String getMessage() {
		Locale locale = LocaleContextHolder.getLocale();
		if (locale == Locale.ENGLISH) {
			return enMessage != null ? enMessage : message;
		}
		return message;
	}

	UnitLifeStatusType(String code, Integer min, Integer max, String message) {
		this.code = code;
		this.min = min;
		this.max = max;
		this.message = message;
	}

	UnitLifeStatusType(String code, Integer min, Integer max, String message, String enMessage) {
		this.code = code;
		this.min = min;
		this.max = max;
		this.message = message;
		this.enMessage = enMessage;
	}

	private static final Map<String, UnitLifeStatusType> LOOKUP = new HashMap<>();

	static {
		for (UnitLifeStatusType type : EnumSet.allOf(UnitLifeStatusType.class)) {
			LOOKUP.put(type.code, type);
		}
	}

	@MappedTypes(UnitLifeStatusType.class)
	public static class TypeHandler extends CodeEnumTypeHandler<UnitLifeStatusType> {
		public TypeHandler() {
			super(UnitLifeStatusType.class);
		}
	}

	public static UnitLifeStatusType get(String code) {
		return LOOKUP.get(code);
	}

}

