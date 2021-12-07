package com.nada.server.dto.payload;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CardDateDTO {
    private String cardId;
    private String title;
}
