package com.training.helpdesk.attachment.validator.size;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = FileSizeValidator.class)
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface FileSize {
    String DEFAULT_MESSAGE
            = "The size of attached file should not be greater than 5Mb. "
                + "Please select another file.";
    /**
     * @return default string message.
     */
    String message() default DEFAULT_MESSAGE;
    
    /**
     * @return default value for groups method.
     */
    Class<?>[] groups() default {};
    
    /**
     * @return default value for payload method.
     */
    Class<? extends Payload>[] payload() default {};
}