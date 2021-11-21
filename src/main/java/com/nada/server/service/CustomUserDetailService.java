package com.nada.server.service;

import com.nada.server.repository.UserRepository;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    // 유저 디테일 객체와 authentication 객체를 이용해 비밀번호 검증
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException{
        return  userRepository.findById(id)
            .map(this::createUserDetails)
            .orElseThrow(() -> new UsernameNotFoundException((id+ "-> DB에서 찾을 수 없습니다.")));
    }

    // DB에 멤버값이 존재하면 user detail객체로 만들어 리턴
    private UserDetails createUserDetails(com.nada.server.domain.User userA) {

        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(
            userA.getAuthority().toString());

        return new User(
            userA.getId(),
            "{noop}",
            Collections.singleton((grantedAuthority))
        );
    }

}