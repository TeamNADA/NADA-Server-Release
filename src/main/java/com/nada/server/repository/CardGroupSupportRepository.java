package com.nada.server.repository;

import com.nada.server.domain.Card;
import com.nada.server.domain.CardGroup;
import com.nada.server.domain.Group;
import com.nada.server.domain.QCard;
import com.nada.server.domain.QCardGroup;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;



@Repository
public class CardGroupSupportRepository extends QuerydslRepositorySupport {
    private final JPAQueryFactory jpaQueryFactory;

    public CardGroupSupportRepository(JPAQueryFactory jpaQueryFactory){
        super(CardGroup.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    public List<Card> findCardsByGroup(Group group, Pageable pageable){
        QCardGroup cardGroup = QCardGroup.cardGroup;
        QCard card = QCard.card;

        return jpaQueryFactory
            .select(card)
            .from(cardGroup)
            .join(cardGroup.card, card)
            .where(cardGroup.group.eq(group))
            .orderBy(card.createDate.desc())
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();
    }
}
