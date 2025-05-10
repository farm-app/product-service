package ru.rtln.productservice.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.rtln.productservice.dto.exception.ErrorMessage;
import ru.rtln.productservice.exception.ProductPictureException;
import ru.rtln.productservice.util.MessageHelper;

@RestControllerAdvice
public class ProductPictureControllerAdvice extends BaseControllerAdvice {

    public ProductPictureControllerAdvice(MessageHelper messageHelper) {
        super(messageHelper);
    }

    @ExceptionHandler(ProductPictureException.class)
    public ResponseEntity<ErrorMessage> handleBookException(ProductPictureException ex) {
        var status = switch (ex.getCode()) {
            case INTERNAL_ERROR -> HttpStatus.INTERNAL_SERVER_ERROR;
            case FILE_NOT_FOUND -> HttpStatus.NOT_FOUND;
            case BAD_EXTENSION,
                 COVER_ALREADY_EXISTS,
                 FILES_LIMIT_EXCEEDED,
                 EMPTY_FILENAME -> HttpStatus.BAD_REQUEST;
        };
        var code = ex.getCode().toString();
        var userMessageProperty = ex.getCode().getUserMessageProperty();
        return formErrorMsg(ex, status, code, userMessageProperty);
    }
}
