package com.nada.server.constants;

import static org.springframework.http.HttpStatus.*;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SuccessCode {

    /* 200 - 성공적인 요청 */
    LOGIN_SUCCESS(OK, "로그인 성공"),
    REGISTER_SUCCESS(OK, "회원가입 성공"),
    UNSUBSCRIBE_SUCCESS(OK, "회원 탈퇴 성공");

    private final HttpStatus httpStatus;
    private final String msg;
}
