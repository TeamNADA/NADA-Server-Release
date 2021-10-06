package com.nada.server.domain;

import static javax.persistence.FetchType.LAZY;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Card {

    @Id
    @Column(name = "card_id")
    private String id;

    private String background;

    private String birthDate;
    private String title;
    private String name;

    private int age;
    private String mbti;
    private String instagram;
    private String linkName;
    private String link;
    private String description;

    private Boolean isMincho;

    private Boolean isSoju;
    private Boolean isBoomuk;
    private Boolean isSauced;

    private String oneQuestion;
    private String oneAnswer;
    private String twoQuestion;
    private String twoAnswer;

    @GeneratedValue
    private int priority;

    private LocalDateTime createDate;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "card", cascade = CascadeType.ALL)
    private List<CardGroup> cardGroups = new ArrayList<>();


}
