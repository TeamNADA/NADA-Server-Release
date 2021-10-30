package com.nada.server.dto.req;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ModifyCardGroupRequest {

    @NotNull(message = "기존 그룹 아이디는 필수입니다")
    private Long groupId;
    @NotNull(message = "새롭게 지정될 그룹 아이디는 필수입니다")
    private Long newGroupId;
    @NotEmpty(message = "카드 아이디는 필수입니다")
    private String cardId;
    @NotEmpty(message = "유저 아이디는 필수입니다")
    private String userId;

}
