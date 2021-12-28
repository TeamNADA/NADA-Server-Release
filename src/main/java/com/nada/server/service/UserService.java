package com.nada.server.service;

import com.nada.server.commons.RedisUtil;
import com.nada.server.commons.SecurityUtil;
import com.nada.server.domain.Authority;
import com.nada.server.domain.Group;
import com.nada.server.domain.User;
import com.nada.server.dto.payload.TokenDTO;
import com.nada.server.exception.CustomException;
import com.nada.server.constants.ErrorCode;
import com.nada.server.jwt.TokenProvider;
import com.nada.server.repository.CardGroupRepository;
import com.nada.server.repository.GroupRepository;
import com.nada.server.repository.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final TokenProvider tokenProvider;

    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    private final CardGroupRepository cardGroupRepository;
    private final RedisUtil redisUtil;

    /**
     * 로그인
     * 등록되어 있지 않으면 자동 회원가입 처리
     */
    @Transactional
    public TokenDTO login(String id){

        Optional<User> user = userRepository.findById(id);
        if(!user.isPresent()) {
            this.register(id);
        }

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(new UsernamePasswordAuthenticationToken(id, ""));
        TokenDTO tokenDTO = tokenProvider.generateTokenDTO(authentication);

        return tokenDTO;
    }

    /**
     * 로그아웃
     */
    public void logout(String id){
        userRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.UNAUTHORIZED_USER));
        redisUtil.deleteData(id);
    }

    /**
     * 회원가입
     */
    @Transactional
    public User register(String id){
        User user = new User();
        user.setId(id);
        user.setAuthority(Authority.ROLE_USER);

        User saveUser = userRepository.save(user);

        Group group = new Group();
        group.setUser(saveUser);
        group.setName("미분류");
        groupRepository.save(group);

        return saveUser;
    }

    /**
     * 회원 탈퇴 - 존재하지 않는 유저일 땐 에러
     */
    @Transactional
    public void unsubscribe(String id){
        User user = userRepository.findById(id)
            .orElseThrow(() -> new CustomException(ErrorCode.UNAUTHORIZED_USER));
        cardGroupRepository.deleteByUser(user);
        userRepository.deleteById(id);
        redisUtil.deleteData(id);
    }

    /**
     * access token, refresh token 재발급
     */
    public TokenDTO reissue(String accessToken, String refreshToken){

        try{
            tokenProvider.validateToken(refreshToken);
        } catch(Exception e){
            throw new CustomException(ErrorCode.EXPIRED_REFRESH_TOKEN);
        }

        Authentication authentication = tokenProvider.getAuthentication(accessToken);

        String rt = redisUtil.getData( authentication.getName())
            .orElseThrow(() -> new CustomException(ErrorCode.LOGOUT_USER));

        if(!rt.equals(refreshToken)){
            throw new CustomException(ErrorCode.INVALID_REFRESH_TOKEN);
        }

        TokenDTO tokenDTO = tokenProvider.generateTokenDTO(authentication);

        return tokenDTO;
    }

}
