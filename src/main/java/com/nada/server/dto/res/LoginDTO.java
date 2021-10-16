package com.nada.server.dto.res;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginDTO {
    private String userId;
    private String accessToken;
    private String refreshToken;

    public LoginDTO(String userId) {
        this.userId = userId;
    }
}
