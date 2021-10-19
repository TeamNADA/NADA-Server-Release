package com.nada.server.dto.res;

import com.nada.server.dto.BaseResponse;
import com.nada.server.dto.payload.UserTokenDTO;

public class LoginDTO extends BaseResponse {
    private UserTokenDTO data;

    public LoginDTO(Boolean success, String msg, UserTokenDTO data) {
        super(success, msg);
        this.data = data;
    }
}
