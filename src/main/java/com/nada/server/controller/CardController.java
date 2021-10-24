package com.nada.server.controller;

import com.nada.server.constants.SuccessCode;
import com.nada.server.domain.Card;
import com.nada.server.dto.BaseResponse;
import com.nada.server.dto.req.CreateCardDTO;
import com.nada.server.service.CardService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Api(tags = "카드 API")
public class CardController {

    private final CardService cardService;

    @ApiOperation(value = "카드 생성")
    @PostMapping("/card")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "카드 생성 성공",
            content = @Content(schema = @Schema(implementation = BaseResponse.class)))
    })
    public ResponseEntity<BaseResponse> createCard(@RequestBody @Valid CreateCardDTO card){

        Card newCard = Card.createCard("background", card.getBirthDate(), card.getTitle(),
            card.getName(), card.getMbti(), card.getInstagram(), card.getLinkName(), card.getLink(), card.getDescription(),
            card.isMincho(), card.isSoju(), card.isBoomuk(), card.isSauced(), card.getOneQuestion(),
            card.getOneAnswer(), card.getTwoQuestion(), card.getTwoAnswer());

        cardService.create(newCard, "nada");
        BaseResponse response = new BaseResponse(SuccessCode.CREATE_CARD.getMsg());
        return new ResponseEntity(response, SuccessCode.CREATE_CARD.getHttpStatus());
    }

}
