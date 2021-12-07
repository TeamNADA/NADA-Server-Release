package com.nada.server.dto.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserTokenDTO {
    private String userId;
    private TokenDTO token;

    public UserTokenDTO(String userId, TokenDTO tokenDTO) {
        this.userId = userId;
        this.token = tokenDTO;
    }

    public UserTokenDTO(String userId) {
        this.userId = userId;
    }
}
