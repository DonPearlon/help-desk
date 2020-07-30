package com.training.helpdesk.user.validator.email;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailValidator implements ConstraintValidator<Email, String> {
    
    private static final String EMAIL_REGEX_PATTERN
            = "^(?=.{1,100}$)[_a-z0-9-]+(\\.[_a-z0-9-]+)*"
                + "@[a-z0-9-]+(\\.[a-z0-9-]+)*(\\.[a-z]{1,100})$";
    
    @Override
    public void initialize(final Email constraintAnnotation) {

    }

    @Override
    public boolean isValid(final String email, 
            final ConstraintValidatorContext constraintValidatorContext) {
        Matcher matcher = Pattern
                .compile(EMAIL_REGEX_PATTERN, Pattern.CASE_INSENSITIVE).matcher(email);
        return matcher.matches();
    }
}