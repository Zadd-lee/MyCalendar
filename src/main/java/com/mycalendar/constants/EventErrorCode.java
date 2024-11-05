package com.mycalendar.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum EventErrorCode implements ErrorCode {

    PASSWORD_INCORRECT(HttpStatus.UNAUTHORIZED, "비밀번호가 정확하지 않습니다"),
    INVALID_ID(HttpStatus.BAD_REQUEST, "올바르지 않은 ID 값입니다"),
    ;


    private final HttpStatus httpStatus;
    private final String message;
    }
