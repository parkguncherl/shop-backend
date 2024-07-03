package com.binblur.core.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <pre>
 * Description : 인증 제외 Annotation
 * Date : 2023/01/26 12:35 PM
 * Company : smart
 * Author : luckeey
 * </pre>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface NotAuthRequired {
}
