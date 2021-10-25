package com.nada.server.dto.res;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.nada.server.dto.BaseResponse;
import com.nada.server.dto.payload.CardDTO;
import com.nada.server.dto.payload.CardDateDTO;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class WrittenCardResponse extends BaseResponse {

    private WrittenCardData data;

    public WrittenCardResponse(String msg, Integer offset, List<CardDTO> cards, List<CardDateDTO> cardDates) {
        super(msg);
        this.data = new WrittenCardData(offset, cards, cardDates);
    }

    @Getter
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    static class WrittenCardData{
        Integer offset;
        List<CardDTO> cards;
        List<CardDateDTO> cardDates;
    }

}
