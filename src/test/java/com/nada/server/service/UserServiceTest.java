package com.nada.server.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.nada.server.domain.User;
import com.nada.server.repository.UserRepository;
import java.util.Optional;
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
    public void 로그인() throws Exception{
        //given
        String userId = userService.login("userA");

        //when
        Optional<User> findUser = userRepository.findById(userId);

        //then
        assertThat(findUser.get().getId()).isEqualTo("userA");
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