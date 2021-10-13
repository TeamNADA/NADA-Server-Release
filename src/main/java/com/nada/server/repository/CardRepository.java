package com.nada.server.repository;

import com.nada.server.domain.Card;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card,String> {
    List<Card> findByUserId(String userId); // 유저의 카드 목록 조회
}
