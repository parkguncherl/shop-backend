package com.smart.core.enums;

import com.smart.core.interfaces.CodeEnum;
import com.smart.core.interfaces.CodeEnumTypeHandler;
import org.apache.ibatis.type.MappedTypes;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 * Description : 접속 타입 속성값
 * Date : 2023/03/13 17:35 PM
 * Company : smart90
 * Author : kdonghwa
 * </pre>
 */
public enum GlobalConst implements CodeEnum {

    TOP_MENU			("TOP", "TOP"),
    UP_OTP_CODE			("S0009", "otp 정보들어있는 코드"),
    OTP_CODE			("9000", "code 중에 otp 메시지 값"),
    OTP_LIMIT			("180", "otp 제한시간"),
    OTP_FAIL_CNT		("5", "otp 실패 횟수"),
    OTP_TITLE_OTP		("[블루플러그] 로그인 인증번호입니다.", "[BLUEPLUG] Login authentication number."),
    MAIL_TITLE_REG   	("[블루플러그] 관리자 계정이 등록되었습니다.", "[BLUEPLUG] Your administrator ID is registered."),
    MAIL_TITLE_DEL   	("[블루플러그] 관리자 계정이 삭제되었습니다.", "[BLUEPLUG] Your administrator ID is deleted."),
    MAIL_TITLE_EX_READY ("[블루플러그] 관리자 계정 휴면예정 입니다.", "[BLUEPLUG] Your administrator ID will be dormant."),
    MAIL_TITLE_EX    	("[블루플러그] 관리자 계정이 휴면처리 되었습니다.", "[BLUEPLUG] Your administrator ID has been dormant."),
	MAIL_SENDER			("KEFICO 충전관리시스템 관리자", "KEFICO BLUEPLUG SYSTEM ADMINISTRATOR"),
    OTP_MAIL_SENDER		("KEFICO 충전관리시스템 관리자", "KEFICO BLUEPLUG SYSTEM ADMINISTRATOR"),
    SMS_SENDER			("0314362499", "SMS 발신번호"), // 조영선 책임에게 전달받음  2023/03/17 금요일 오후 5:30:40
    SMS_TITLE			("케피코 충전관리시스템", "KEFICO BLUEPLUG SYSTEM"),

    REP_EMAIL			("blueplug-service@hyundai-kefico.com", "대표 이메일"),
    HOMEPAGE			("https://www.hyundai-kefico.com", "회사홈페이지"),

    COUNTRY_COOKIE	   ("kefEcmsLoginLanCode", "로그인시 선택언어 쿠키"),

    SHEET_100          ("IN_100ms", "100 미리세컨드"),
    SHEET_1000         ("IN_1s", "1초단위"),
    SHEET_10000        ("IN_10s", "10초단위"),
    SHEET_CALC_100     ("calc_100ms", "100 미리세컨드"),
    SHEET_CALC_1000    ("calc_1s", "1초단위"),
    SHEET_CALC_10000   ("calc_10s", "10초단위"),
    SHEET_OUTPUT        ("OUTPUT", "서버이벤트 결과"),

    KO_REGION_CODE_UPPER ("S0007", "지역코드(한국)"),
    US_REGION_CODE_UPPER ("S0014", "지역코드(미국)"),
    MAX_EXCEL_ROW ("1048576", "엑셀지원MAX ROW"),

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

    GlobalConst(String code, String message) {
        this.code = code;
        this.message = message;
    }

    private static final Map<String, GlobalConst> LOOKUP = new HashMap<>();

    static {
        for (GlobalConst type : EnumSet.allOf(GlobalConst.class)) {
            LOOKUP.put(type.code, type);
        }
    }

    @MappedTypes(GlobalConst.class)
    public static class TypeHandler extends CodeEnumTypeHandler<GlobalConst> {

        public TypeHandler() {
            super(GlobalConst.class);
        }
    }

    public static GlobalConst get(String code) {
        return LOOKUP.get(code);
    }
}
