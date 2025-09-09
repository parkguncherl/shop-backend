package com.shop.core.enums;

import com.shop.core.interfaces.CodeEnum;
import com.shop.core.interfaces.CodeEnumTypeHandler;
import org.apache.ibatis.type.MappedTypes;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 * Description: 엑셀컬럼 헤더값모음
 * Date: 2023/03/28 17:35 PM
 * Company: smart90
 * Author : luckeey
 * </pre>
 */
public enum ExcelTitleCode implements CodeEnum {

    MENU_EXCEL_COLUMNS("상위코드|코드|순서|이름|영문명|URI(ICO)", "메뉴 권한 관리 엑셀 일괄업로드"),
    CODE_EXCEL_COLUMNS("상위코드|코드|이름|영문명|설명|기타정보1|기타정보2|순서(Num)", "코드 관리 엑셀 일괄업로드"),
    ERRCODE_EXCEL_COLUMNS("코드값(Num)|구분(Code)|고장코드명", "고장코드모델 엑셀 일괄업로드"),
    STATION_EXCEL_COLUMNS("NO|이름|S/N|충전사업자|충전포스트|파워뱅크|국가|주소|등록일시", "충전소 목록 엑셀 일괄다운로드"),
    LOGIS_EXCEL_COLUMNS("물류창고KEY|물류창고명|물류창고위치|물류창고설명|회사 전화 번호|담당자 명|담당자 전화 번호|상세 정보|위치 수", "창고 관리 엑셀 일괄업로드"),
    LIVE_INFO_EXCEL_COLUMNS("상품NO|매장명|구분|상품명|컬러|사이즈|재고량|입금단가|주문량|주문금액", "라방 관리엑셀");

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

