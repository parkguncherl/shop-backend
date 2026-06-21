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
 * Description: 결제 상태 (TB_CODE.CODE_UPPER = 10070)
 * </pre>
 */
public enum PaymentStatus implements CodeEnum {

    READY     ("R", "결제대기", "READY"),
    PAID      ("P", "결제완료", "PAID"),
    CANCELLED ("C", "결제취소", "CANCELLED"),
    FAILED    ("F", "결제실패", "FAILED"),
    ;

    private final String code;
    private final String message;
    private final String enMessage;

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

    PaymentStatus(String code, String message, String enMessage) {
        this.code = code;
        this.message = message;
        this.enMessage = enMessage;
    }

    private static final Map<String, PaymentStatus> LOOKUP = new HashMap<>();

    static {
        for (PaymentStatus s : EnumSet.allOf(PaymentStatus.class)) {
            LOOKUP.put(s.code, s);
        }
    }

    @MappedTypes(PaymentStatus.class)
    public static class TypeHandler extends CodeEnumTypeHandler<PaymentStatus> {
        public TypeHandler() {
            super(PaymentStatus.class);
        }
    }

    public static PaymentStatus get(String code) {
        return LOOKUP.get(code);
    }
}
