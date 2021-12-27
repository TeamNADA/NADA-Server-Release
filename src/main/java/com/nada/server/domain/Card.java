package com.nada.server.domain;

import static javax.persistence.FetchType.LAZY;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PostLoad;
import javax.persistence.PrePersist;
import javax.persistence.Transient;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.domain.Persistable;

@Entity
@Getter @Setter
public class Card implements Persistable<String> {

    @Id
    @Column(name = "card_id")
    private String id;

    private String background;

    private String birthDate;
    private String title;
    private String name;

    private String mbti;

    @Builder.Default
    private String instagram = "";
    @Builder.Default
    private String link = "";
    @Builder.Default
    private String description = "";

    private Boolean isMincho;
    private Boolean isSoju;
    private Boolean isBoomuk;
    private Boolean isSauced;

    @Builder.Default
    private String oneTmi = "";
    @Builder.Default
    private String twoTmi = "";
    @Builder.Default
    private String threeTmi = "";

    private Integer priority;

    private LocalDateTime createDate = LocalDateTime.now();

    @ManyToOne(fetch = LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id")
    private User user;

    public static Card createCard(String background, String birthDate, String title, String name, String mbti,
        String instagram, String link, String description, Boolean isMincho,
        Boolean isSoju, Boolean isBoomuk, Boolean isSauced, String oneTmi,
        String twoTmi, String threeTmi) {

        Card card = new Card();

        // card Id 랜덤 UUID 이용한 랜덤생성
        String cardId = UUID.randomUUID().toString().replaceAll("-", ""); // -를 제거해 주었다.
        cardId = cardId.substring(0, 6).toUpperCase();
        card.setId(cardId);
        card.setBackground(background);
        card.setBirthDate(birthDate);
        card.setTitle(title);
        card.setName(name);
        card.setMbti(mbti);

        card.setInstagram(instagram);
        card.setLink(link);
        card.setDescription(description);

        card.setIsMincho(isMincho);
        card.setIsSoju(isSoju);
        card.setIsBoomuk(isBoomuk);
        card.setIsSauced(isSauced);

        card.setOneTmi(oneTmi);
        card.setTwoTmi(twoTmi);
        card.setThreeTmi(threeTmi);

        return card;
    }

    // save 전 select문 없애기 위함
    @Transient
    private boolean isNew = true;

    @Override
    public boolean isNew() {
        return isNew;
    }

    @PrePersist
    @PostLoad
    void markNotNew() {
        this.isNew = false;
    }
}
