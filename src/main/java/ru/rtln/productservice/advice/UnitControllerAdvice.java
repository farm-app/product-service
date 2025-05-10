package ru.rtln.productservice.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.rtln.productservice.dto.exception.ErrorMessage;
import ru.rtln.productservice.exception.ProductException;
import ru.rtln.productservice.exception.UnitException;
import ru.rtln.productservice.util.MessageHelper;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static ru.rtln.productservice.exception.UnitException.Code.UNIT_ALREADY_EXISTS;

@RestControllerAdvice
public class UnitControllerAdvice extends BaseControllerAdvice {

    public UnitControllerAdvice(MessageHelper messageHelper) {
        super(messageHelper);
    }

    @ExceptionHandler(UnitException.class)
    public ResponseEntity<ErrorMessage> handleException(UnitException ex) {
        var exceptionCode = ex.getCode();
        var httpStatus = switch (exceptionCode) {
            case INTERNAL_ERROR -> HttpStatus.INTERNAL_SERVER_ERROR;
            case UNIT_NOT_FOUND,
                 UNIT_ALREADY_EXISTS -> BAD_REQUEST;
        };
        var userMessageProperty = exceptionCode.getUserMessageProperty();
        return formErrorMsg(ex, httpStatus, exceptionCode.toString(), userMessageProperty);
    }
}
