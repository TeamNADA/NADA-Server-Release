package com.nada.server.dto;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class NResultDTO {
    private String msg;
    private Boolean success;
    private LocalDateTime timestamp;

    public NResultDTO(String msg, Boolean success) {
        this.msg = msg;
        this.success = success;
        this.timestamp = LocalDateTime.now();
    }
}
