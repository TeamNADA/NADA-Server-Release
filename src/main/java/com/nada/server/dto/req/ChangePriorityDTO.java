package com.nada.server.dto.req;

import com.nada.server.dto.payload.CardPriorityDTO;
import java.util.List;
import javax.validation.Valid;
import lombok.Data;

@Data
public class ChangePriorityDTO {
    List<@Valid CardPriorityDTO> ordered;
}
