package com.nada.server.dto.res;

import com.nada.server.dto.BaseResponse;
import com.nada.server.dto.payload.UserTokenDTO;
import lombok.Getter;

@Getter
public class LoginResponse extends BaseResponse {
    private UserTokenDTO data;

    public LoginResponse(String msg, UserTokenDTO data) {
        super(true, msg);
        this.data = data;
    }
}
