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
 * Description: 재고상태
 * Date: 2025/02/05 17:35 PM
 * Company: smart90
 * Author : luckeey
 * </pre>
 */
public enum InvenStatCd implements CodeEnum {

	SAMPLE    ("S" , "샘플출고"),  // OUT
	ACCUMULATION    ("A" , "적치"),  // ACCUMULATION (가용재고로 적치완료)
	WAIT    ("W" , "적치대기"),  // WAIT
	MAEJANG    ("M" , "매장출고"),  // 현재위치가 매장
	SAMPLE_SAIL    ("P" , "샘플판매"),  // OUT
	OUT    ("O" , "출고"),  // OUT
	OUTFROMRETAIL	    ("F" , "매장재고출고"),  // OUT
	GO_MOVE    ("G" , "재고이동"),  // 재고 이동중 go move
	TAKE_DISPOSE    ("N" , "일반반출"),  // 폐기반출
	TAKE_REPAIR    ("T" , "수선반출"),  // TAKE OUT FOR REPAIR
	//BULYONG    ("B" , "불용"),  // 불용판정난 재고 -- 현재는 씌이지 않는다.
	SAMPLE_RETURN    ("R" , "샘플반납")  // 현재위치가 매장
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

	InvenStatCd(String code, String message) {
		this.code = code;
		this.message = message;
	}

	InvenStatCd(String code, String message, String enMessage) {
		this.code = code;
		this.message = message;
	}

	private static final Map<String, InvenStatCd> LOOKUP = new HashMap<>();

	static {
		for (InvenStatCd type : EnumSet.allOf(InvenStatCd.class)) {
			LOOKUP.put(type.code, type);
		}
	}

	@MappedTypes(InvenStatCd.class)
	public static class TypeHandler extends CodeEnumTypeHandler<InvenStatCd> {
		public TypeHandler() {
			super(InvenStatCd.class);
		}
	}

	public static InvenStatCd get(String code) {
		return LOOKUP.get(code);
	}

}

