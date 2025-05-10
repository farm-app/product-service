package ru.rtln.productservice.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import ru.rtln.productservice.validator.FileTypeValidator;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD, PARAMETER})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = FileTypeValidator.class)
public @interface FileType {

    String message() default "Invalid file type";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    Class<? extends Enum<?>> enumClass();
}
