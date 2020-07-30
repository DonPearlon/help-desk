package com.training.helpdesk.ticket.validator.name;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class TicketNameValidator implements ConstraintValidator<TicketName, String> {
    
    private static final String TICKET_NAME_REGEX_PATTERN
            = "(?=.*[a-z])^[a-z0-9~.\\\"(),:;<>@\\[\\]\\s!#$%&'*+-/=?^_`{|}]{1,100}$";
    
    @Override
    public void initialize(final TicketName constraintAnnotation) {

    }

    @Override
    public boolean isValid(final String ticketName, 
            final ConstraintValidatorContext constraintValidatorContext) {
        Matcher matcher = Pattern.compile(TICKET_NAME_REGEX_PATTERN).matcher(ticketName);
        return matcher.matches();
    }
}