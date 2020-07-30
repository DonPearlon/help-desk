package com.training.helpdesk.ticket.validator.field;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class StringFieldValidator 
        implements ConstraintValidator<StringField, String> {
    
    private static final String FIELD_REGEX_PATTERN
            = "(?=.*[a-zA-Z])^[a-zA-Z0-9~.\\\"(),:;<>@\\[\\]\\s!#$%&'*+-/=?^_`{|}]{1,500}$";
    
    @Override
    public void initialize(final StringField constraintAnnotation) {

    }

    @Override
    public boolean isValid(final String field, 
            final ConstraintValidatorContext constraintValidatorContext) {
        if ((field == null) || (field.isEmpty())) {
            return true;
        }
        Matcher matcher = Pattern.compile(FIELD_REGEX_PATTERN).matcher(field);
        return matcher.matches();
    }
}