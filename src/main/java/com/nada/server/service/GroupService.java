package com.nada.server.service;

import com.nada.server.constants.ErrorCode;
import com.nada.server.domain.Group;
import com.nada.server.domain.User;
import com.nada.server.exception.CustomException;
import com.nada.server.repository.GroupRepository;
import com.nada.server.repository.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GroupService {

    private final GroupRepository groupRepository;
    private final UserRepository userRepository;

    /**
     * 그룹 추가
     * params : 유저아이디, 그룹
     * 중복된 이름이 존재할 경우 에러
     */
    @Transactional
    public Long create(Group group, String userId){

        validateGroupName(group.getName(), userId);

        User user = userRepository.findById(userId).get();

        group.setUser(user);
        groupRepository.save(group);

        return group.getId();
    }

    private void validateGroupName(String groupName, String userId) {
        List<Group> findGroups = groupRepository.findByNameAndUser_Id(groupName, userId);
        if (!findGroups.isEmpty()) {
            throw new CustomException(ErrorCode.DUPLICATE_GROUP_NAME);
        }
    }

    /**
     * 그룹 삭제
     * 존재하지 않으면 에러
     */
    @Transactional
    public void delete(Long groupId){
        groupRepository.findById(groupId)
            .orElseThrow(() -> new CustomException(ErrorCode.INVALID_GROUP_ID));
        groupRepository.deleteById(groupId);
    }

    /**
     * 그룹명 변경
     */
    @Transactional
    public void changeName(Long groupId, String groupName){
        Group findGroup = groupRepository.findById(groupId)
            .orElseThrow(() -> new CustomException(ErrorCode.INVALID_GROUP_ID));
        findGroup.setName(groupName);
    }

    /**
     * 유저가 추가한 그룹 목록 조회
     */
    public List<Group> findGroups(String userId){
        return groupRepository.findByUserId(userId);
    }

}
