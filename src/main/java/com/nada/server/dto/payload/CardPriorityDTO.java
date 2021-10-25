package com.nada.server.dto.payload;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
public class CardPriorityDTO {
    @NotEmpty(message = "카드 아이디는 필수입니다!")
    private String cardId;
    @NotNull(message = "카드의 우선순위를 넣어주세요!")
    private Integer priority;
}
