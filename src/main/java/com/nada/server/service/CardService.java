package com.nada.server.service;

import com.nada.server.domain.Card;
import com.nada.server.domain.User;
import com.nada.server.dto.CardFrontDTO;
import com.nada.server.dto.PriorityChangeDTO;
import com.nada.server.repository.CardRepository;
import com.nada.server.repository.UserRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
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
     */
    @Transactional
    public String create(Card card, String userId){
        User user = userRepository.findById(userId).get();
        card.setUser(user);
        card.setCreateDate(LocalDateTime.now());

        cardRepository.save(card);
        return card.getId();
    }

    /**
     * 카드 삭제
     */
    @Transactional
    public void delete(String cardId){
        cardRepository.deleteById(cardId);
    }

    /**
     * 카드 검색
     * 없다면 에러 발생
     */
    public Card findOne(String cardId){
        Optional<Card> findCard = cardRepository.findById(cardId);

        if(findCard.isPresent()){
            return findCard.get();
        }else{
            throw new IllegalStateException("존재하지 않는 카드입니다.");
        }
    }

    /**
     * 유저가 작성한 카드 목록 조회
     */
    public List<Card> findCards(String userId){
        return cardRepository.findByUserIdOrderByPriorityAsc(userId);
    }

    /**
     * 카드 우선순위 변경
     */
    public void changePriority(List<PriorityChangeDTO> changeList){
        for (PriorityChangeDTO p : changeList) {
            Card card = cardRepository.findById(p.getCardId()).get();
            card.setPriority(p.getNewPriority());
        }
    }
}
