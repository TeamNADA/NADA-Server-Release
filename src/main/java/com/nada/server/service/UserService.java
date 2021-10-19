package com.nada.server.service;

import com.nada.server.domain.Group;
import com.nada.server.domain.User;
import com.nada.server.exception.CustomException;
import com.nada.server.exception.ErrorCode;
import com.nada.server.repository.GroupRepository;
import com.nada.server.repository.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final GroupRepository groupRepository;

    /**
     * 로그인
     * 등록되어 있지 않으면 회원가입 진행 요구
     */
    public String login(String id){
        User findUser = userRepository.findById(id)
            .orElseThrow(() -> new CustomException(ErrorCode.UNAUTHORIZED_USER));

        return findUser.getId();
    }

    /**
     * 회원가입
     * 그룹 "미분류"도 default로 생성시킵니다.
     */
    @Transactional
    public String register(User user){
        User saveUser = userRepository.save(user);

        Group group = new Group();
        group.setUser(saveUser);
        group.setName("미분류");
        groupRepository.save(group);

        return saveUser.getId();
    }

    /**
     * 회원 탈퇴
     */
    @Transactional
    public void unsubscribe(String id){
        userRepository.deleteById(id);
    }



}
