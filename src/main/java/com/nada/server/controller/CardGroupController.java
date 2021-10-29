package com.nada.server.controller;

import com.nada.server.constants.SuccessCode;
import com.nada.server.dto.BaseResponse;
import com.nada.server.dto.req.CreateCardGroupRequest;
import com.nada.server.service.CardGroupService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Api(tags = "그룹-카드 API")
public class CardGroupController {

    private final CardGroupService cardGroupService;

    @ApiOperation(value = "그룹 속 카드 추가")
    @PostMapping("/groups/card")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "그룹 속 카드 추가 성공",
            content = @Content(schema = @Schema(implementation = BaseResponse.class))),
        @ApiResponse(responseCode = "400", description = "요청 값 부족 & 자신이 작성한 카드 추가 불가능",
            content = @Content(schema = @Schema(implementation = BaseResponse.class))),
        @ApiResponse(responseCode = "401", description = "등록되지 않은 유저 정보",
            content = @Content(schema = @Schema(implementation = BaseResponse.class))),
        @ApiResponse(responseCode = "404", description = "존재하지 않은 카드, 그룹",
            content = @Content(schema = @Schema(implementation = BaseResponse.class))),
        @ApiResponse(responseCode = "409", description = "이미 추가된 카드",
            content = @Content(schema = @Schema(implementation = BaseResponse.class)))
    })
    public ResponseEntity<BaseResponse> groupCardCreate(@RequestBody @Valid CreateCardGroupRequest request){

        cardGroupService.add(request.getCardId(), request.getGroupId(), request.getUserId());
        SuccessCode code = SuccessCode.CREATE_GROUP_CARD_SUCCESS;
        BaseResponse response = new BaseResponse(code.getMsg());
        return new ResponseEntity(response, code.getHttpStatus());
    }


}
