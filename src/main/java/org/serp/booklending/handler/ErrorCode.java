package org.serp.booklending.handler;

import org.springframework.http.HttpStatus;

import lombok.Getter;

public enum ErrorCode {
    ACCOUNT_DISABLED(303, HttpStatus.FORBIDDEN, "Account is Disabled"),
    ACCOUNT_LOCKED(302, HttpStatus.FORBIDDEN, "User account is locked"),
    BAD_CREDENTIAL(304, HttpStatus.FORBIDDEN, "Login is incorrect"),
    INCORRECT_CURRENT_PASSWORD(300, HttpStatus.BAD_REQUEST, "Current password is not correct"),
    NEW_PASSWORD_DOES_NOT_MATCH(301, HttpStatus.BAD_REQUEST, "The new password doesn't match"),
    No_Code(0, HttpStatus.NOT_IMPLEMENTED, "No code");
    @Getter
    private final int code;
    @Getter
    private final String description;
    @Getter
    private final HttpStatus httpStatus;

    ErrorCode(int code, HttpStatus httpStatus, String description) {
        this.code = code;
        this.description = description;
        this.httpStatus = httpStatus;
    }
}
