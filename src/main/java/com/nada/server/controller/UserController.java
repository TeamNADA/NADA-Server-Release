package com.nada.server.controller;

import com.nada.server.constants.SuccessCode;
import com.nada.server.dto.BaseResponse;
import com.nada.server.dto.req.registerDTO;
import com.nada.server.domain.User;
import com.nada.server.dto.payload.UserTokenDTO;
import com.nada.server.dto.res.LoginResponse;
import com.nada.server.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import javax.validation.Valid;
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
            content = @Content(schema = @Schema(implementation = LoginResponse.class))),
        @ApiResponse(responseCode = "401", description = "로그인 실패 - 등록된 유저가 아닙니다.",
            content = @Content(schema = @Schema(implementation = BaseResponse.class)))
    })
    @GetMapping("/{user-id}/login")
    public ResponseEntity<LoginResponse> login(@PathVariable("user-id") String id){
        String userId = userService.login(id);

        SuccessCode code = SuccessCode.LOGIN_SUCCESS;
        LoginResponse response = new LoginResponse(code.getMsg(), new UserTokenDTO(userId));
        return new ResponseEntity(response, code.getHttpStatus());
    }


    @ApiOperation(value = "소셜 회원가입", notes = "회원가입 시 그룹 '미분류'가 자동으로 만들어집니다.")
    @PostMapping("/register")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "회원가입 성공",
            content = @Content(schema = @Schema(implementation = LoginResponse.class))),
        @ApiResponse(responseCode = "409", description = "회원가입 실패 - 이미 존재하는 유저입니다.",
            content = @Content(schema = @Schema(implementation = BaseResponse.class))),
    })
    public ResponseEntity<LoginResponse> register(@RequestBody @Valid registerDTO request){
        User user = new User();
        user.setId(request.getUserId());
        String userId = userService.register(user);

        SuccessCode code = SuccessCode.REGISTER_SUCCESS;
        LoginResponse response = new LoginResponse(code.getMsg(), new UserTokenDTO(userId));
        return new ResponseEntity(response, code.getHttpStatus());
    }

    @ApiOperation(value = "회원 탈퇴")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "회원탈퇴 성공",
            content = @Content(schema = @Schema(implementation = BaseResponse.class))),
        @ApiResponse(responseCode = "401", description = "회원 탈퇴 실패 - 존재하지 않는 유저입니다.",
            content = @Content(schema = @Schema(implementation = BaseResponse.class))),
    })
    @DeleteMapping("/{user-id}")
    public ResponseEntity<BaseResponse> deleteUser(@PathVariable("user-id") String id){
        userService.unsubscribe(id);

        SuccessCode code = SuccessCode.UNSUBSCRIBE_SUCCESS;
        BaseResponse response = new BaseResponse(code.getMsg());
        return new ResponseEntity(response, code.getHttpStatus());
    }



}
