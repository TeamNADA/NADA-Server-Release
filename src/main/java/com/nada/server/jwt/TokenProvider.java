package com.nada.server.jwt;

import com.nada.server.commons.RedisUtil;
import com.nada.server.commons.SecurityUtil;
import com.nada.server.constants.ErrorCode;
import com.nada.server.dto.payload.TokenDTO;
import com.nada.server.exception.CustomException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

// JWT 토큰에 관련된 암호화, 복호화, 검증 로직
@Slf4j
@Component
public class TokenProvider {
    private static final String AUTHORITIES_KEY = "auth";
    private static final String BEARER_TYPE = "bearer";
    private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 14;            // 5min
    private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 2; //30;  // 30min

    private final Key key;
    private final RedisUtil redisUtil;

    public TokenProvider(@Value("${jwt.secret}") String secretKey, RedisUtil redisUtil){
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.redisUtil = redisUtil;
    }

    // Access Token 과 Refresh Token 을 생성
    public TokenDTO generateTokenDTO(Authentication authentication){
        // 권한 가져오기
        String authorities = authentication.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.joining(","));

        Long now = (new Date()).getTime();

        // AT 생성 -> 유저 역할, 이름
        Date accessTokenExpiresIn = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);
        String accessToken = Jwts.builder()
            .setSubject(authentication.getName())       // payload "sub": "name"
            .claim(AUTHORITIES_KEY, authorities)        // payload "auth": "ROLE_USER"
            .setExpiration(accessTokenExpiresIn)        // payload "exp": 1516239022 (예시)
            .signWith(key, SignatureAlgorithm.HS512)    // header "alg": "HS512"
            .compact();

        // RT 생성 -> 단순 생성 날짜
        String refreshToken = Jwts.builder()
            .setExpiration(new Date(now + REFRESH_TOKEN_EXPIRE_TIME))
            .signWith(key, SignatureAlgorithm.HS512)
            .compact();

        redisUtil.setDateExpire(authentication.getName(), refreshToken,REFRESH_TOKEN_EXPIRE_TIME);

        return TokenDTO.builder()
            .grantType(BEARER_TYPE)
            .accessToken(accessToken)
            .accessTokenExpiresIn(accessTokenExpiresIn.getTime())
            .refreshToken(refreshToken)
            .build();
    }

    // 직접 DB 를 조회한 것이 아니라 Access Token 에 있는 Member ID 를 꺼낸 것
    // 탈퇴로 인해 Member ID 가 DB 에 없는 경우 등 예외 상황은 Service 단에서 고려
    public Authentication getAuthentication(String accessToken) {

        // 토큰 속 내용(정보) 꺼내기
        Claims claims = parseClaims(accessToken);

        if (claims.get(AUTHORITIES_KEY) == null) {
            throw new RuntimeException("권한 정보가 없는 토큰입니다.");
        }

        // 클레임에서 권한 정보 가져오기
        Collection<? extends GrantedAuthority> authorities =
            Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        // SecurityContext 사용 위함
        UserDetails principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    // 토큰 정보 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch(Exception e){
            throw e;
        }
    }

    private Claims parseClaims(String accessToken){
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

}
