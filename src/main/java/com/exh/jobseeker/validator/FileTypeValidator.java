package com.exh.jobseeker.validator;

import com.exh.jobseeker.annotation.ValidFileType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;

public class FileTypeValidator implements ConstraintValidator<ValidFileType, MultipartFile> {

    private String[] types;

    @Override
    public void initialize(ValidFileType constraintAnnotation) {
        this.types = constraintAnnotation.types();
    }

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
        if (file == null || file.isEmpty()) {
            return true;
        }

        String contentType = file.getContentType();
        return Arrays.asList(types).contains(contentType);
    }
}
