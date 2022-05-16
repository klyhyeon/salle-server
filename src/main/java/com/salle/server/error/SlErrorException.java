package com.salle.server.error;

import com.salle.server.domain.enumeration.ErrorCode;
import org.springframework.http.HttpStatus;

public class SlErrorException extends RuntimeException {

    private ErrorCode errorCode;

    public SlErrorException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorCode.getErrorMessage();
    }

    public HttpStatus getErrorHttpStatus() {
        return errorCode.getErrorHttpStatus();
    }
}
