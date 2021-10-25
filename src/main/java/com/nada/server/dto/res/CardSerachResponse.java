package com.nada.server.dto.res;

import com.nada.server.dto.BaseResponse;
import com.nada.server.dto.payload.CardFrontDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class CardSerachResponse extends BaseResponse {
    private CardSearchData data;

    public CardSerachResponse(String msg, CardFrontDTO card){
        super(msg);
        this.data = new CardSearchData(card);
    }

    @Getter
    @AllArgsConstructor
    static class CardSearchData{
        private CardFrontDTO card;
    }

}
