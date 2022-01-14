package com.langsin.oa.annotation;

import java.lang.annotation.*;

/**
 * 参数校验
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DoValid {
}
