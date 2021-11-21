package com.nada.server.service;

import com.nada.server.domain.Authority;
import com.nada.server.domain.Group;
import com.nada.server.domain.RefreshToken;
import com.nada.server.domain.User;
import com.nada.server.dto.payload.TokenDTO;
import com.nada.server.exception.CustomException;
import com.nada.server.constants.ErrorCode;
import com.nada.server.jwt.TokenProvider;
import com.nada.server.repository.GroupRepository;
import com.nada.server.repository.RefreshTokenRepository;
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
    private final RefreshTokenRepository refreshTokenRepository;

    private final UserRepository userRepository;
    private final GroupRepository groupRepository;

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

        // 4. RefreshToken 저장
        RefreshToken refreshToken = RefreshToken.builder()
            .key(authentication.getName())
            .value(tokenDTO.getRefreshToken())
            .build();

        RefreshToken save = refreshTokenRepository.save(refreshToken);

        return tokenDTO;
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
       userRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.UNAUTHORIZED_USER));
       userRepository.deleteById(id);
    }


    public TokenDTO reissue(String accessToken, String refreshToken){
        if(!tokenProvider.validateToken(refreshToken)){
            throw new CustomException(ErrorCode.EXPIRED_REFRESH_TOKEN);
        }

        Authentication authentication = tokenProvider.getAuthentication(accessToken);

        RefreshToken rt = refreshTokenRepository.findByKey(authentication.getName())
            .orElseThrow(() -> new CustomException(ErrorCode.LOGOUT_USER));

        if(!rt.getValue().equals(refreshToken)){
            throw new CustomException(ErrorCode.INVALID_REFRESH_TOKEN);
        }

        TokenDTO tokenDTO = tokenProvider.generateTokenDTO(authentication);

        // 6. 저장소 정보 업데이트
        RefreshToken newRefreshToken = rt.updateValue(tokenDTO.getRefreshToken());
        refreshTokenRepository.save(newRefreshToken);

        return tokenDTO;
    }

}
