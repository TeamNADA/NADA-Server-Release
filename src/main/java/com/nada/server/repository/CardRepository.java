package com.nada.server.repository;

import com.nada.server.domain.Card;
import com.nada.server.domain.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CardRepository extends JpaRepository<Card,String> {
    @Query("SELECT c FROM Card c JOIN FETCH c.user WHERE c.id=:id")
    Optional<Card> findById(@Param("id") String id);
    @Query("SELECT MIN(c.priority) FROM Card c WHERE c.user.id=:userId")
    Integer minPriority(@Param("userId") String userId);

    @Modifying
    @Query("UPDATE Card c SET c.priority = c.priority+1 WHERE c.priority > :minPriority")
    void updatePriority(@Param("minPriority") Integer minPriority);

    @Query("SELECT c FROM Card c JOIN FETCH c.user WHERE c.user=:user")
    List<Card> findByUser(@Param("user")User user, Pageable pageable); // 유저의 카드 목록 조회 - 페이지네이션
    List<Card> findByUserOrderByPriorityAsc(User user); // 유저의 모든 카드 목록 조회 - 리스트 뷰
}
