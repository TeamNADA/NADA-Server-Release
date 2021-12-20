package com.nada.server.controller;

import com.nada.server.commons.SecurityUtil;
import com.nada.server.constants.SuccessCode;
import com.nada.server.dto.BaseResponse;
import com.nada.server.dto.payload.TokenDTO;
import com.nada.server.dto.payload.UserTokenDTO;
import com.nada.server.dto.req.LoginRequest;
import com.nada.server.dto.req.ReissueRequest;
import com.nada.server.dto.res.LoginResponse;
import com.nada.server.dto.res.ReissuseResponse;
import com.nada.server.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@Api(tags = "사용자 API")
public class UserController {

    private final UserService userService;

    @ApiOperation(value = "소셜 로그인", notes = "자동으로 회원가입되며, 회원가입 시 그룹 '미분류'가 자동으로 만들어집니다.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "로그인 성공",
            content = @Content(schema = @Schema(implementation = LoginResponse.class))),
        @ApiResponse(responseCode = "401", description = "로그인 실패 - 등록된 유저가 아닙니다.",
            content = @Content(schema = @Schema(implementation = BaseResponse.class)))
    })
    @PostMapping("/auth/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request){
        TokenDTO tokenDTO = userService.login(request.getUserId());

        SuccessCode code = SuccessCode.LOGIN_SUCCESS;
        LoginResponse response = new LoginResponse(code.getMsg(), new UserTokenDTO(request.getUserId(), tokenDTO));
        return new ResponseEntity(response, code.getHttpStatus());
    }

    @PostMapping("/auth/reissue")
    public ResponseEntity<ReissuseResponse> reissue(@Valid @RequestBody ReissueRequest request){

        TokenDTO tokenDTO = userService.reissue(request.getAccessToken(), request.getRefreshToken());
        SuccessCode code = SuccessCode.REISSUE_SUCCESS;
        ReissuseResponse response = new ReissuseResponse(code.getMsg(), tokenDTO.getAccessToken(),
            tokenDTO.getRefreshToken());

        return new ResponseEntity(response, code.getHttpStatus());
    }

    @ApiOperation(value = "회원 탈퇴")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "회원탈퇴 성공",
            content = @Content(schema = @Schema(implementation = BaseResponse.class))),
        @ApiResponse(responseCode = "401", description = "회원 탈퇴 실패 - 존재하지 않는 유저입니다.",
            content = @Content(schema = @Schema(implementation = BaseResponse.class))),
    })
    @DeleteMapping("/user")
    public ResponseEntity<BaseResponse> deleteUser(){
        String memberId = SecurityUtil.getCurrentMemberId();
        userService.unsubscribe(memberId);

        SuccessCode code = SuccessCode.UNSUBSCRIBE_SUCCESS;
        BaseResponse response = new BaseResponse(code.getMsg());
        return new ResponseEntity(response, code.getHttpStatus());
    }

    @ApiOperation(value = "로그아웃")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "로그아웃 성공",
            content = @Content(schema = @Schema(implementation = BaseResponse.class))),
        @ApiResponse(responseCode = "401", description = "로그아웃 실패 - 존재하지 않는 유저입니다.",
            content = @Content(schema = @Schema(implementation = BaseResponse.class))),
    })
    @DeleteMapping("/auth/logout")
    public ResponseEntity<BaseResponse> logout(){
        String memberId = SecurityUtil.getCurrentMemberId();
        userService.logout(memberId);

        SuccessCode code = SuccessCode.LOGOUT_SUCCESS;
        BaseResponse response = new BaseResponse(code.getMsg());
        return new ResponseEntity(response, code.getHttpStatus());
    }


}
