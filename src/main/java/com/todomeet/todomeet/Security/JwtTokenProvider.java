package com.todomeet.todomeet.Security;


import com.todomeet.todomeet.dto.JwtAuthDto;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;

import java.util.Base64;
import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    @Value("${app.jwt.secret}")
    private String jwtSecret;

    @Value("${app.jwt.accessTokenExpiration.milliseconds}")
    private long jwtAccessTokenExpiration;

    @Value("${app.jwt.refreshTokenExpiration.milliseconds}")
    private long jwtRefreshTokenExpiration;

    private final CustomUserDetailsService customUserDetailsService;

    private Key key;

    @PostConstruct
    protected void init() {
        this.jwtSecret  = Base64.getEncoder().encodeToString(jwtSecret.getBytes());
        this.key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(jwtSecret));

    }
    //토큰에서 userEmail 추출
    public String getUserEmail(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }


    // JWT 토큰 생성
    // JWT 토큰 생성
    public JwtAuthDto generateTokens(Authentication authentication) {
        String username = authentication.getName();

        // Access Token 생성
        Claims accessTokenClaims = Jwts.claims().setSubject(username);
        accessTokenClaims.put("roles", authentication.getAuthorities());
        Date currentDateTime = new Date();
        Date accessTokenExpiration = new Date(currentDateTime.getTime() + jwtAccessTokenExpiration);
        String accessToken = Jwts.builder()
                .setClaims(accessTokenClaims)
                .setIssuedAt(currentDateTime)
                .setExpiration(accessTokenExpiration)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        // Refresh Token 생성
        Claims refreshTokenClaims = Jwts.claims().setSubject(username);
        refreshTokenClaims.put("roles", authentication.getAuthorities());
        Date refreshTokenExpiration = new Date(currentDateTime.getTime() + jwtRefreshTokenExpiration);
        String refreshToken = Jwts.builder()
                .setClaims(refreshTokenClaims)
                .setIssuedAt(currentDateTime)
                .setExpiration(refreshTokenExpiration)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        return new JwtAuthDto(accessToken, refreshToken);
    }

    // jwt 토큰 검증
    public boolean validateToken(String token){
        if(token!=null) {
            try {
                log.info("=============토큰검증 시작");
                Jwts.parserBuilder()
                        .setSigningKey(key)
                        .build()
                        .parse(token);
                log.info("=============토큰검증 성공");
                return true;
            } catch (MalformedJwtException ex) {
                log.error("잘못된 토큰");
            } catch (SignatureException ex) {
                log.error("유효하지 않은 JWT signature");
            } catch (ExpiredJwtException ex) {
                log.error("만료된 토큰");
            } catch (UnsupportedJwtException ex) {
                log.error("지원하지 않는 토큰");
            } catch (IllegalArgumentException ex) {
                log.error("유효하지 않은 토큰");
            }
            return false;
        }
        else{
            throw new AccessDeniedException("권한이 없습니다.");
        }
    }

    // JWT 토큰에서 인증 정보 조회
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(this.getUserEmail(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());

    }
}
