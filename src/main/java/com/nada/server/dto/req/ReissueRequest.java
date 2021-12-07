package com.nada.server.dto.req;

import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReissueRequest {
    @NotNull(message = "access token을 필수로 넣어주세요!")
    private String accessToken;
    @NotNull(message = "refresh token을 필수로 넣어주세요!")
    private String refreshToken;
}
