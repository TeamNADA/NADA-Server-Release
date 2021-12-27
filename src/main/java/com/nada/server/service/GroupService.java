package com.nada.server.service;

import com.nada.server.constants.ErrorCode;
import com.nada.server.domain.Group;
import com.nada.server.domain.User;
import com.nada.server.exception.CustomException;
import com.nada.server.repository.CardGroupRepository;
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
    private final CardGroupRepository cardGroupRepository;

    /**
     * 그룹 추가
     * params : 유저아이디, 그룹
     * 중복된 이름이 존재할 경우 에러
     */
    @Transactional
    public Long create(Group group, String userId){
        User user = userRepository.findById(userId).get();

        validateGroupName(group.getName(), user);

        group.setUser(user);
        groupRepository.save(group);

        return group.getId();
    }

    private void validateGroupName(String groupName, User user) {
        List<Group> findGroups = groupRepository.findByNameAndUser(groupName, user);
        if (!findGroups.isEmpty()) {
            throw new CustomException(ErrorCode.DUPLICATE_GROUP_NAME);
        }
    }

    /**
     * 그룹 삭제
     * 존재하지 않으면 에러
     * 미분류 그룹 삭제 불가
     */
    @Transactional
    public void delete(Long groupId, Long defaultGroupId){
        Group findGroup = groupRepository.findById(groupId)
            .orElseThrow(() -> new CustomException(ErrorCode.INVALID_GROUP_ID));

        if(findGroup.getName() == "미분류") throw new CustomException(ErrorCode.CANNOT_DELETE_DEFAULT_GROUP);
        cardGroupRepository.moveToDefaultGroup(defaultGroupId, groupId);
        groupRepository.deleteById(groupId);
    }

    /**
     * 그룹명 변경
     * 존재하지 않으면 에러
     * 미분류 그룹은 그룹명 변경 불가
     */
    @Transactional
    public void changeName(Long groupId, String groupName){
        Group findGroup = groupRepository.findById(groupId)
            .orElseThrow(() -> new CustomException(ErrorCode.INVALID_GROUP_ID));
        if(findGroup.getName() == "미분류") throw new CustomException(ErrorCode.CANNOT_MODIFY_DEFAULT_GROUP);

        findGroup.setName(groupName);
    }

    /**
     * 유저가 추가한 그룹 목록 조회
     * 유저가 Unvalid하면 에러
     */
    public List<Group> findGroups(String userId){
        User findUser = userRepository.findById(userId)
            .orElseThrow(() -> new CustomException(ErrorCode.UNAUTHORIZED_USER));

        return groupRepository.findByUser(findUser);
    }

    /**
     * 받은 명함 초기화 (= 모든 그룹 삭제)
     * 미분류 그룹은 다시 추가해줍니다.
     */
    @Transactional
    public void deleteAllGroups(String id){
        User findUser = userRepository.findById(id).orElseThrow(
            () -> new CustomException(ErrorCode.UNAUTHORIZED_USER)
        );
        groupRepository.deleteAllByUserId(id);

        Group group = new Group();
        group.setUser(findUser);
        group.setName("미분류");
        groupRepository.save(group);
    }

}
