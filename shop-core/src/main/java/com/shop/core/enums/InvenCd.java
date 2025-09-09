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
 *   재고 관련 타입
 */
public enum InvenCd implements CodeEnum {
	// 재고상태코드 관련  (상위코드: 10030)
	INVEN_CD_WAIT  	         ("W"		, "적치대기"),
	INVEN_CD_ACCUMULATION  	 ("A"		, "적치"),
	INVEN_CD_CREATE_NOREASON ("C"		, "적치asn없이"),
	INVEN_CD_GOMOVE          ("G"		, "재고이동"),
	INVEN_CD_SAMPLE          ("S"		, "샘플출고"), /* 출고 */
	INVEN_CD_SAMPLE_SAIL     ("P"		, "샘플판매"),
    INVEN_CD_OUT             ("O"		, "출고"), /* 출고 */
    INVEN_CD_MAEJANG_OUT     ("F"		, "매장재고출고"), /* 출고 */
    INVEN_CD_MEJANG          ("M"		, "매장출고"), /* 출고 */
	INVEN_CD_RETURN          ("R"		, "샘플반납"),

	// ZONE 코드관련 (TB_ZONE)
	TC_ZONE_CD         ("TC"		, "TC zone"),  // 수선TC ZONE
	TC_REPARE         ("수선_TC"		, "Repaired TC zone"),  // 수선TC ZONE
	TC_ORDER         ("제작_TC"		, "제작 zone"),  // 제작TC ZONE
	TZ_TRANS         ("데이터이관"		, "이관 zone"),  // 제작TC ZONE
	TZ_TEMP         ("임시작업대"		, "제작 zone"),  // 제작TC ZONE
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

	InvenCd(String code, String message) {
		this.code = code;
		this.message = message;
	}

	InvenCd(String code, String message, String enMessage) {
		this.code = code;
		this.message = message;
	}

	private static final Map<String, InvenCd> LOOKUP = new HashMap<>();

	static {
		for (InvenCd type : EnumSet.allOf(InvenCd.class)) {
			LOOKUP.put(type.code, type);
		}
	}

	@MappedTypes(InvenCd.class)
	public static class TypeHandler extends CodeEnumTypeHandler<InvenCd> {
		public TypeHandler() {
			super(InvenCd.class);
		}
	}

	public static InvenCd get(String code) {
		return LOOKUP.get(code);
	}

}

