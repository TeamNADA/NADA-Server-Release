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
    UNSUBSCRIBE_SUCCESS(OK, "회원 탈퇴 성공"),
    DELETE_CARD_SUCCESS(OK, "카드 삭제 성공"),
    SEARCH_CARD_SUCCESS(OK, "카드 검색 성공"),
    LOAD_WRITTEN_CARD_SUCCESS(OK, "작성한 카드 리스트 조회 성공"),
    MODIFY_PRIORITY_SUCCESS(OK, "카드 우선순위 변경 성공"),
    LOAD_CARD_SUCCESS(OK, "명함 세부 조회 성공"),

    /* 201 - 자원 생성 */
    CREATE_CARD_SUCCESS(CREATED, "카드 생성 성공");

    private final HttpStatus httpStatus;
    private final String msg;
}
