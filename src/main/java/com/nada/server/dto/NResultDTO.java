package com.nada.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NResultDTO {
    private String msg;
    private Boolean success;
}
