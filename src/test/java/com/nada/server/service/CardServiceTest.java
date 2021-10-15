package com.nada.server.service;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.nada.server.domain.Card;
import com.nada.server.dto.PriorityChangeDTO;
import com.nada.server.repository.CardRepository;
import java.util.ArrayList;
import java.util.List;
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
        cardService.create(card, userId);

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

    @Test
    public void 작성한_카드_조회() throws Exception{
        //given
        String userId = userService.login("userA");

        Card card1 = new Card();
        card1.setId("cardA");
        cardService.create(card1, userId);

        Card card2 = new Card();
        card2.setId("cardB");
        cardService.create(card2, userId);

        //when
        List<Card> findCards = cardService.findCards(userId);

        //then
        assertThat(findCards.size()).isEqualTo(2);
    }

    @Test
    public void 카드_우선순위_변경() throws Exception{
        //given
        String userId = userService.login("userA");

        Card card1 = new Card();
        card1.setId("cardA");
        cardService.create(card1, userId);

        Card card2 = new Card();
        card2.setId("cardB");
        cardService.create(card2, userId);

        List<PriorityChangeDTO> p = new ArrayList<>();
        p.add(new PriorityChangeDTO("cardA", 1));
        p.add(new PriorityChangeDTO("cardB", 0));

        //when
        cardService.changePriority(p);
        List<Card> cards = cardService.findCards(userId);

        //then
        assertThat(cards.get(0).getId()).isEqualTo("cardB");
    }
}