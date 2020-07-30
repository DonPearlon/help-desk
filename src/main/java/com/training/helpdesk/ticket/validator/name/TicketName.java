package com.training.helpdesk.ticket.validator.name;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = TicketNameValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface TicketName {
    
    /**
     * @return default string message.
     */
    String message() default "Invalid ticket name (only lowercase allowed)";
    
    /**
     * @return default value for groups method.
     */
    Class<?>[] groups() default {};
    
    /**
     * @return default value for payload method.
     */
    Class<? extends Payload>[] payload() default {};
}