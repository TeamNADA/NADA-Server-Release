package com.nada.server.dto.payload;

import java.util.Calendar;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class CardDTO {
    private String cardId;
    private String author;
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

    public CardDTO(String cardId, String author, String background, String title, String name,
        String birthDate, String mbti, String instagram, String link, String description,
        Boolean isMincho, Boolean isSoju, Boolean isBoomuk, Boolean isSauced, String oneTmi,
        String twoTmi, String threeTmi) {
        this.cardId = cardId;
        this.author = author;
        this.background = background;
        this.title = title;
        this.name = name;

        // birthdate 파싱하여 age값 세팅
        Calendar current = Calendar.getInstance();
        int currentYear  = current.get(Calendar.YEAR);
        String temp = birthDate.split("\\.")[0];
        int birthYear = Integer.parseInt(temp);
        this.birthDate = birthDate +String.format(" (%d)", currentYear-birthYear+1);

        this.mbti = mbti;
        this.instagram = instagram;
        this.link = link;
        this.description = description;
        this.isMincho = isMincho;
        this.isSoju = isSoju;
        this.isBoomuk = isBoomuk;
        this.isSauced = isSauced;
        this.oneTmi = oneTmi;
        this.twoTmi = twoTmi;
        this.threeTmi = threeTmi;
    }
}
