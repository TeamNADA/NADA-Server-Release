package com.nada.server.service;

import com.nada.server.constants.ErrorCode;
import com.nada.server.domain.Card;
import com.nada.server.domain.CardGroup;
import com.nada.server.domain.Group;
import com.nada.server.domain.User;
import com.nada.server.exception.CustomException;
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
     * 그룹이 자신의 그룹이 아니면 추가 불가능
     * 이미 추가되어있는 카드라면 추가 불가능
     * 자신의 카드라면 추가 불가능(ValidateCard)
     */
    @Transactional
    public Long add(String cardId, Long groupId, String userId){
        User findUser = userRepository.findById(userId).orElseThrow(
            () -> new CustomException(ErrorCode.UNAUTHORIZED_USER)
        );
        Group findGroup = groupRepository.findById(groupId).orElseThrow(
            () -> new CustomException(ErrorCode.INVALID_GROUP_ID)
        );
        Card findCard = cardRepository.findById(cardId).orElseThrow(
            () -> new CustomException(ErrorCode.INVALID_CARD_ID)
        );

        // 자신의 그룹인지 확인
        validateMyGroup(findGroup, findUser);
        // 이미 추가된 카드인지 확인
        validateCard(findCard, findUser);

        CardGroup cardGroup = new CardGroup();
        cardGroup.setGroup(findGroup);
        cardGroup.setCard(findCard);
        cardGroup.setUser(findGroup.getUser());

        cardGroupRepository.save(cardGroup);

        return cardGroup.getId();
    }

    private void validateMyGroup(Group findGroup, User findUser) {
        if(findGroup.getUser().getId() != findUser.getId()){
            throw new CustomException(ErrorCode.NOT_MY_GROUP);
        }
    }


    private void validateCard(Card findCard, User findUser) {
        // 자신의 카드인지 확인
        if(findCard.getUser().getId() == findUser.getId()){
            throw new CustomException(ErrorCode.CANNOT_ADD_MY_CARD);
        }
        // 이미 등록된(추가된) 카드인지 확인
        Optional<CardGroup> findCardGroup = cardGroupRepository.findByCardAndUser(findCard, findUser);
        if(!findCardGroup.isEmpty()){
            throw new CustomException(ErrorCode.DUPLICATE_CARD_ID);
        }
    }


    /**
     * 그룹 변경 => 이러면 기존 그룹으로 바꾸려고 해도 OKAY
     * 내가 만든 그룹이 아니면 에러
     * 존재하지 않은 카드이면 에러
     * 추가하지 않은 카드이면 에러
     */
    @Transactional
    public Long change(String cardId, Long groupId, String userId, Long newGroupId){

        User findUser = userRepository.findById(userId).orElseThrow(
            () -> new CustomException(ErrorCode.UNAUTHORIZED_USER)
        );

        Group originGroup = groupRepository.findById(groupId).orElseThrow(
            () -> new CustomException(ErrorCode.INVALID_GROUP_ID)
        );;
        Group newGroup = groupRepository.findById(newGroupId).orElseThrow(
            () -> new CustomException(ErrorCode.INVALID_GROUP_ID)
        );;

        validateMyGroup(originGroup, findUser);
        validateMyGroup(newGroup, findUser);

        Card findCard = cardRepository.findById(cardId).orElseThrow(
            () -> new CustomException(ErrorCode.INVALID_CARD_ID)
        );


        CardGroup cardGroup = cardGroupRepository.findByCardAndUserAndGroup(findCard, findUser, originGroup).orElseThrow(
            () -> new CustomException(ErrorCode.INVALID_CARD_GROUP)
        );
        cardGroup.setGroup(newGroup);

        return cardGroup.getId();
    }

    /**
     * 그룹에 포함된 카드 목록 조회
     * 존재하지 않은 그룹이면 에러
     */
    public List<Card> findCardsByGroup(Long groupId, int offset, int size){
        Group findGroup = groupRepository.findById(groupId).orElseThrow(
            () -> new CustomException(ErrorCode.INVALID_GROUP_ID)
        );;

        Pageable paging = PageRequest.of(offset, size, Sort.by("create_date").descending());
        return cardGroupSupportRepository.findCardsByGroup(findGroup, paging);
    }

    /**
     * 그룹 속 카드 삭제
     */
    @Transactional
    public void deleteCardFromGroup(String cardId, Long groupId){
        Group findGroup = groupRepository.findById(groupId).orElseThrow(
            () -> new CustomException(ErrorCode.INVALID_GROUP_ID)
        );
        Card findCard = cardRepository.findById(cardId).orElseThrow(
            () -> new CustomException(ErrorCode.INVALID_CARD_ID)
        );

        cardGroupRepository.deleteByCardAndGroup(findCard, findGroup);
    }
}
