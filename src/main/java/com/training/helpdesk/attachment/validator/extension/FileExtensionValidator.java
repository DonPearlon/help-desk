package com.training.helpdesk.attachment.validator.extension;

import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Class represents validator of file extension.
 *
 * @author Alexandr_Terehov
 */
public class FileExtensionValidator
        implements ConstraintValidator<FileExtension, MultipartFile> {
    private static final String EXTENSION_REGEX_PATTERN
            = "((pdf|doc|docx|png|jpeg|jpg)$)";

    @Override
    public void initialize(final FileExtension constraintAnnotation) {

    }

    @Override
    public boolean isValid(final MultipartFile file, 
            final ConstraintValidatorContext constraintValidatorContext) {
        if (file == null) {
            return true;
        }
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        Matcher matcher = Pattern.compile(EXTENSION_REGEX_PATTERN).matcher(extension);
        return matcher.matches();
    }
}