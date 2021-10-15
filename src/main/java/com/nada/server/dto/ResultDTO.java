package com.nada.server.dto;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResultDTO<T>{
    private String msg;
    private Boolean success;
    private T data;
}