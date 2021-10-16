package com.nada.server.controller;

import com.nada.server.domain.User;
import com.nada.server.dto.NResultDTO;
import com.nada.server.dto.ResultDTO;
import com.nada.server.service.UserService;
import io.swagger.annotations.ApiOperation;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @ApiOperation(value = "소셜 로그인")
    @GetMapping("/{user-id}/login")
    public ResultDTO<LoginResponse> login(@PathVariable("user-id") String id){
        String userId = userService.login(id);
        return new ResultDTO("로그인 성공!", true, new LoginResponse(userId));
    }

    @ApiOperation(value = "소셜 회원가입", notes = "회원가입 시 그룹 '미분류'가 자동으로 만들어집니다.")
    @PostMapping("/register")
    public ResultDTO<LoginResponse> register(@RequestBody @Valid registerRequest request){
        User user = new User();
        user.setId(request.getUserId());
        String userId = userService.register(user);
        return new ResultDTO("회원가입 성공!", true, new LoginResponse(userId));
    }

    @ApiOperation(value = "회원 탈퇴")
    @DeleteMapping("/{user-id}")
    public NResultDTO deleteUser(@PathVariable("user-id") String id){
        userService.unsubscribe(id);
        return new NResultDTO("회원 탈퇴 성공!", true);
    }
    
    @Data
    @AllArgsConstructor
    // 이후에 token들이 추가 되어야합니다.
    static class LoginResponse {
        private String userId;
    }

    @Data
    @AllArgsConstructor
    static class registerRequest {
        private String userId;
    }

}
