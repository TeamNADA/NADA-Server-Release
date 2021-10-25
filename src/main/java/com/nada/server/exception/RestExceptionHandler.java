package com.nada.server.exception;

import com.nada.server.constants.ErrorCode;
import com.nada.server.dto.BaseResponse;
import javax.annotation.Nullable;
import lombok.Builder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Builder
@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity handleMethodArgumentNotValid(MethodArgumentNotValidException e, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return BaseResponse.toCustomErrorResponse(e.getBindingResult().getAllErrors().get(0).getDefaultMessage(), HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity handleHttpRequestMethodNotSupported(
        HttpRequestMethodNotSupportedException e, HttpHeaders headers, HttpStatus status, WebRequest request){
        ErrorCode code = ErrorCode.METHOD_NOT_SUPPORTED;
        return BaseResponse.toCustomErrorResponse(code.getMsg(), code.getHttpStatus());
    }

    @Override
    protected ResponseEntity handleExceptionInternal(Exception e, @Nullable Object body, HttpHeaders headers, HttpStatus status, WebRequest request){
        ErrorCode code = ErrorCode.SERVER_INTERNAL_ERROR;
        return BaseResponse.toCustomErrorResponse(code.getMsg(), code.getHttpStatus());
    }

    @ExceptionHandler(value = { CustomException.class })
    protected ResponseEntity<BaseResponse> handleCustomException(CustomException e) {
        return BaseResponse.toErrorResponse(e.getErrorCode());
    }
}
