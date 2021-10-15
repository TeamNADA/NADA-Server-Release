package com.nada.server.service;

import com.nada.server.domain.User;
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

    /**
     * 로그인/회원가입
     * DB에 유저가 등록되어있는지 확인
     * 1) 등록O -> 로그인 완료
     * 2) 등록X -> 등록 시키고 로그인
     */
    @Transactional
    public String login(String id){
        Optional<User> findUser = userRepository.findById(id);

        if(findUser.isPresent()){
            // 1) 등록된 회원이라면
            return findUser.get().getId();
        }else{
            // 2) 등록되지 않은 회원이라면
            User user = new User();
            user.setId(id);
            User saveUser = userRepository.save(user);
            return saveUser.getId();
        }
    }

    /**
     * 회원 탈퇴
     */
    @Transactional
    public void unsubscribe(String id){
        userRepository.deleteById(id);
    }



}
