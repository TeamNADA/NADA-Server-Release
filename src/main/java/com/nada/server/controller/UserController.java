package com.nada.server.controller;

import com.nada.server.domain.User;
import com.nada.server.dto.res.LoginDTO;
import com.nada.server.dto.res.ResponseDTO;
import com.nada.server.dto.req.registerDTO;
import com.nada.server.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import javax.validation.Valid;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@Api(tags = "사용자 API")
public class UserController {

    private final UserService userService;
    @ApiOperation(value = "소셜 로그인")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "로그인 성공",
            content = @Content(schema = @Schema(implementation = LoginResponse.class)))
    })
    @GetMapping("/{user-id}/login")
    public ResponseEntity<LoginResponse> login(@PathVariable("user-id") String id){
        String userId = userService.login(id);

        LoginResponse response = new LoginResponse(true, "회원가입 성공", new LoginDTO(userId));
        return new ResponseEntity(response, HttpStatus.OK);
    }


    @ApiOperation(value = "소셜 회원가입", notes = "회원가입 시 그룹 '미분류'가 자동으로 만들어집니다.")
    @PostMapping("/register")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "회원가입 성공",
            content = @Content(schema = @Schema(implementation = LoginResponse.class)))
    })
    public ResponseEntity<LoginResponse> register(@RequestBody @Valid registerDTO request){
        User user = new User();
        user.setId(request.getUserId());
        String userId = userService.register(user);
        LoginResponse response = new LoginResponse(true, "회원가입 성공", new LoginDTO(userId));
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @ApiOperation(value = "회원 탈퇴")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "회원가입 성공",
            content = @Content(schema = @Schema(implementation = ResponseDTO.class)))
    })
    @DeleteMapping("/{user-id}")
    public ResponseEntity<ResponseDTO> deleteUser(@PathVariable("user-id") String id){
        userService.unsubscribe(id);
        ResponseDTO response = new ResponseDTO(true, "회원 탈퇴 성공");
        return new ResponseEntity(response, HttpStatus.OK);
    }

    static class LoginResponse extends ResponseDTO<LoginDTO>{
        public LoginResponse(Boolean success, String msg, LoginDTO res){
            super(success, msg, res);
        }
    }


}
