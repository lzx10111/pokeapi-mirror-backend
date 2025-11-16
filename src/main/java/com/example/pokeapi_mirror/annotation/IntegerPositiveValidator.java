package com.example.pokeapi_mirror.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class IntegerPositiveValidator implements ConstraintValidator<IntegerPositive, String> {
    private Boolean allowPlusSign;
    private Boolean allowEmptyValue;

    @Override
    public void initialize(IntegerPositive constraintAnnotation) {
        allowPlusSign = constraintAnnotation.allowPlusSign();
        allowEmptyValue = constraintAnnotation.allowEmptyValue();
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s == null) {
            return false;
        }
        
        final int length = s.length();

        if (allowEmptyValue && length == 0) {
            return true;
        }

        if (length == 0) {
            return false;
        }
        int i = 0;
        if (s.charAt(0) == '0') {
            return false;
        }
        
        if (s.charAt(0) == '+' && allowPlusSign) {
            if (length == 1 || length > 10) {
                return false;
            }
            i = 1;
        }
        else {
            if (length > 9) {
                return false;
            }
        }

        for (; i < length; i++) {
            char c = s.charAt(i);
            if (c < '0' || c > '9') {
                return false;
            }
        }

        return true;
    }
}