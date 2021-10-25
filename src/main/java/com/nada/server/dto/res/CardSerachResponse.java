package com.nada.server.dto.res;

import com.nada.server.dto.BaseResponse;
import com.nada.server.dto.CardFrontDTO;
import lombok.Getter;

@Getter
public class CardSerachResponse extends BaseResponse{
    private CardFrontDTO data;

    public CardSerachResponse(String msg, CardFrontDTO data){
        super(msg);
        this.data = data;
    }

}
