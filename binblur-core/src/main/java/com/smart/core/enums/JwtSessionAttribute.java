package com.binblur.core.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 * Description : JWT Session 속성값
 * Date : 2023/01/26 12:35 PM
 * Company : smart
 * Author : luckeey
 * </pre>
 */
public enum JwtSessionAttribute {

	ACCESS_TOKEN     	("ACCESS_TOKEN"	, "ACCESS_TOKEN"),
	USER_ID				("USER_ID"		, "회원_식별자"),
	USER_LOGIN_ID		("USER_LOGIN_ID"	, "회원_로그인_아이디"),
	USER_NAME			("USER_NAME"		, "회원_명"),
	USER				("USER"			, "회원_정보"),
	;

	private String code;
	private String description;

	JwtSessionAttribute(String code, String description) {
		this.code = code;
		this.description = description;
	}

	private static final Map<String, JwtSessionAttribute> LOOKUP = new HashMap<>();

	static {
		for (JwtSessionAttribute type : EnumSet.allOf(JwtSessionAttribute.class)) {
			LOOKUP.put(type.code, type);
		}
	}

	public static JwtSessionAttribute get(int code) {
		return LOOKUP.get(code);
	}
}

