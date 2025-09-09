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
public enum PartnerCodeGroup implements CodeEnum {

	ACCOUNT     	("P0001"		, "계정과목"),
	CATEGORY     	("P0002"		, "거래카테고리"),
	BORYU_CATEGORY  ("P0003"		, "보류카테고리"),
	SELLER_TYPE		("P0004"		, "판매처유형"),
	ORDER_ETC		("P0005"		, "주문비고목록"),
	GUBUN_P0010		("P0010"		, "주문비고목록"),
	GUBUN_P0010_SELLER1		("seller1"		, "판매처비고1"),
	GUBUN_P0010_SELLER2		("seller2"		, "판매처비고2"),
	GUBUN_P0010_FACTORY1		("factory1"		, "생산처비고1"),
	GUBUN_P0010_FACTORY2		("factory2"		, "생산처비고2"),
	GUBUN_P0010_SKU1		("sku1"		, "SKU비고1"),
	GUBUN_P0010_SKU2		("sku2"		, "SKU비고2"),
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

	PartnerCodeGroup(String code, String message) {
		this.code = code;
		this.message = message;
	}

	private static final Map<String, PartnerCodeGroup> LOOKUP = new HashMap<>();

	static {
		for (PartnerCodeGroup type : EnumSet.allOf(PartnerCodeGroup.class)) {
			LOOKUP.put(type.code, type);
		}
	}

	@MappedTypes(PartnerCodeGroup.class)
	public static class TypeHandler extends CodeEnumTypeHandler<PartnerCodeGroup> {
		public TypeHandler() {
			super(PartnerCodeGroup.class);
		}
	}

	public static PartnerCodeGroup get(String code) {
		return LOOKUP.get(code);
	}

}

