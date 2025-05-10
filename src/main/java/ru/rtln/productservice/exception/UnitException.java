package ru.rtln.productservice.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public class UnitException extends BaseException {

    protected final Code code;

    protected UnitException(String msg, Throwable ex, Code code) {
        super(msg, ex);
        this.code = code;
    }

    @Getter
    @RequiredArgsConstructor
    public enum Code {
        INTERNAL_ERROR("error-message.internal-error"),
        UNIT_NOT_FOUND("error-message.unit-not-found"),
        UNIT_ALREADY_EXISTS("error-message.unit-already-exists"),
        ;

        private final String userMessageProperty;

        public UnitException getWith(String msg) {
            return new UnitException(msg, null, this);
        }

        public UnitException getWith(String msg, Throwable ex) {
            return new UnitException(msg, ex, this);
        }
    }
}
