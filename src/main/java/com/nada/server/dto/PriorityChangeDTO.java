package com.nada.server.dto;

import lombok.Data;

@Data
public class PriorityChangeDTO {
    private String cardId;
    private int newPriority;

    public PriorityChangeDTO(String cardId, int newPriority) {
        this.cardId = cardId;
        this.newPriority = newPriority;
    }
}
