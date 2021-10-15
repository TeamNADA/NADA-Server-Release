package com.nada.server.controller;

import com.nada.server.dto.NResultDTO;
import com.nada.server.dto.ResultDTO;
import com.nada.server.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @ApiOperation(value = "소셜 로그인(회원가입)", notes = "유저가 없다면 회원가입을 진행합니다.")
    @GetMapping("/{user-id}/login")
    public ResultDTO<LoginResponse> login(@PathVariable("user-id") String id){
        String userId = userService.login(id);
        return new ResultDTO("로그인 성공!", true, new LoginResponse(userId));
    }

    @ApiOperation(value = "회원 탈퇴")
    @DeleteMapping("/{user-id}")
    public NResultDTO deleteUser(@PathVariable("user-id") String id){
        userService.unsubscribe(id);
        return new NResultDTO("회원 탈퇴 성공!", true);
    }
    
    @Data
    @AllArgsConstructor
    static class LoginResponse {
        private String userId;
    }

}
