package com.nada.server.jwt;


import com.nada.server.constants.ErrorCode;
import java.time.LocalDateTime;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// 유저 정보 없이 접근 시 에러 코드 반환
@Component
public class JwtAuthenticationDeniedHandler implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
        AuthenticationException authException) throws IOException {
        // 유효한 자격증명을 제공하지 않고 접근하려 할때 401
        ErrorCode errorCode = ErrorCode.UNAUTHORIZED_TOKEN;
        setResponse(response, errorCode);
        return;
    }

    private void setResponse(HttpServletResponse response, ErrorCode errorCode) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().println("{ \"msg\" : \"" + errorCode.getMsg()
            + "\", \"timestamp\" : \"" + LocalDateTime.now() + "\" \n }");
    }

}
