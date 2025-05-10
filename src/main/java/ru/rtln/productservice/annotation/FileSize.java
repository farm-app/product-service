package ru.rtln.productservice.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import ru.rtln.productservice.validator.FileSizeValidator;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD, PARAMETER})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = FileSizeValidator.class)
public @interface FileSize {

    String message() default "File size is too big";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    long max() default Long.MAX_VALUE;
}
