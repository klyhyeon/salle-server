package com.salle.server.domain.enumeration;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

    //NOT FOUND
    USER_NOT_FOUND(HttpStatus.UNAUTHORIZED, "존재하지 않는 유저입니다."),

    //AUTH
    AUTH_WRONG_REQUEST(HttpStatus.UNAUTHORIZED, "인증 정보가 올바르지 않습니다."),
    WRONG_PASSWORD(HttpStatus.UNAUTHORIZED, "비밀번호가 올바르지 않습니다."),

    //FILE
    FILE_UPLOAD_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "파일 업로드에 오류가 발생했습니다."),

    //PRODUCT
    PRODUCT_NOT_FOUND(HttpStatus.UNAUTHORIZED, "존재하지 않는 상품입니다."),

    //PRODUCT COMMENT
    PRODUCT_COMMENT_NOT_FOUND(HttpStatus.UNAUTHORIZED, "존재하지 않는 댓글입니다.");

    private HttpStatus errorHttpStatus;
    private String errorMessage;

    ErrorCode(HttpStatus errorHttpStatus, String errorMessage) {
        this.errorHttpStatus = errorHttpStatus;
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public HttpStatus getErrorHttpStatus() {
        return errorHttpStatus;
    }
}
