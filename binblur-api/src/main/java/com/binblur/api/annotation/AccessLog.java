package com.binblur.api.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <pre>
 * Description : 접근 로그 어노테이션
 * Date : 2023/03/16 12:35 PM
 * Company : smart90
 * Author : kdonghwa
 * </pre>
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface AccessLog {
    String value();
}
