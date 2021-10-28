package com.nada.server.dto.req;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ModifyGroupNameRequest {
    @NotNull(message = "그룹 아이디는 필수입니다")
    private Long groupId;
    @NotEmpty(message = "수정할 그룹 명은 필수입니다")
    private String groupName;
}
