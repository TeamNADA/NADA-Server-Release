package com.nada.server.dto.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserTokenDTO {
    private String userId;
    private String accessToken;
    private String refreshToken;

    public UserTokenDTO(String userId, String accessToken, String refreshToken) {
        this.userId = userId;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public UserTokenDTO(String userId) {
        this.userId = userId;
    }
}
