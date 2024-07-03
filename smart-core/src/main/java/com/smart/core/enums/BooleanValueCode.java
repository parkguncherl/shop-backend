package com.smart.core.enums;

import com.smart.core.interfaces.CodeEnum;
import com.smart.core.interfaces.CodeEnumTypeHandler;
import org.apache.ibatis.type.MappedTypes;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 * Description : Boolean 코드 컬럼
 * Date : 2023/01/26 12:35 PM
 * Company : smart90
 * Author : harry
 * </pre>
 */
public enum BooleanValueCode implements CodeEnum {
	N	("N", "NO"),
	Y	("Y", "YES"),
	;

	private final String code;
	private final String message;

	BooleanValueCode(String code, String message) {
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

	private static final Map<String, BooleanValueCode> LOOKUP = new HashMap<>();

	static {
		for (BooleanValueCode type : EnumSet.allOf(BooleanValueCode.class)) {
			LOOKUP.put(type.getCode(), type);
		}
	}

	/**
	 * MybatisConfigure.java typeHandlers 에 추가 필요함
	 */
	@MappedTypes(BooleanValueCode.class)
	public static class TypeHandler extends CodeEnumTypeHandler<BooleanValueCode> {
		public TypeHandler() {
			super(BooleanValueCode.class);
		}
	}

	/**
	 * get ResultCode using code
	 * @param code code
	 * @return ResultCode object
	 */
	public static BooleanValueCode get(String code) {
		return LOOKUP.get(code);
	}
}

