package com.shop.core.coreUtils;

import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;


/**
 * <p>문자열을 다룰때 필요한 기능을 모아 놓은 유틸성 클래스. </p>
 *
 * @author luckeey
 * @version 1.0
 */
@Slf4j
public class CoreUtil {
    public static int nullToZero(Integer value) {
        return value == null ? 0 : value;
    }

    public static BigDecimal nullToZero(BigDecimal value) {
        return value == null ? BigDecimal.ZERO : value;
    }

}