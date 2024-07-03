package com.binblur.api.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <pre>
 * Description : 담당 충전소 ID 어노테이션
 * Date : 2023/02/14 12:35 PM
 * Company : smart90
 * Author : kdonghwa
 * </pre>
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface UserStation {
}
