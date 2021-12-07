package com.nada.server.dto.payload;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CardFrontDTO {
    private String cardId;
    private String background;
    private String title;
    private String name;
    private String birthDate;
    private String mbti;
    private String instagram;
    private String link;
    private String description;
}
