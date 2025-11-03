package com.example.pokeapi_mirror.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD,
    ElementType.FIELD,
    ElementType.ANNOTATION_TYPE,
    ElementType.CONSTRUCTOR,
    ElementType.PARAMETER,
    ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = IntegerPositiveValidator.class)
public @interface IntegerPositive {
    boolean allowPlusSign() default false;

    boolean allowEmptyValue() default false;

    String message() default "String is not a positive integer.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

