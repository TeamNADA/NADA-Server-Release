package com.nada.server.constants;

import static org.springframework.http.HttpStatus.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    /* 400 - 잘못된 요청 */
    CANNOT_ADD_MY_CARD(BAD_REQUEST, "내가 작성한 카드는 추가할 수 없습니다."),

    /* 401 - 인증 실패 */
    // token 관련
    INVALID_AUTH_TOKEN(UNAUTHORIZED, "권한 정보가 없는(잘못된) 토큰입니다"),
    INVALID_REFRESH_TOKEN(UNAUTHORIZED, "권한 정보가 없는(잘못된) 리프레시 토큰입니다"),
    EXPIRED_REFRESH_TOKEN(UNAUTHORIZED, "리프레시 토큰이 만료되었습니다."),
    // 로그인 관련
    UNAUTHORIZED_USER(UNAUTHORIZED, "등록된 유저 정보가 없습니다."),

    /* 404 - 자원 존재하지 않음 */
    INVALID_CARD_ID(NOT_FOUND, "등록되지 않은 카드입니다."),

    /* 405 - 잘못된 요청 */
    METHOD_NOT_SUPPORTED(METHOD_NOT_ALLOWED, "잘못된 요청입니다."),

    /* 409 - 중복 자원 존재 */
    DUPLICATE_GROUP_NAME(CONFLICT, "동일한 이름의 그룹이 존재합니다"),
    DUPLICATE_USER_ID(CONFLICT, "이미 존재하는 유저입니다."),

    /* 500 - 서버 에러 */
    SERVER_INTERNAL_ERROR(INTERNAL_SERVER_ERROR, "서버 내부 오류입니다.");

    private final HttpStatus httpStatus;
    private final String msg;
}
