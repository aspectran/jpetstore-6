package com.aspectran.jpetstore.common.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmptyOrNotBlankValidator implements ConstraintValidator<EmptyOrNotBlank, String> {

    public void initialize(EmptyOrNotBlank parameters) {
        // Nothing to do here
    }

    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null || value.length() == 0) {
            return true;
        }
        return !value.matches("^\\s*$");
    }

}
