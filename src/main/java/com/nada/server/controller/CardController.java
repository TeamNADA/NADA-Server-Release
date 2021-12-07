package com.nada.server.controller;

import com.nada.server.commons.S3Utils;
import com.nada.server.commons.SecurityUtil;
import com.nada.server.constants.SuccessCode;
import com.nada.server.domain.Card;
import com.nada.server.dto.BaseResponse;
import com.nada.server.dto.payload.CardDTO;
import com.nada.server.dto.payload.CardDateDTO;
import com.nada.server.dto.payload.CardFrontDTO;
import com.nada.server.dto.payload.CreateCardDTO;
import com.nada.server.dto.req.ChangePriorityDTO;
import com.nada.server.dto.req.CreateCardRequest;
import com.nada.server.dto.res.CardDetailResponse;
import com.nada.server.dto.res.CardSerachResponse;
import com.nada.server.dto.res.WrittenCardResponse;
import com.nada.server.service.CardService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "카드 API")
public class CardController {

    private final CardService cardService;
    private final S3Utils s3Utils;

    @ApiOperation(value = "카드 생성")
    @PostMapping("/card")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "카드 생성 성공",
            content = @Content(schema = @Schema(implementation = BaseResponse.class))),
        @ApiResponse(responseCode = "400", description = "요청 값 부족",
            content = @Content(schema = @Schema(implementation = BaseResponse.class)))
    })
    public ResponseEntity<BaseResponse> createCard(@ModelAttribute @Valid CreateCardRequest card) throws IOException {

        CreateCardDTO cardData = card.getCard();

        Integer isDefault = card.getCard().getDefaultImage();
        String imageURL;

        if(isDefault == 0){
            imageURL = s3Utils.upload(card.getImage());
        }else{
            // 지정 이미지
            imageURL = "Default IMAGE~";
        }

        Card newCard = Card.createCard(imageURL, cardData.getBirthDate(), cardData.getTitle(),
            cardData.getName(), cardData.getMbti(), cardData.getInstagram(), cardData.getLinkName(), cardData.getLink(), cardData.getDescription(),
            cardData.getIsMincho(), cardData.getIsSoju(), cardData.getIsBoomuk(),cardData.getIsSauced(), cardData.getOneQuestion(),
            cardData.getOneAnswer(), cardData.getTwoQuestion(), cardData.getTwoAnswer());

        cardService.create(newCard, cardData.getUserId());

        SuccessCode code = SuccessCode.CREATE_CARD_SUCCESS;
        BaseResponse response = new BaseResponse(code.getMsg());
        return new ResponseEntity(response, code.getHttpStatus());
    }

    @ApiOperation(value = "카드 삭제")
    @DeleteMapping("/card/{card-id}")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "카드 삭제 성공",
            content = @Content(schema = @Schema(implementation = BaseResponse.class))),
        @ApiResponse(responseCode = "404", description = "존재하지 않는 카드 ID",
            content = @Content(schema = @Schema(implementation = BaseResponse.class)))
    })
    public ResponseEntity<BaseResponse> deleteCard(@PathVariable("card-id") String cardId){
        Card findCard = cardService.findOne(cardId);

        // todo -> default 이미지일 때 삭제 X
        s3Utils.deleteBackground(findCard.getBackground());
        cardService.delete(findCard);

        SuccessCode code = SuccessCode.DELETE_CARD_SUCCESS;
        BaseResponse response = new BaseResponse(code.getMsg());
        return new ResponseEntity(response, code.getHttpStatus());
    }

    @ApiOperation(value = "카드 검색")
    @GetMapping("/cards/{card-id}")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "카드 검색 성공",
            content = @Content(schema = @Schema(implementation = CardSerachResponse.class))),
        @ApiResponse(responseCode = "404", description = "존재하지 않는 카드 ID",
            content = @Content(schema = @Schema(implementation = BaseResponse.class)))
    })
    public ResponseEntity<CardSerachResponse> searchCard(@PathVariable("card-id") String cardId) {
        Card card = cardService.findOne(cardId);

        CardFrontDTO cardFrontDTO = new CardFrontDTO(card.getId(), card.getBackground(),
            card.getTitle(), card.getName(), card.getBirthDate(),
            card.getAge(), card.getMbti(), card.getInstagram(), card.getLinkName(), card.getLink(),
            card.getDescription());

        SuccessCode code = SuccessCode.SEARCH_CARD_SUCCESS;
        CardSerachResponse response = new CardSerachResponse(code.getMsg(), cardFrontDTO);
        return new ResponseEntity(response, code.getHttpStatus());
    }

    @ApiOperation(value = "작성한 카드 리스트 조회")
    @GetMapping("/cards")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "작성한 카드 리스트 조회 성공",
            content = @Content(schema = @Schema(implementation = WrittenCardResponse.class))),
        @ApiResponse(responseCode = "400", description = "요청 값 속 유저 아이디 값 없음",
            content = @Content(schema = @Schema(implementation = BaseResponse.class)))
    })
    public ResponseEntity<WrittenCardResponse> getCardList(
        @RequestParam(value = "userId") String userId,
        @RequestParam(value = "list", defaultValue = "0", required = false) boolean list,
        @RequestParam(value = "offset", defaultValue = "0", required = false) Integer offset) {

        List<Card> findCards;

        SuccessCode code = SuccessCode.LOAD_WRITTEN_CARD_SUCCESS;
        WrittenCardResponse response;

        if(!list){
            findCards = cardService.findCards(userId, offset, 1);
            List<CardDTO> cards = findCards.stream()
                .map(card -> new CardDTO(card.getId(), card.getBackground(), card.getTitle(),
                    card.getName(), card.getBirthDate(), card.getAge(), card.getMbti(), card.getInstagram(),
                    card.getLinkName(), card.getLink(), card.getDescription(), card.getIsMincho(), card.getIsSoju(),
                    card.getIsBoomuk(), card.getIsSauced(), card.getOneQuestion(), card.getOneAnswer(),
                    card.getTwoQuestion(), card.getTwoAnswer()))
                .collect(Collectors.toList());
            response = new WrittenCardResponse(code.getMsg(), offset, cards, null);
        }else{
            findCards = cardService.findCards(userId);
            List<CardDateDTO> cardDates = findCards.stream()
                .map(card -> new CardDateDTO(card.getId(), card.getTitle(), card.getBirthDate()))
                .collect(Collectors.toList());
            response = new WrittenCardResponse(code.getMsg(), null,null, cardDates);
        }
        return new ResponseEntity(response, code.getHttpStatus());
    }


    @ApiOperation(value = "카드 우선순위 변경(명함 리스트 편집)")
    @PutMapping("/cards")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "카드 우선순위 변경 성공",
            content = @Content(schema = @Schema(implementation = BaseResponse.class))),
        @ApiResponse(responseCode = "400", description = "요청 값 부족",
            content = @Content(schema = @Schema(implementation = BaseResponse.class))),
        @ApiResponse(responseCode ="404", description = "존재하지 않는 카드 ID",
            content = @Content(schema = @Schema(implementation = BaseResponse.class)))
    })
    public ResponseEntity<BaseResponse> changeCardPriority(@RequestBody @Valid ChangePriorityDTO request){
        request.getOrdered().forEach(order -> cardService.changePriority(order.getCardId(), order.getPriority()));

        SuccessCode code = SuccessCode.MODIFY_PRIORITY_SUCCESS;
        BaseResponse response = new BaseResponse(code.getMsg());
        return new ResponseEntity(response, code.getHttpStatus());
    }

    @ApiOperation(value = "명함 세부 조회")
    @GetMapping("/card/{card-id}")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "명함 세부 조회 성공",
            content = @Content(schema = @Schema(implementation = BaseResponse.class))),
        @ApiResponse(responseCode ="404", description = "존재하지 않는 카드 ID",
            content = @Content(schema = @Schema(implementation = BaseResponse.class)))
    })
    public ResponseEntity<CardDetailResponse> getCardDetail(@PathVariable("card-id") String cardId){
        Card findCard = cardService.findOne(cardId);

        log.info("상세조회 "+ SecurityUtil.getCurrentMemberId());

        CardDTO card = new CardDTO(findCard.getId(), findCard.getBackground(), findCard.getTitle(),
            findCard.getName(), findCard.getBirthDate(), findCard.getAge(), findCard.getMbti(), findCard.getInstagram(),
            findCard.getLinkName(), findCard.getLink(), findCard.getDescription(), findCard.getIsMincho(),
            findCard.getIsSoju(), findCard.getIsBoomuk(), findCard.getIsSauced(), findCard.getOneQuestion(),
            findCard.getOneAnswer(), findCard.getTwoQuestion(), findCard.getTwoAnswer());

        SuccessCode code = SuccessCode.LOAD_CARD_SUCCESS;
        CardDetailResponse response = new CardDetailResponse(code.getMsg(), card);
        return new ResponseEntity(response, code.getHttpStatus());
    }
}
