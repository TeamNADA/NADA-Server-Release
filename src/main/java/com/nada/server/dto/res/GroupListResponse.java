package com.nada.server.dto.res;

import com.nada.server.dto.BaseResponse;
import com.nada.server.dto.payload.GroupListDTO;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class GroupListResponse extends BaseResponse {

    private GroupListData data;
    public GroupListResponse(String msg, List<GroupListDTO> groups){
        super(msg);
        this.data = new GroupListData(groups);
    }
    @Getter
    @AllArgsConstructor
    static class GroupListData{
        List<GroupListDTO> groups;
    }
}
