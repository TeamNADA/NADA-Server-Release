package com.nada.server.dto.res;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDTO<T> {
    private Boolean success;
    private String msg;

    private T data;
    private LocalDateTime timestamp;


    public ResponseDTO(Boolean success, String msg, T data) {
        this.success = success;
        this.msg = msg;
        this.data = data;
        this.timestamp = LocalDateTime.now();
    }

    public ResponseDTO(Boolean success, String msg) {
        this.success = success;
        this.msg = msg;
        this.data = null;
        this.timestamp = LocalDateTime.now();
    }
}
