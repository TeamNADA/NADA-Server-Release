package com.nada.server.dto;


import com.nada.server.constants.ErrorCode;
import java.time.LocalDateTime;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class BaseResponse {
    private String msg;
    private LocalDateTime timestamp = LocalDateTime.now();

    public BaseResponse(String msg) {
        this.msg = msg;
    }

    public static ResponseEntity<BaseResponse> toErrorResponse(ErrorCode errorCode) {
        return ResponseEntity
            .status(errorCode.getHttpStatus())
            .body(new BaseResponse(errorCode.getMsg()));
    }

    public static ResponseEntity<BaseResponse> toUnValidRequestErrorResponse(String msg) {
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(new BaseResponse(msg));
    }
}