package com.nada.server.dto.res;

import com.nada.server.dto.BaseResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class GetHarmonyResponse extends BaseResponse {

    private HarmonyData data;

    public GetHarmonyResponse(String msg, int harmony) {
        super(msg);
        this.data = new HarmonyData(harmony);
    }

    @Getter
    @AllArgsConstructor
    static class HarmonyData{
        int harmony;
    }
}
