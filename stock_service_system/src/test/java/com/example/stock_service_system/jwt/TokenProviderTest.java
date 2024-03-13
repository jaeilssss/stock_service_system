package com.example.stock_service_system.jwt;

import com.example.stock_service_system.entity.UserInfo;
import com.example.stock_service_system.repository.UserRepository;
import com.mysql.cj.log.Log;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Duration;
import java.util.Date;
import java.util.Map;

@SpringBootTest
public class TokenProviderTest {

    @Autowired
    private TokenProvider tokenProvider;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtProperties jwtProperties;

    @DisplayName("generateToken() : 유저 정보와 만료 기간을 전달해 토큰을 만들 수 있다.")
    @Test
    void generateToken() {
        //given
        UserInfo testUser = userRepository.save(UserInfo.builder()
                .id("jaeilssss")
                .name("jaeil")
                .nickName("gdgd")
                .password("1234")
                .seq(111L)
                .build()
        );
        //when
        String token = tokenProvider.generateToken(testUser , Duration.ofDays(14));
        System.out.println(token);
        //then
        Long userId = Jwts.parser()
                .setSigningKey(jwtProperties.getSecretKey())
                .parseClaimsJws(token)
                .getBody()
                .get("id",Long.class);
        System.out.println(testUser.getSeq());
        System.out.println(userId);
    }

    //validToken() 검증 테스트
    @DisplayName("validToken() : 만료된 토큰인 때에 유효성 검증에 실패한다.")
    @Test
    void validToken_invalidToken() {
        //given
        String token = JwtFactory.builder()
                .expiration(new Date(new Date().getTime() - Duration.ofDays(7).toMillis()))
                .build()
                .createToken(jwtProperties);

        //when
        boolean result = tokenProvider.validToken(token);
        //then
        System.out.println(result);
    }

    //getAuthentication() 검증 테스트
    @DisplayName("getAuthentication() : 토큰 기반으로 인증 정보를 가져올 수 있다.")
    @Test
    void getAuthentication() {
        // given
        String id = "test12121";
        String token = JwtFactory.builder()
                .subject(id)
                .build()
                .createToken(jwtProperties);
        //when
        Authentication authentication = tokenProvider.getAuthentication(token);

        System.out.println("23123232");
        System.out.println(((UserDetails) authentication.getPrincipal()).getAuthorities());
        System.out.println(((UserDetails) authentication.getPrincipal()).getUsername());
    }

    @DisplayName("getUserId() : 토큰으로 유저 ID를 가져옿ㄹ 수 있다")
    @Test
    void getUserId() {
        //given
        Long userId = 1L;
        String token = JwtFactory.builder()
                .claims(Map.of("id", userId))
                .build()
                .createToken(jwtProperties);

        //when
        Long userIdByToken = tokenProvider.getUserId(token);

        //then
        System.out.println(userIdByToken);
    }
}
