package com.nada.server.dto.req;

import com.nada.server.dto.payload.CreateCardDTO;
import javax.validation.Valid;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class CreateCardRequest {
    private MultipartFile image;
    @Valid
    private CreateCardDTO card;

}
