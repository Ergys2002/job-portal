package com.exh.jobseeker.validator;

import com.exh.jobseeker.annotation.FileSize;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

public class FileSizeValidator implements ConstraintValidator<FileSize, MultipartFile> {

    private long maxSize;

    @Override
    public void initialize(FileSize constraintAnnotation) {
        this.maxSize = parseSize(constraintAnnotation.max());
    }

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
        if (file == null || file.isEmpty()) {
            return true;
        }

        return file.getSize() <= maxSize;
    }

    private long parseSize(String size) {
        size = size.toUpperCase();
        long l = Long.parseLong(size.substring(0, size.length() - 2));
        if (size.endsWith("KB")) {
            return l * 1024;
        } else if (size.endsWith("MB")) {
            return l * 1024 * 1024;
        } else if (size.endsWith("GB")) {
            return l * 1024 * 1024 * 1024;
        }
        return Long.parseLong(size);
    }
}
