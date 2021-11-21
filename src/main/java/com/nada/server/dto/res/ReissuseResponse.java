package com.nada.server.dto.res;

import com.nada.server.dto.BaseResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class ReissuseResponse extends BaseResponse {
    private ReissueData data;

    public ReissuseResponse(String msg, String accessToken, String refreshToken) {
        super(msg);
        data = new ReissueData(accessToken, refreshToken);
    }

    @Getter
    @AllArgsConstructor
    static class ReissueData {
        private String accessToken;
        private String refreshToken;
    }
}
