package com.example.stock_service_system.jwt;

import com.example.stock_service_system.entity.UserInfo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Collections;
import java.util.Date;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class TokenProvider {

    private final JwtProperties jwtProperties;

    public String generateToken(UserInfo userInfo , Duration expiredAt) {
        Date now = new Date();
        return makeToken(new Date(now.getTime()+ expiredAt.toMillis()), userInfo);
    }

    private String makeToken(Date expiry , UserInfo user) {
        Date now = new Date();

        return Jwts.builder()
                .setHeaderParam(Header.TYPE , Header.JWT_TYPE) //헤더 typ : JWT
                .setIssuer(jwtProperties.getIssuer()) //내용 iss : jaeil
                .setIssuedAt(now) // 내용 iat : 현재 시간
                .setExpiration(expiry) // 내용 exp : expiry 멤버 변수 값
                .setSubject(user.getId()) // 내용 sub : 유저의 아이디
                .claim("id",user.getSeq()) // 클레임 id : 유저의 serq
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecretKey())
                .compact();

    }

    public boolean validToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(jwtProperties.getSecretKey()) //비밀값으로 복호화
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    //토큰 기반으로 인증 정보를 가져오는 메소드
    public Authentication getAuthentication(String token) {
        Claims claims = getClaims(token);
        Set<SimpleGrantedAuthority> authorities =
                Collections.singleton(
                        new SimpleGrantedAuthority("ROLE_USER"));
        // 아래에서 User는 Spring Security에 있는 User 클래스
        return new UsernamePasswordAuthenticationToken(
                new User(claims.getSubject(),"",authorities),
                token,
                authorities);

    }

    public Long getUserId(String token) {
        Claims claims = getClaims(token);
        return claims.get("id",Long.class);
    }
    private Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(jwtProperties.getSecretKey())
                .parseClaimsJws(token)
                .getBody();
    }
}
