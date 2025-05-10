package ru.rtln.productservice.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.compress.utils.FileNameUtils;
import org.springframework.web.multipart.MultipartFile;
import ru.rtln.productservice.annotation.FileType;

import java.util.Arrays;
import java.util.List;

public class FileTypeValidator implements ConstraintValidator<FileType, MultipartFile> {

    private Class<? extends Enum<?>> enumClass;

    @Override
    public void initialize(FileType constraintAnnotation) {
        this.enumClass = constraintAnnotation.enumClass();
    }

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
        if (file == null || file.isEmpty()) {
            return false;
        }
        var fileExtension = FileNameUtils.getExtension(file.getOriginalFilename());
        var allowedExtensions = getEnumValues();
        return fileExtension != null && allowedExtensions.contains(fileExtension.toLowerCase());
    }

    private List<String> getEnumValues() {
        return Arrays.stream(enumClass.getEnumConstants())
                .map(Enum::name)
                .map(String::toLowerCase)
                .toList();
    }
}
