package com.nada.server.controller;

import com.nada.server.commons.SecurityUtil;
import com.nada.server.constants.SuccessCode;
import com.nada.server.domain.Group;
import com.nada.server.dto.BaseResponse;
import com.nada.server.dto.payload.GroupListDTO;
import com.nada.server.dto.req.CreateCardRequest;
import com.nada.server.dto.req.CreateGroupRequest;
import com.nada.server.dto.req.ModifyGroupNameRequest;
import com.nada.server.dto.res.GroupListResponse;
import com.nada.server.service.GroupService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Api(tags = "그룹 API")
public class GroupController {

    private final GroupService groupService;

    @ApiOperation(value = "그룹 추가")
    @PostMapping("/group")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "그룹 추가 성공",
            content = @Content(schema = @Schema(implementation = BaseResponse.class))),
        @ApiResponse(responseCode = "400", description = "요청 값 부족",
            content = @Content(schema = @Schema(implementation = BaseResponse.class))),
        @ApiResponse(responseCode = "409", description = "이미 존재하는 그룹 이름",
            content = @Content(schema = @Schema(implementation = BaseResponse.class)))
    })
    public ResponseEntity<BaseResponse> groupCreate(
        @RequestBody @Valid CreateGroupRequest request) {

        Group group = Group.createGroup(request.getGroupName());
        groupService.create(group, request.getUserId());

        SuccessCode code = SuccessCode.CREATE_GROUP_SUCCESS;
        BaseResponse response = new BaseResponse(code.getMsg());
        return new ResponseEntity(response, code.getHttpStatus());
    }

    @ApiOperation(value = "그룹 삭제")
    @DeleteMapping("/group/{group-id}")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "그룹 삭제 성공",
            content = @Content(schema = @Schema(implementation = BaseResponse.class))),
        @ApiResponse(responseCode = "404", description = "존재하지 않는 그룹",
            content = @Content(schema = @Schema(implementation = BaseResponse.class))),
        @ApiResponse(responseCode = "406", description = "미분류 그룹 삭제 불가",
            content = @Content(schema = @Schema(implementation = BaseResponse.class)))
    })
    public ResponseEntity<BaseResponse> deleteGroup(@PathVariable("group-id") Long groupId,
        @RequestParam("defaultGroupId") Long defaultGroupId) {
        groupService.delete(groupId, defaultGroupId);
        SuccessCode code = SuccessCode.DELETE_GROUP_SUCCESS;
        BaseResponse response = new BaseResponse(code.getMsg());
        return new ResponseEntity(response, code.getHttpStatus());
    }

    @ApiOperation(value = "그룹 리스트 조회")
    @GetMapping("/groups")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "그룹 리스트 조회 성공",
            content = @Content(schema = @Schema(implementation = BaseResponse.class))),
        @ApiResponse(responseCode = "400", description = "요청 값 부족",
            content = @Content(schema = @Schema(implementation = BaseResponse.class))),
        @ApiResponse(responseCode = "404", description = "존재하지 않는 유저",
            content = @Content(schema = @Schema(implementation = BaseResponse.class)))
    })
    public ResponseEntity<BaseResponse> deleteGroup(@RequestParam(value = "userId") String userId) {

        List<GroupListDTO> groups = groupService.findGroups(userId).stream()
            .map(group -> new GroupListDTO(group.getId(), group.getName()))
            .collect(Collectors.toList());

        SuccessCode code = SuccessCode.LOAD_GROUP_LIST_SUCCESS;
        GroupListResponse response = new GroupListResponse(code.getMsg(), groups);
        return new ResponseEntity(response, code.getHttpStatus());
    }

    @ApiOperation(value = "그룹명 수정")
    @PutMapping("/group")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "그룹명 수정 성공",
            content = @Content(schema = @Schema(implementation = BaseResponse.class))),
        @ApiResponse(responseCode = "400", description = "요청 값 부족",
            content = @Content(schema = @Schema(implementation = BaseResponse.class))),
        @ApiResponse(responseCode = "404", description = "존재하지 않는 그룹",
            content = @Content(schema = @Schema(implementation = BaseResponse.class))),
        @ApiResponse(responseCode = "406", description = "미분류 그룹 수정 불가",
            content = @Content(schema = @Schema(implementation = BaseResponse.class)))
    })
    public ResponseEntity<BaseResponse> modifyGroupName(@RequestBody @Valid ModifyGroupNameRequest request) {
        groupService.changeName(request.getGroupId(), request.getGroupName());
        SuccessCode code = SuccessCode.MODIFY_GROUP_NAME_SUCCESS;
        BaseResponse response = new BaseResponse(code.getMsg());
        return new ResponseEntity(response, code.getHttpStatus());
    }

    @ApiOperation(value = "받은 명함 초기화")
    @DeleteMapping("/groups")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "받은 명함 초기화 성공",
            content = @Content(schema = @Schema(implementation = BaseResponse.class))),
        @ApiResponse(responseCode = "401", description = "등록되지 않은 유저 정보",
            content = @Content(schema = @Schema(implementation = BaseResponse.class))),
    })
    public ResponseEntity<BaseResponse> deleteAllGroups(){
        String memberId = SecurityUtil.getCurrentMemberId();
        groupService.deleteAllGroups(memberId);

        SuccessCode code = SuccessCode.RE_INIT_GROUPS_SUCCESS;
        BaseResponse response = new BaseResponse(code.getMsg());
        return new ResponseEntity(response, code.getHttpStatus());
    }
}
