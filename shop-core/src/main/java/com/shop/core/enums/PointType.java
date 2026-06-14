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
 * Description: 포인트 타입 (TB_CODE.CODE_UPPER = 10110)
 * </pre>
 */
public enum PointType implements CodeEnum {

    EARN    ("E", "구매 적립",   "Purchase Earn"),
    USE     ("U", "주문 사용",   "Order Use"),
    RESTORE ("R", "취소 환불",   "Cancel Restore"),
    REVIEW  ("V", "리뷰 적립",   "Review Earn"),
    EXPIRE  ("X", "만료",        "Expire"),
    ADMIN   ("A", "관리자 지급", "Admin Grant"),
    ;

    private String code;
    private String message;
    private String enMessage;

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        Locale locale = LocaleContextHolder.getLocale();
        if (locale == Locale.ENGLISH) {
            return enMessage != null ? enMessage : message;
        }
        return message;
    }

    PointType(String code, String message, String enMessage) {
        this.code = code;
        this.message = message;
        this.enMessage = enMessage;
    }

    private static final Map<String, PointType> LOOKUP = new HashMap<>();

    static {
        for (PointType type : EnumSet.allOf(PointType.class)) {
            LOOKUP.put(type.code, type);
        }
    }

    // CodeEnumTypeHandler : DB에 getCode() 값 저장 (E, U, R ...)
    @MappedTypes(PointType.class)
    public static class TypeHandler extends CodeEnumTypeHandler<PointType> {
        public TypeHandler() {
            super(PointType.class);
        }
    }

    public static PointType get(String code) {
        return LOOKUP.get(code);
    }
}
