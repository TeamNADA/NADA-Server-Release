package com.nada.server.repository;

import com.nada.server.domain.Card;
import com.nada.server.domain.CardGroup;
import com.nada.server.domain.Group;
import com.nada.server.domain.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CardGroupRepository extends JpaRepository<CardGroup, Long> {
    Optional<CardGroup> findByCardAndUser(Card card, User user);
    Optional<CardGroup> findByCardAndUserAndGroup(Card card, User user, Group group);
    void deleteByCardAndGroup(Card card, Group group);

    @Modifying
    @Query("UPDATE CardGroup c SET c.group.id=:defaultGroupId WHERE c.group.id=:groupId")
    void moveToDefaultGroup(@Param("defaultGroupId") Long defaultGroupId, @Param("groupId") Long groupId);

    void deleteByUser(User user);
}
