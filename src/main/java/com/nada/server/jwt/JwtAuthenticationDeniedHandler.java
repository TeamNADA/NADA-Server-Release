package com.nada.server.jwt;


import com.nada.server.constants.ErrorCode;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// 유저 정보 없이 접근 시 에러 코드 반환
@Component
@Slf4j
public class JwtAuthenticationDeniedHandler implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
        AuthenticationException authException) throws IOException {
        // 유효한 자격증명을 제공하지 않고 접근하려 할때 401
        String exception = String.valueOf(request.getAttribute("exception"));

        if(exception==null){
            log.info("알 수 없는 인증 에러입니다.");
            setResponse(response, ErrorCode.UNKNOWN_ERROR);
        }else if(exception.equals("WRONG_TYPE_TOKEN")){
            setResponse(response, ErrorCode.WRONG_TYPE_TOKEN);
        }else if(exception.equals("EXPIRED_TOKEN")){
            setResponse(response, ErrorCode.EXPIRED_TOKEN);
        }else if(exception.equals("UNSUPPORTED_TOKEN")){
            setResponse(response, ErrorCode.UNSUPPORTED_TOKEN);
        }else{
            setResponse(response, ErrorCode.ACCESS_DENIED);
        }
        return;
    }

    private void setResponse(HttpServletResponse response, ErrorCode errorCode) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().println("{ \"msg\" : \"" + errorCode.getMsg()
            + "\", \"timestamp\" : \"" + LocalDateTime.now() + "\" \n }");
    }

}
