package ru.rtln.productservice.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public class ProductException extends BaseException {

    protected final Code code;

    protected ProductException(String msg, Throwable ex, Code code) {
        super(msg, ex);
        this.code = code;
    }

    @Getter
    @RequiredArgsConstructor
    public enum Code {
        INTERNAL_ERROR("error-message.internal-error"),
        PRODUCT_NOT_FOUND("error-message.product-not-found"),
        PRODUCT_ALREADY_EXISTS("error-message.product-already-exists"),
        WRONG_PRODUCT_ACTION("error-message.wrong-product-action")
        ;

        private final String userMessageProperty;

        public ProductException getWith(String msg) {
            return new ProductException(msg, null, this);
        }

        public ProductException getWith(String msg, Throwable ex) {
            return new ProductException(msg, ex, this);
        }
    }
}
