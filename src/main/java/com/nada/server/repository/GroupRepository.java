package com.nada.server.repository;

import com.nada.server.domain.Group;
import com.nada.server.domain.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Group, Long> {

    List<Group> findByUser(User user); // 그룹 리스트 조회
    List<Group> findByNameAndUser_Id(String groupName, String userId); // 그룹 중복 검사

}
