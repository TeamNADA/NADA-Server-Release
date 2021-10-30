package com.nada.server.dto.res;

import com.nada.server.dto.BaseResponse;
import com.nada.server.dto.payload.CardFrontDTO;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class CardGroupListResponse extends BaseResponse {

    private CardGroupListData data;

    public CardGroupListResponse(String msg, Integer offset, List<CardFrontDTO> cards) {
        super(msg);
        this.data = new CardGroupListData(offset, cards);
    }

    @Getter
    @AllArgsConstructor
    static class CardGroupListData{
        Integer offset;
        List<CardFrontDTO>  cards;
    }
}
