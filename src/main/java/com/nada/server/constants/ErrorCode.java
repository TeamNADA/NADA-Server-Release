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
    NOT_MY_GROUP(BAD_REQUEST, "내가 추가한 그룹이 아닙니다."),
    /* 401 - 인증 실패 */
    // token 관련
    INVALID_AUTH_TOKEN(UNAUTHORIZED, "권한 정보가 없는(잘못된) 토큰입니다"),
    INVALID_REFRESH_TOKEN(UNAUTHORIZED, "권한 정보가 없는(잘못된) 리프레시 토큰입니다"),
    EXPIRED_REFRESH_TOKEN(UNAUTHORIZED, "리프레시 토큰이 만료되었습니다."),

    // 로그인 관련
    UNAUTHORIZED_USER(UNAUTHORIZED, "등록된 유저 정보가 없습니다."),
    UNAUTHORIZED_TOKEN(UNAUTHORIZED, "토큰을 함께 보내주세요."),

    /* 403 - forbidden, 접근권한 존재하지 않음 */
    FORBIDDEN_ACCESS(FORBIDDEN, "해당 요청에 접근할 권한이 아닙니다."),

    /* 404 - 자원 존재하지 않음 */
    INVALID_CARD_ID(NOT_FOUND, "등록되지 않은 카드입니다."),
    INVALID_GROUP_ID(NOT_FOUND, "등록되지 않은 그룹입니다"),
    INVALID_CARD_GROUP(NOT_FOUND, "그룹에 추가되지 않았던 카드입니다."),

    /* 405 - 잘못된 요청 */
    METHOD_NOT_SUPPORTED(METHOD_NOT_ALLOWED, "잘못된 요청입니다."),

    /* 406 - 접근 불허용 */
    CANNOT_DELETE_DEFAULT_GROUP(NOT_ACCEPTABLE, "미분류 그룹은 삭제할 수 없습니다"),
    CANNOT_MODIFY_DEFAULT_GROUP(NOT_ACCEPTABLE, "미분류 그룹은 수정할 수 없습니다"),

    /* 409 - 중복 자원 존재 */
    DUPLICATE_GROUP_NAME(CONFLICT, "동일한 이름의 그룹이 존재합니다"),
    DUPLICATE_USER_ID(CONFLICT, "이미 존재하는 유저입니다."),
    DUPLICATE_CARD_ID(CONFLICT, "이미 추가된 카드입니다."),

    /* 500 - 서버 에러 */
    SERVER_INTERNAL_ERROR(INTERNAL_SERVER_ERROR, "서버 내부 오류입니다.");

    private final HttpStatus httpStatus;
    private final String msg;
}
