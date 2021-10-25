package com.nada.server.dto.res;

import com.nada.server.dto.BaseResponse;
import com.nada.server.dto.payload.CardDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class CardDetailResponse extends BaseResponse {

    private CardDetailData data;

    public CardDetailResponse(String msg, CardDTO card){
        super(msg);
        this.data = new CardDetailData(card);
    }
    @Getter
    @AllArgsConstructor
    static class CardDetailData{
        CardDTO card;
    }
}
