package com.nada.server.dto.req;

import javax.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class CreateGroupRequest {
    @NotEmpty(message = "유저 아이디는 필수입니다")
    private String userId;
    @NotEmpty(message = "추가 할 그룹명은 필수입니다")
    private String groupName;
}
