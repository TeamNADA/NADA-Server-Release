package com.nada.server.dto.payload;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GroupListDTO {
    private Long groupId;
    private String groupName;
}
