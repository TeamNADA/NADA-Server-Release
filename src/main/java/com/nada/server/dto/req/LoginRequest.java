package com.nada.server.dto.req;

import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LoginRequest {
    @NotNull(message = "유저 아이디값을 넣어주세요!")
    private String userId;
}
