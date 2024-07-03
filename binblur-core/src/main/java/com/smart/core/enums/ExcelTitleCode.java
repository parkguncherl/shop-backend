package com.binblur.core.enums;

import com.binblur.core.interfaces.CodeEnum;
import com.binblur.core.interfaces.CodeEnumTypeHandler;
import org.apache.ibatis.type.MappedTypes;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 * Description : 엑셀컬럼 헤더값모음
 * Date : 2023/03/28 17:35 PM
 * Company : smart90
 * Author : luckeey
 * </pre>
 */
public enum ExcelTitleCode implements CodeEnum {

    MENU_EXCEL_COLUMNS("상위코드|코드|순서|이름|영문명|URI(ICO)", "메뉴 권한 관리 엑셀 일괄업로드"),
    CODE_EXCEL_COLUMNS("상위코드|코드|이름|영문명|설명|기타정보1|기타정보2|순서(Num)", "코드 관리 엑셀 일괄업로드"),
    UNIT_MODEL_EXCEL_COLUMNS("이름|종류(Code)|케피코품번|업체품번|제조사(Code)|사용구분(Code)|타입(Code)|목표수명(Num)|금액(Num)", "부품 엑셀 일괄업로드"),
    UNIT_MODEL_EXCEL_GRID_COLUMNS("NO|이름|종류|케피코품번|업체품번|제조사|사용구분|타입|목표수명|금액|등록자|등록일시", "부품 모델 엑셀 일괄 다운로드"),
    UNIT_MODEL_EXCEL_GRID_COLUMNS2("NO|부품사용구분|부품사용구분코드|부품타입|케피코품번|업체품번|부품종류구분|부품명|제조사|목표수명|부품금액|등록자|등록일시", "부품 모델 엑셀 일괄 다운로드 2"),
    PARAM_MODEL_DET_EXCEL_COLUMNS("INTERVAL(Code)|제어기변수명|서버변수명", "변수 모델 엑셀 일괄업로드"),
    ERRCODE_EXCEL_COLUMNS("코드값(Num)|구분(Code)|고장코드명", "고장코드모델 엑셀 일괄업로드"),
    STATION_EXCEL_COLUMNS("NO|이름|S/N|충전사업자|충전포스트|파워뱅크|국가|주소|등록일시", "충전소 목록 엑셀 일괄다운로드"),
    ALERT_MODEL_EXCEL_COLUMNS("NO|알람명|결과값|설명", "알람모델 목록 엑셀 일괄다운로드"),
    POST_EXCEL_COLUMNS("NO|이름|S/N|충전사업자|충전소명|뱅크연결수|HMI Mac Address|모델명|알람그룹|등록일시", "충전포스트 목록 엑셀 일괄다운로드"),
    CHECK_EXCEL_COLUMNS("NO|구분|이름|점검자|점검일자|충전소명|충전소 S/N|포스트명|포스트 S/N", "점검리포트 목록 엑셀 다운로드"),
    BANK_EXCEL_COLUMNS("NO|이름|S/N|충전소명|포스트연결|모델명|등록일시", "파워뱅크 엑셀 다운로드"),
    POST_BANK_EXCEL_COLUMNS("NO|포스트명|포스트 S/N|뱅크연결수|파워뱅크명|파워뱅크 S/N|충전소명|연결일시", "충전포스트 파워뱅크 연결 엑셀 다운로드"),
    CLAIM_EXCEL_COLUMNS("충전소명|충전소S/N|할당위치 구분|할당위치 S/N|고장|인입경로|출장구분|부품교체비용|출장비|총비용|수리기간|발생일자|조치 구분|조치 상태|조치 일자|개선 부서|개선 담당자|등록자|등록일시", "클레임 조치 엑셀 다운로드"),
    UNIT_REG_EXCEL_COLUMNS("위치(구분)코드(P or B)|케피코품번|S/N", "재고 부품 등록 엑셀 일괄업로드"),
    POST_UNIT_MODEL_EXCEL_COLUMNS("이름|종류|케피코품번|업체품번|사용구분|타입|목표수명|S/N", "포스트 부품 목록 엑셀 다운로드"),
    BANK_UNIT_MODEL_EXCEL_COLUMNS("이름|종류|케피코품번|업체품번|사용구분|타입|목표수명|S/N", "뱅크 부품 목록 엑셀 다운로드"),
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

    ExcelTitleCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    private static final Map<String, ExcelTitleCode> LOOKUP = new HashMap<>();

    static {
        for (ExcelTitleCode type : EnumSet.allOf(ExcelTitleCode.class)) {
            LOOKUP.put(type.code, type);
        }
    }

    @MappedTypes(ExcelTitleCode.class)
    public static class TypeHandler extends CodeEnumTypeHandler<ExcelTitleCode> {

        public TypeHandler() {
            super(ExcelTitleCode.class);
        }
    }

    public static ExcelTitleCode get(String code) {
        return LOOKUP.get(code);
    }

}

