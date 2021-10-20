package com.nada.server.dto;

import com.fasterxml.jackson.databind.ser.Serializers.Base;
import com.nada.server.exception.ErrorCode;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
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
}
