package com.example.demo.exceptions;

import com.example.demo.constants.ErrorCode;
import lombok.Getter;

@Getter
public class ResourceNotFoundException extends RuntimeException {

    protected int errorCode = ErrorCode.RESOURCE_NOT_FOUND;

    public ResourceNotFoundException () {
    }

    public ResourceNotFoundException (int errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public ResourceNotFoundException (int errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public ResourceNotFoundException (int errorCode, Throwable cause) {
        super(cause);
        this.errorCode = errorCode;
    }

    public ResourceNotFoundException (int errorCode, String message, Throwable cause, boolean enableSuppression,
                                      boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.errorCode = errorCode;
    }
}
