package com.training.helpdesk.attachment.validator.size;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Class represents validator of file size.
 *
 * @author Alexandr_Terehov
 */
public class FileSizeValidator 
        implements ConstraintValidator<FileSize, MultipartFile> {
    private static final int MAX_SIZE = 5242880;

    @Override
    public void initialize(final FileSize constraintAnnotation) {

    }

    @Override
    public boolean isValid(final MultipartFile file, 
            final ConstraintValidatorContext constraintValidatorContext) {
        if (file == null) {
            return true;
        }
        return (file.getSize() <= MAX_SIZE);
    }
}