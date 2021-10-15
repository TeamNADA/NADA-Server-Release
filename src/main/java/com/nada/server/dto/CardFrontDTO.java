package com.nada.server.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class CardFrontDTO {
    private String cardId;
    private String background;
    private String title;
    private String name;
    private String birthDate;
    private String age;
    private String mbti;
    private String instagram;
    private String linkName;
    private String link;
    private String description;

    public CardFrontDTO(String cardId, String background, String title, String name,
        String birthDate, String age, String mbti, String instagram, String linkName,
        String link, String description) {
        this.cardId = cardId;
        this.background = background;
        this.title = title;
        this.name = name;
        this.birthDate = birthDate;
        this.age = age;
        this.mbti = mbti;
        this.instagram = instagram;
        this.linkName = linkName;
        this.link = link;
        this.description = description;
    }
}
