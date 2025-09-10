package com.shop.core.enums;

import com.shop.core.interfaces.CodeEnum;
import com.shop.core.interfaces.CodeEnumTypeHandler;
import org.apache.ibatis.type.MappedTypes;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 * Description: 접속 타입 속성값
 * Date: 2023/03/13 17:35 PM
 * Company: smart90
 * Author : luckeey
 * </pre>
 */
public enum GlobalConst implements CodeEnum {
    YES			        ("Y", "YES"),
    NO			        ("N", "NO"),
    TOP_MENU			("TOP", "TOP"),
    UP_OTP_CODE			("S0009", "otp 정보들어있는 코드"),
    OTP_CODE			("9000", "code 중에 otp 메시지 값"),
    OTP_LIMIT			("180", "otp 제한시간"),
    OTP_FAIL_CNT		("5", "otp 실패 횟수"),
    OTP_TITLE_OTP		("로그인 인증번호입니다.", "Login authentication number."),
    MAIL_TITLE_REG   	("관리자 계정이 등록되었습니다.", "Your administrator ID is registered."),
    MAIL_TITLE_DEL   	("관리자 계정이 삭제되었습니다.", "Your administrator ID is deleted."),
    MAIL_TITLE_EX_READY ("관리자 계정 휴면예정 입니다.", "Your administrator ID will be dormant."),
    MAIL_TITLE_EX    	("관리자 계정이 휴면처리 되었습니다.", "Your administrator ID has been dormant."),
	MAIL_SENDER			("관리자", "BINBLUR SYSTEM ADMINISTRATOR"),
    OTP_MAIL_SENDER		("관리자", "BINBLUR SYSTEM ADMINISTRATOR"),
    SMS_SENDER			("0314362499", "SMS 발신번호"),
    SMS_TITLE			("물류 관리시스템", "OMS MANAGEMENT SYSTEM"),

    REP_EMAIL			("admin@binblur.com", "대표 이메일"),
    HOMEPAGE			("http://shop.com/", "회사홈페이지"),
    AWS_BUCKET_NAME     ("binblur-file", "s3 버킷네임"),

    // 전표양식 기본 설정
    LOGO_PRINT_YN       ("Y","이미지 인쇄 여부"),
    LOGO_LOC_CD         ("L","이미지 위치"),
    TITLE_YN            ("Y","타이틀 여부"),
    TITLE_MNG           ("TITLE","타이틀 관리"),
    TITLE_NOR           ("TITLE","타이틀 일반"),
    TOP_YN              ("Y","상단 여부"),
    TOP_MNG             ("TOP_MESSAGE","상단 관리"),
    TOP_NOR             ("TOP_MESSAGE","상단 일반"),
    BOTTOM_YN           ("Y","하단 여부"),
    BOTTOM_MNG          ("BOTTOM_MESSAGE","하단 관리"),
    BOTTOM_NOR          ("BOTTOM_MESSAGE","하단 일반"),

    // 코드 대분류정보
    ORDER_CD            ("10120","주문분류(주문대분류)"),
    CUST_STAT_CLASS     ("10310","주문분류 스타일 클래스"),
    ZONE_CD             ("10090", "ZONE코드"),

    MAGIC_PASSWORD      ("1230","만능비밀번호"),
    COMPANY_SHORT_NM    ("BLUR","축약이름"),


    NORMAL_SELLER		("N", "소매처를 선택해주세요"),

    MISONG		        ("MISONG", "미송처리"),
    ORDER_INSERT		("ORDERINSERT", "주문입력"),
    ORDER_UPDATE		("ORDERUPDATE", "주문수정"),
    ORDER_APPEND		("ORDERAPPEND", "주문추가"),

    // 사용자권한코드
    STORESTAFF     		("340", "매장직원"),
    DESIGNER     		("350", "디자이너"),
    PARTNER     		("399", "화주"),

    SHIPPING_TEAM   	("560", "출고팀"),
    STOCKING_TEAM    	("565", "입고팀"),
    LOGIS_ADMIN	    	("570", "물류관리자"),
    WMS_ADMIN	    	("980", "WMS관리자"),
    MANAGE_TEAM	    	("990", "관리팀"),
    SYS_ADMIN	    	("999", "시스템관리자"),

    MIG     		    ("MIG", "마이그 데이터"),

    // 배치결과코드
    BATCH_RSLT_CD_NONE     ("1","미처리"),
    BATCH_RSLT_CD_SUCC     ("2","처리성공"),
    BATCH_RSLT_CD_FAIL     ("9", "처리실패"),

    // 공장입출금구분 코드 (10500)
    INOUT_CD_PAY            ("A","결제"),
    INOUT_CD_IN             ("B","일반입고"),
    INOUT_CD_OUT            ("D","일반반출"),
    INOUT_CD_SUSUN_IN       ("E","수선입고"),
    INOUT_CD_SUSUN_OUT      ("F","수선반출"),

    MAX_ROWCOUNT      ("999999","수선반출"),
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
