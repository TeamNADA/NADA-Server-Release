package com.nada.server.repository;

import com.nada.server.domain.Card;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CardRepository extends JpaRepository<Card,String> {

    @Query("SELECT MAX(priority) FROM Card")
    Long maxPriority();
    List<Card> findByUserId(String userId, Pageable pageable); // 유저의 카드 목록 조회
}
