package com.nada.server.repository;

import com.nada.server.domain.Card;
import com.nada.server.domain.CardGroup;
import com.nada.server.domain.Group;
import com.nada.server.domain.QCard;
import com.nada.server.domain.QCardGroup;
import com.nada.server.domain.QGroup;
import com.nada.server.repository.dto.CardFrontDTO;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;



@Repository
public class CardGroupSupportRepository extends QuerydslRepositorySupport {
    private final JPAQueryFactory jpaQueryFactory;

    public CardGroupSupportRepository(JPAQueryFactory jpaQueryFactory){
        super(CardGroup.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    public List<CardFrontDTO> findCardsByGroup(Group group){
        QCardGroup cardGroup = QCardGroup.cardGroup;
        QCard card = QCard.card;

        return jpaQueryFactory
            .select(Projections.constructor(CardFrontDTO.class,
                card.id, card.background, card.title, card.name, card.birthDate,
                card.age, card.mbti, card.instagram, card.linkName, card.link,
                card.description))
            .from(cardGroup)
            .join(cardGroup.card, card)
            .where(cardGroup.group.eq(group))
            .orderBy(card.createDate.desc())
            .fetch();
    }
}
