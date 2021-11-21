package com.nada.server.exception;

import com.nada.server.constants.ErrorCode;
import com.nada.server.dto.BaseResponse;
import lombok.Builder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Builder
@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(value = {CustomException.class})
    protected ResponseEntity<BaseResponse> handleCustomException(CustomException e) {
        return BaseResponse.toErrorResponse(e.getErrorCode());
    }

    // @RequestBody valid 에러
    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    protected ResponseEntity<BaseResponse> handleMethodArgNotValidException(
        MethodArgumentNotValidException e) {
        return BaseResponse.toCustomErrorResponse(
            e.getBindingResult().getAllErrors().get(0).getDefaultMessage(), HttpStatus.BAD_REQUEST);
    }

    // @ModelAttribute valid 에러
    @ExceptionHandler(value = {BindException.class})
    protected ResponseEntity<BaseResponse> handleMethodArgNotValidException(BindException e) {
        return BaseResponse.toCustomErrorResponse(
            e.getBindingResult().getAllErrors().get(0).getDefaultMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {HttpRequestMethodNotSupportedException.class})
    protected ResponseEntity<BaseResponse> handleMethodNotSupportedException(
        HttpRequestMethodNotSupportedException e) {
        ErrorCode code = ErrorCode.METHOD_NOT_SUPPORTED;
        return BaseResponse.toCustomErrorResponse(code.getMsg(), code.getHttpStatus());
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    protected ResponseEntity<BaseResponse> handleRequestParamNotValidException(
        MissingServletRequestParameterException e) {
        return BaseResponse.toCustomErrorResponse(e.getParameterName() + " 값을 필수로 넣어주세요!",
            HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {Exception.class})
    protected ResponseEntity<BaseResponse> handleException(Exception e) {
        ErrorCode code = ErrorCode.SERVER_INTERNAL_ERROR;
        //return BaseResponse.toCustomErrorResponse(code.getMsg(), code.getHttpStatus());
        return BaseResponse.toCustomErrorResponse(e.getMessage(), code.getHttpStatus());

    }
}