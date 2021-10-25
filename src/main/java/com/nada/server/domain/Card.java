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

    private String age;
    private String mbti;

    @Builder.Default
    private String instagram = "";
    @Builder.Default
    private String linkName = "";
    @Builder.Default
    private String link = "";
    @Builder.Default
    private String description = "";

    private Boolean isMincho;
    private Boolean isSoju;
    private Boolean isBoomuk;
    private Boolean isSauced;

    @Builder.Default
    private String oneQuestion = "";
    @Builder.Default
    private String oneAnswer = "";
    @Builder.Default
    private String twoQuestion = "";
    @Builder.Default
    private String twoAnswer = "";

    private Integer priority;

    private LocalDateTime createDate = LocalDateTime.now();

    @ManyToOne(fetch = LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id")
    private User user;

    public static Card createCard(String background, String birthDate, String title, String name, String mbti,
        String instagram, String linkName, String link, String description, Boolean isMincho,
        Boolean isSoju, Boolean isBoomuk, Boolean isSauced, String oneQuestion,
        String oneAnswer, String twoQuestion, String twoAnswer) {

        Card card = new Card();

        // card Id 랜덤 UUID 이용한 랜덤생성
        String cardId = UUID.randomUUID().toString().replaceAll("-", ""); // -를 제거해 주었다.
        cardId = cardId.substring(0, 10).toUpperCase();
        card.setId(cardId);

        // birthdate 파싱하여 age값 세팅
        Calendar current = Calendar.getInstance();
        int currentYear  = current.get(Calendar.YEAR);
        String temp = birthDate.split("/")[0];
        int birthYear = Integer.parseInt(temp);
        card.setAge((currentYear-birthYear+1) +"세");

        card.setBackground(background);
        card.setBirthDate(birthDate);
        card.setTitle(title);
        card.setName(name);
        card.setMbti(mbti);

        card.setInstagram(instagram);
        card.setLinkName(linkName);
        card.setLink(link);
        card.setDescription(description);

        card.setIsMincho(isMincho);
        card.setIsSoju(isSoju);
        card.setIsBoomuk(isBoomuk);
        card.setIsSauced(isSauced);

        card.setOneQuestion(oneQuestion);
        card.setOneAnswer(oneAnswer);
        card.setTwoQuestion(twoQuestion);
        card.setTwoAnswer(twoAnswer);

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
