package com.paymentsideproject.security;


import com.paymentsideproject.domain.customer.enums.Rank;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {

    public static final String BEARER_PREFIX = "Bearer ";
    private static final String ROLE_CLAIM = "role";
    private static final long TOKEN_TIME = 60 * 60 * 1000L;


    @Value("${jwt.secret.key}")
    private String secretKey;


    private SecretKey key;

    // key → JWT 서명/검증에 실제로 사용하는 변환된 키 객체
    @PostConstruct
    public void init() {
        key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }


    // 토큰 발행
    public String createToken(String name, String email, String password, Rank rank) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + TOKEN_TIME);

        return Jwts.builder()
                .setSubject(name)
                .claim("email", email)
                .claim("password", password)
                .claim(ROLE_CLAIM, "PENDING")
                // 이 토큰이 언제 발급됐는지.
                .setIssuedAt(now)
                // 이 토큰이 언제 만료되는지.
                .setExpiration(expiry)
                // 검증에 사용하는 토큰.
                .signWith(key)
                // compact = 최종 토큰 문자열 생성
                .compact();
    }


    // Authorization 헤더에서 "Bearer " 접두사를 제거하고 순수 JWT 토큰만 추출
    public String substringToken(String tokenValue) {
        if (tokenValue != null && tokenValue.startsWith(BEARER_PREFIX)) {
            return tokenValue.substring(7);
        }
        throw new IllegalArgumentException("Bearer 토큰이 아닙니다.");
    }


    // JWT를 파싱해서 내부 claims(subject, role, email 등) 정보를 꺼냄
    public Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // 토큰의 subject 값 조회
// 현재 setSubject(name)으로 만들었다면 사용자 이름을 반환
    public String getUserName(String token) {
        return extractClaims(token).getSubject();
    }

    // 토큰에 저장한 role claim 값 조회
    public String getRole(String token) {
        return extractClaims(token).get(ROLE_CLAIM, String.class);
    }


    // 토큰이 정상 서명되었는지, 만료되지 않았는지 검증
// 문제가 없으면 true, 서명 오류/만료/형식 오류가 있으면 false 반환
    public boolean validateToken(String token) {
        try {
            extractClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}