package com.shop.api.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <pre>
 * Description: guest fo 유저 정보 어노테이션
 * Date: 2026/05/24 12:35 PM
 * Company: smart
 * Author: luckeey
 * </pre>
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface GuestUser {
}
