package com.nada.server.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.nada.server.domain.User;
import com.nada.server.repository.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class UserServiceTest {
    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Test
    public void 로그인_에러() throws Exception{
        //given

        //when
        IllegalStateException e = assertThrows(IllegalStateException.class,
            () -> userService.login("userA"));

        //then
        assertThat(e.getMessage()).isEqualTo("회원 가입을 진행해주세요!.");

    }

    @Test
    public void 회원가입() throws Exception{
        //given
        String userId = userService.register("userA");

        //when
        String loginId = userService.login(userId);

        //then
        assertThat(loginId).isEqualTo(userId);
    }

    @Test
    public void 회원_탈퇴() throws Exception{
        //given
        String userId = userService.login("userA");

        //when
        userService.unsubscribe(userId);

        //then
        assertThat(userRepository.findById(userId).isEmpty()).isEqualTo(true);
    }
}