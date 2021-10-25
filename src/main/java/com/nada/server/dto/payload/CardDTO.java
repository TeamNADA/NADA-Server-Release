package com.nada.server.dto.payload;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CardDTO {
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

    private Boolean isMincho;
    private Boolean isSoju;
    private Boolean isBoomuk;
    private Boolean isSauced;

    private String oneQuestion;
    private String oneAnswer;
    private String twoQuestion;
    private String twoAnswer;
}
