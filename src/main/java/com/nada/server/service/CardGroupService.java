package com.nada.server.service;

import com.nada.server.domain.Card;
import com.nada.server.domain.CardGroup;
import com.nada.server.domain.Group;
import com.nada.server.domain.User;
import com.nada.server.repository.CardGroupRepository;
import com.nada.server.repository.CardGroupSupportRepository;
import com.nada.server.repository.CardRepository;
import com.nada.server.repository.GroupRepository;
import com.nada.server.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CardGroupService {

    private final CardGroupRepository cardGroupRepository;
    private final GroupRepository groupRepository;
    private final CardRepository cardRepository;
    private final UserRepository userRepository;

    private final CardGroupSupportRepository cardGroupSupportRepository;

    /**
     * 검색한 카드 유저의 그룹에 추가
     * 이미 추가되어있는 카드라면 추가 불가능(validateDuplicateCard)
     * 자신의 카드라면 추가 불가능(ValidateMyCard)
     */
    @Transactional
    public Long add(String cardId, Long groupId, String userId){
        Group findGroup = groupRepository.findById(groupId).get();
        User findUser = userRepository.findById(userId).get();
        Card findCard = cardRepository.findById(cardId).get();

        // 이미 추가된 카드인지 확인
        validateDuplicateCard(findCard, findUser);

        // 자신의 카드인지 확인
        validateMyCard(userId, findCard.getUser().getId());

        CardGroup cardGroup = new CardGroup();
        cardGroup.setGroup(findGroup);
        cardGroup.setCard(findCard);
        cardGroup.setUser(findGroup.getUser());

        cardGroupRepository.save(cardGroup);

        return cardGroup.getId();
    }

    // 자신의 카드인지 확인
    private void validateMyCard(String authorId, String userId) {
        if(authorId == userId){
            throw new IllegalStateException("자신의 카드입니다.");
        }
    }

    // 이미 등록된(추가된) 카드인지 확인
    private void validateDuplicateCard(Card findCard, User findUser) {
        Optional<CardGroup> findCardGroup = cardGroupRepository.findByCardAndUser(findCard, findUser);
        if(!findCardGroup.isEmpty()){
            throw new IllegalStateException("이미 추가된 카드입니다.");
        }
    }

    /**
     * 그룹 변경 => 이러면 기존 그룹으로 바꾸려고 해도 OKAY
     */
    @Transactional
    public Long change(String cardId, Long groupId, String userId, Long newGroupId){
        Group originGroup = groupRepository.findById(groupId).get();
        Group newGroup = groupRepository.findById(newGroupId).get();

        User findUser = userRepository.findById(userId).get();
        Card findCard = cardRepository.findById(cardId).get();

        CardGroup cardGroup = cardGroupRepository.findByCardAndUserAndGroup(findCard, findUser, originGroup).get();
        cardGroup.setGroup(newGroup);

        return cardGroup.getId();
    }

    /**
     * 그룹에 포함된 카드 목록 조회
     */
    public List<Card> findCardsByGroup(Long groupId, int offset, int size){
        Group findGroup = groupRepository.findById(groupId).get();

        Pageable paging = PageRequest.of(offset, size, Sort.by("createDate").descending());
        return cardGroupSupportRepository.findCardsByGroup(findGroup, paging);
    }

    /**
     * 그룹 속 카드 삭제
     */
    public void deleteCardFromGroup(String cardId, Long groupId){
        Group findGroup = groupRepository.findById(groupId).get();
        Card findCard = cardRepository.findById(cardId).get();

        cardGroupRepository.deleteByCardAndGroup(findCard, findGroup);
    }
}
