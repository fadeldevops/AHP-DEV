package com.example.learn.api.master.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValidationException extends RuntimeException {
    /**
     *
     */
    private static final long serialVersionUID = 3545937571146934479L;

    private String messageCode;
    private String[] varValues;

    public ValidationException(String messageCode, Throwable cause, String... varValues) {
        super(messageCode, cause);
        this.messageCode = messageCode;
        this.varValues = varValues;
    }

    public ValidationException(String messageCode, String... varValues) {
        super(messageCode);
        this.messageCode = messageCode;
        this.varValues = varValues;
    }

    public ValidationException(String messageCode, Throwable cause) {
        super(cause);
        this.messageCode = messageCode;
    }

}
