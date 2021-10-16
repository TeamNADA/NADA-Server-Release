package com.nada.server.dto;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class ResultDTO<T>{
    private String msg;
    private Boolean success;
    private T data;
    private LocalDateTime timestamp;

    public ResultDTO(String msg, Boolean success, T data) {
        this.msg = msg;
        this.success = success;
        this.data = data;
        this.timestamp = LocalDateTime.now();
    }
}