package ru.rtln.productservice.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * An application-specific class for exceptions relating to file handling. Contains an internal {@code Code} enumeration
 * used to specify the exception details and retrieve a custom message about it.
 */
@Getter
public class ProductPictureException extends BaseException {

    protected final Code code;

    protected ProductPictureException(String msg, Throwable ex, Code code) {
        super(msg, ex);
        this.code = code;
    }

    @Getter
    @RequiredArgsConstructor
    public enum Code {

        INTERNAL_ERROR("error-message.internal-error"),
        BAD_EXTENSION("error-message.file.bad-extension"),
        FILE_NOT_FOUND("error-message.file.not-found"),
        COVER_ALREADY_EXISTS("error-message.file.cover-already-exists"),
        FILES_LIMIT_EXCEEDED("error-message.file.files-limit-exceeded"),
        EMPTY_FILENAME("error-message.file.empty-filename")
        ;

        /**
         * The key to retrieve the user  message from the properties file.
         */
        private final String userMessageProperty;

        /**
         * Returns an exception instance with the developer message.
         *
         * @param msg developer message for {@link ru.relex.libraryservice.dto.exception.ErrorMessage#devMessage ErrorMessage.devMessage} field
         * @return {@code FileException} instance
         */
        public ProductPictureException getWith(String msg) {
            return new ProductPictureException(msg, null, this);
        }

        /**
         * Returns an exception instance with the developer message and the exception cause.
         *
         * @param ex  cause exception
         * @param msg developer message for {@link ru.relex.libraryservice.dto.exception.ErrorMessage#devMessage ErrorMessage.devMessage} field
         * @return {@code FileException} instance
         */
        public ProductPictureException getWith(String msg, Throwable ex) {
            return new ProductPictureException(msg, ex, this);
        }
    }
}
