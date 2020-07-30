package com.training.helpdesk.user.validator.password;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<Password, String> {

    private static final String PASSWORD_REGEX_PATTERN 
            = "(?=.*[a-z])(?=.*[A-Z])(?=..*[0-9])(?=.*"
                    + "[~.\"(),:;<>@\\[\\]!#$%&'*+-/=?^_`{|}])(?=\\S+$).{6,20}$";
    
    @Override
    public void initialize(final Password constraintAnnotation) {

    }

    @Override
    public boolean isValid(final String password,
            final ConstraintValidatorContext constraintValidatorContext) {
        Matcher matcher = Pattern.compile(PASSWORD_REGEX_PATTERN).matcher(password);
        return matcher.matches();
    }
}