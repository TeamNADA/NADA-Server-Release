package com.nada.server.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import com.nada.server.domain.Group;
import com.nada.server.repository.GroupRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class GroupServiceTest {

    @Autowired
    UserService userService;

    @Autowired
    GroupService groupService;
    @Autowired
    GroupRepository groupRepository;

    @Test
    public void 그룹_추가() throws Exception{
        //given
        String userId = userService.login("userA");

        Group group = new Group();
        group.setName("groupA");

        //when
        Long groupId = groupService.create(group, userId);

        //then
        assertThat(groupRepository.findById(groupId).get().getName()).isEqualTo("groupA");
    }

    @Test
    public void 그룹_추가_예외() throws Exception{
        //given
        String userId = userService.login("userA");

        Group group1 = new Group();
        group1.setName("groupA");

        Group group2 = new Group();
        group2.setName("groupA");

        //when
        groupService.create(group1, userId);
        IllegalStateException e = assertThrows(IllegalStateException.class,
            () -> groupService.create(group2, userId));

        //then
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 그룹 이름입니다.");
    }

    @Test
    public void 그룹명_변경() throws Exception{
        //given
        String userId = userService.login("userA");

        Group group = new Group();
        group.setName("groupA");
        Long groupId = groupService.create(group, userId);

        //when
        groupService.changeName(groupId, "newName");

        //then
        assertThat(groupRepository.findById(groupId).get().getName()).isEqualTo("newName");
    }

    @Test
    public void 그룹_삭제() throws Exception{
        //given
        String userId = userService.login("userA");

        Group group = new Group();
        group.setName("groupA");
        Long groupId = groupService.create(group, userId);

        //when
        groupService.delete(groupId);

        //then
        assertThat(groupRepository.findById(groupId).isEmpty()).isEqualTo(true);
    }

}