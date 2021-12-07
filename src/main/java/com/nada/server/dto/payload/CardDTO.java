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
    private String mbti;
    private String instagram;
    private String link;
    private String description;

    private Boolean isMincho;
    private Boolean isSoju;
    private Boolean isBoomuk;
    private Boolean isSauced;

    private String oneTmi;
    private String twoTmi;
    private String threeTmi;
}
