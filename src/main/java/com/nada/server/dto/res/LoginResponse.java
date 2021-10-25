package com.nada.server.dto.res;

import com.nada.server.dto.BaseResponse;
import com.nada.server.dto.payload.UserTokenDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class LoginResponse extends BaseResponse {
    private LoginData data;

    public LoginResponse(String msg, UserTokenDTO user) {
        super(msg);
        this.data = new LoginData(user);
    }

    @Getter
    @AllArgsConstructor
    static class LoginData{
        private UserTokenDTO user;
    }
}
