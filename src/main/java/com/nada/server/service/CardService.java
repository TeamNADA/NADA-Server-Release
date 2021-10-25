package com.nada.server.service;

import com.nada.server.constants.ErrorCode;
import com.nada.server.domain.Card;
import com.nada.server.domain.User;
import com.nada.server.exception.CustomException;
import com.nada.server.repository.CardRepository;
import com.nada.server.repository.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CardService {

    private final UserRepository userRepository;
    private final CardRepository cardRepository;

    /**
     * 카드 생성
     * priority 자동으로 삽입할 수 있게끔(index 이용, max값 +1)
     * 랜덤생성 문자열 필요
     */
    @Transactional
    public String create(Card card, String userId){
        User user = userRepository.findById(userId).get();

        Integer maxPriority = cardRepository.maxPriority();
        if(maxPriority == null){
            card.setPriority(Integer.valueOf(0));
        }else{
            card.setPriority(maxPriority+1);
        }
        card.setUser(user);

        cardRepository.save(card);
        return card.getId();
    }

    /**
     * 카드 삭제
     * 없다면 에러발생
     */
    @Transactional
    public void delete(String cardId){
        cardRepository.findById(cardId)
            .orElseThrow(() -> new CustomException(ErrorCode.INVALID_CARD_ID));
        cardRepository.deleteById(cardId);
    }

    /**
     * 카드 검색
     * 없다면 에러 발생
     */
    public Card findOne(String cardId){
        return cardRepository.findById(cardId)
            .orElseThrow(() -> new CustomException(ErrorCode.INVALID_CARD_ID));
    }

    /**
     * 유저가 작성한 카드 목록 조회
     * 오프셋 기반 페이지네이션 추가
     */
    public List<Card> findCards(String userId, int offset, int size){
        Pageable paging = PageRequest.of(offset, size, Sort.by("priority").ascending());
        User user = userRepository.findById(userId).get();
        return cardRepository.findByUser(user, paging);
    }
    /**
     * 유저가 작성한 모든 카드 목록 조회
     */
    public List<Card> findCards(String userId) {
        User user = userRepository.findById(userId).get();
        return cardRepository.findByUserOrderByPriorityAsc(user);
    }

    /**
     * 카드 우선순위 변경
     * 서비스 단에는 entity접근 가능케 오고,
     * Controller에서 request를 받을 때 DTO에 mapping 시키자.
     * 해당 카드가 없으면 에러발생
     */
    @Transactional
    public void changePriority(String cardId, int priority){
        Card card = cardRepository.findById(cardId).orElseThrow(() -> new CustomException(ErrorCode.INVALID_CARD_ID));
        card.setPriority(priority);
    }
}
