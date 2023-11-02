package com.petproject.common.logging;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogResponseBody {

    /**
     * If true and response is Collection then size only will be logged.
     */
    boolean sizeOnly() default false;

    /**
     * SpEL expression. SpEL parser consumes response body as source.
     */
    String expression() default "";
}
