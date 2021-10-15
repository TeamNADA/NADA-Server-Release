package com.nada.server.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import com.nada.server.domain.Card;
import com.nada.server.domain.Group;
import com.nada.server.repository.CardGroupRepository;

import com.nada.server.repository.dto.CardFrontDTO;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class CardGroupServiceTest {

    @Autowired
    CardGroupService cardGroupService;
    @Autowired
    CardGroupRepository cardGroupRepository;

    @Autowired
    UserService userService;

    @Autowired
    CardService cardService;

    @Autowired
    GroupService groupService;

    @Test
    public void 카드_그룹_추가_중복_예외() throws Exception{
        //given
        String authorId = userService.login("userA"); // 작성자
        String userId = userService.login("userB"); // 카드 추가할 유저

        Group group1 = new Group();
        group1.setName("groupA");
        Long groupId = groupService.create(group1, userId);

        Card card = new Card();
        card.setId("cardA");
        String cardId = cardService.create(card, authorId);

        //when
        cardGroupService.add(cardId, groupId, userId);
        IllegalStateException e = assertThrows(IllegalStateException.class,
            () -> cardGroupService.add(cardId, groupId, userId));

        //then
        assertThat(e.getMessage()).isEqualTo("이미 추가된 카드입니다.");
    }

    @Test
    public void 카드_추가_자신의_것_예외() throws Exception{
        //given
        String authorId = userService.login("userA"); // 작성자이자 추가할 자

        Group group1 = new Group();
        group1.setName("groupA");
        Long groupId = groupService.create(group1, authorId);

        Card card = new Card();
        card.setId("cardA");
        String cardId = cardService.create(card, authorId);

        //when
        IllegalStateException e = assertThrows(IllegalStateException.class,
            () -> cardGroupService.add(cardId, groupId, authorId));

        //then
        assertThat(e.getMessage()).isEqualTo("자신의 카드입니다.");
    }

    @Test
    public void 속한_그룹_변경() throws Exception{
        //given
        String authorId = userService.login("userA"); // 작성자
        String userId = userService.login("userB"); // 카드 추가할 유저

        Group group1 = new Group();
        group1.setName("groupA");
        Long group1Id = groupService.create(group1, userId);

        Group group2 = new Group();
        group2.setName("groupB");
        Long group2Id = groupService.create(group2, userId);

        Card card = new Card();
        card.setId("cardA");
        String cardId = cardService.create(card, authorId);
        Long addCardGroupId = cardGroupService.add(cardId, group1Id, userId);

        //when
        Long changeCardGroupId = cardGroupService.change(cardId, group1Id, userId, group2Id);

        //then
        assertThat(addCardGroupId).isEqualTo(changeCardGroupId);
        assertThat(cardGroupRepository.findById(addCardGroupId).get().getGroup().getName()).isEqualTo("groupB");

    }

    @Test
    public void 그룹_속_카드_조회() throws Exception{
        //given
        String authorId = userService.login("userA"); // 작성자
        String userId = userService.login("userB"); // 카드 추가할 유저

        Group group = new Group();
        group.setName("groupA");
        Long groupId = groupService.create(group, userId);

        Card card1 = new Card();
        card1.setId("cardA");
        String card1Id = cardService.create(card1, authorId);
        cardGroupService.add(card1Id, groupId, userId);

        Card card2 = new Card();
        card2.setId("cardB");
        String card2Id = cardService.create(card2, authorId);
        cardGroupService.add(card2Id, groupId, userId);

        //when
        List<CardFrontDTO> findCards = cardGroupService.findCardsByGroup(groupId);

        //then
        assertThat(findCards.size()).isEqualTo(2);

    }

    @Test
    public void 그룹_속_카드_삭제() throws Exception{
        //given
        String authorId = userService.login("userA"); // 작성자
        String userId = userService.login("userB"); // 카드 추가할 유저

        Group group = new Group();
        group.setName("groupA");
        Long groupId = groupService.create(group, userId);

        Card card1 = new Card();
        card1.setId("cardA");
        String card1Id = cardService.create(card1, authorId);
        cardGroupService.add(card1Id, groupId, userId);

        Card card2 = new Card();
        card2.setId("cardB");
        String card2Id = cardService.create(card2, authorId);
        cardGroupService.add(card2Id, groupId, userId);

        //when
        cardGroupService.deleteCardFromGroup(card1Id, groupId);

        //then
        List<CardFrontDTO> findCards = cardGroupService.findCardsByGroup(groupId);
        assertThat(findCards.size()).isEqualTo(1);

    }
}