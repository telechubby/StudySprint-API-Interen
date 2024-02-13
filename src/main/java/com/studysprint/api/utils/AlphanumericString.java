package com.studysprint.api.utils;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AlphanumericString {
    int length() default 6;
}
