package com.nada.server.service;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.nada.server.domain.Card;
import com.nada.server.repository.CardRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class CardServiceTest {
    @Autowired
    UserService userService;

    @Autowired
    CardService cardService;
    @Autowired
    CardRepository cardRepository;

    @Test
    public void 카드_생성() throws Exception{
        //given
        String userId = userService.login("userA");

        Card card = new Card();
        card.setId("cardA");

        //when
        String cardId = cardService.create(card, userId);

        //then
        assertThat(cardRepository.findById(cardId).get().getId()).isEqualTo("cardA");
    }

    @Test
    public void 카드_삭제() throws Exception{
        //given
        String userId = userService.login("userA");

        Card card = new Card();
        card.setId("cardA");
        String cardId = cardService.create(card, userId);

        //when
        cardService.delete(cardId);

        //then
        assertThat(cardRepository.findById(cardId).isEmpty()).isEqualTo(true);
    }

    @Test
    public void 카드_검색() throws Exception{
        //given
        String userId = userService.login("userA");

        Card card = new Card();
        card.setId("cardA");
        String cardId = cardService.create(card, userId);

        //when
        Card findCard = cardService.findOne("cardA");

        //then
        assertThat(findCard.getId()).isEqualTo("cardA");
    }

    @Test
    public void 카드_검색_예외() throws Exception{
        //given
        String userId = userService.login("userA");

        Card card = new Card();
        card.setId("cardA");
        String cardId = cardService.create(card, userId);

        //when
        IllegalStateException e = assertThrows(IllegalStateException.class,
            () -> cardService.findOne("cardB"));

        //then
        assertThat(e.getMessage()).isEqualTo("존재하지 않는 카드입니다.");
    }
}