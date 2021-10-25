package com.nada.server.repository;

import com.nada.server.domain.Card;
import com.nada.server.domain.User;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CardRepository extends JpaRepository<Card,String> {

    @Query("SELECT MAX(priority) FROM Card")
    Integer maxPriority();
    List<Card> findByUser(User user, Pageable pageable); // 유저의 카드 목록 조회 - 페이지네이션
    List<Card> findByUserOrderByPriorityAsc(User user); // 유저의 모든 카드 목록 조회 - 리스트 뷰
}
