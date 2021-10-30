package com.nada.server.dto.req;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateCardGroupRequest {
    @NotNull(message = "그룹 아이디는 필수입니다")
    private Long groupId;
    @NotEmpty(message = "카드 아이디는 필수입니다")
    private String cardId;
    @NotEmpty(message = "유저 아이디는 필수입니다")
    private String userId;
}
