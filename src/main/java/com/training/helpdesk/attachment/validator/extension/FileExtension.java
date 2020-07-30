package com.training.helpdesk.attachment.validator.extension;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = FileExtensionValidator.class)
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface FileExtension {
    String DEFAULT_MESSAGE
            = "The selected file type is not allowed. Please select a file"
                + " of one of the following types: pdf, png, doc, docx, jpg, jpeg.";
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