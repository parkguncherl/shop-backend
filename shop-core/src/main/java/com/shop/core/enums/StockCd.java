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
 *   입하 관련 타입
 */
public enum StockCd implements CodeEnum {
	// 구분코드 관련  (상위코드: 10240)
	STOCK_CD_FACTORY     	("1"		, "공장입하"),
	STOCK_CD_PARTNER        ("2"		, "도매입하"),
	STOCK_CD_OTHER          ("3"		, "기타입하"),

	// 상태코드 관련 (10230)
	STOCK_STAT_CD_READY     	    ("1"		, "대기"),   // 입하완료시
	STOCK_STAT_CD_LOC_DOING     	("5"		, "적치중"),	// WCS에서 적치중상태를 응답시
	STOCK_STAT_CD_LOC_COMPLET     	("9"		, "적치완료"), // 적치시작시

	// 사유코드 (10250)
	STOCK_RSN_CD_CHANGE     	("C"		, "교환"),
	STOCK_RSN_CD_SAMPLE     	("S"		, "샘플반납"),
	STOCK_RSN_CD_ETC            ("E"		, "기타"),
	STOCK_RSN_CD_NORMAL     	("N"		, "정상"),
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

	StockCd(String code, String message) {
		this.code = code;
		this.message = message;
	}

	StockCd(String code, String message, String enMessage) {
		this.code = code;
		this.message = message;
	}

	private static final Map<String, StockCd> LOOKUP = new HashMap<>();

	static {
		for (StockCd type : EnumSet.allOf(StockCd.class)) {
			LOOKUP.put(type.code, type);
		}
	}

	@MappedTypes(StockCd.class)
	public static class TypeHandler extends CodeEnumTypeHandler<StockCd> {
		public TypeHandler() {
			super(StockCd.class);
		}
	}

	public static StockCd get(String code) {
		return LOOKUP.get(code);
	}

}

