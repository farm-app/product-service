package ru.rtln.productservice.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.rtln.productservice.dto.exception.ErrorMessage;
import ru.rtln.productservice.exception.ProductException;
import ru.rtln.productservice.util.MessageHelper;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice
public class ProductControllerAdvice extends BaseControllerAdvice {

    public ProductControllerAdvice(MessageHelper messageHelper) {
        super(messageHelper);
    }

    @ExceptionHandler(ProductException.class)
    public ResponseEntity<ErrorMessage> handleException(ProductException ex) {
        var exceptionCode = ex.getCode();
        var httpStatus = switch (exceptionCode) {
            case INTERNAL_ERROR -> HttpStatus.INTERNAL_SERVER_ERROR;
            case PRODUCT_NOT_FOUND,
                 PRODUCT_ALREADY_EXISTS,
                 WRONG_PRODUCT_ACTION -> BAD_REQUEST;
        };
        var userMessageProperty = exceptionCode.getUserMessageProperty();
        return formErrorMsg(ex, httpStatus, exceptionCode.toString(), userMessageProperty);
    }
}
