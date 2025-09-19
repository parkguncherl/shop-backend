package com.shop.core.enums;

import com.shop.core.interfaces.CodeEnum;
import com.shop.core.interfaces.CodeEnumTypeHandler;
import org.apache.ibatis.type.MappedTypes;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 * Description: 정렬 방법 코드
 * Date: 2023/02/04 01:35 PM
 * Company: smart
 * Author: luckeey
 * </pre>
 */
public enum SortTypeCode implements CodeEnum {
	DESC	("desc", "내림차순"),
	ASC	("asc", "오름차순"),
	;

	private String code;
	private String message;

	private SortTypeCode(String code, String message) {
		this.code = code;
		this.message = message;
	}

	/**
	 *get Code
	 * @return code
	 */
	@Override
	public String getCode() {
		return code;
	}

	/**
	 * get Message
	 * @return
	 */
	public String getMessage() {
		return message;
	}

	private static final Map<String, SortTypeCode> LOOKUP = new HashMap<>();

	static {
		for (SortTypeCode type : EnumSet.allOf(SortTypeCode.class)) {
			LOOKUP.put(type.getCode(), type);
		}
	}

	/**
	 * MybatisConfigure.java typeHandlers 에 추가 필요함
	 */
	@MappedTypes(SortTypeCode.class)
	public static class TypeHandler extends CodeEnumTypeHandler<SortTypeCode> {
		public TypeHandler() {
			super(SortTypeCode.class);
		}
	}

	/**
	 * get ResultCode using code
	 * @param code code
	 * @return ResultCode object
	 */
	public static SortTypeCode get(int code) {
		return LOOKUP.get(code);
	}
}

