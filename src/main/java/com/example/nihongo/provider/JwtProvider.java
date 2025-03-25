package com.example.nihongo.provider;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

/**
 * class: JWT 토큰 생성 및 검증을 담당하는 제공자 클래스입니다.
 * <p>
 * - 서명 알고리즘: HS256  
 * - 비밀 키: application.properties 또는 환경변수의 {@code jwt.secret}  
 * - 토큰 유효기간: 9시간  
 * </p>
 */
@Component
public class JwtProvider {
  /**
   * description: JWT 서명에 사용되는 비밀 키
   */
  @Value("${jwt.secret}")
  private String secretKey;

  /**
   * function: JWT 생성 메서드
   * <p>
   * <ul>
   *   <li>{@code sub} (Subject): 사용자의 이메일</li>
   *   <li>{@code iat} (Issued At): 토큰 생성 시간</li>
   *   <li>{@code exp} (Expiration): 생성 시간 기준 9시간 후</li>
   * </ul>
   * </p>
   *
   * @param email 토큰에 포함할 사용자 이메일
   * @return 생성된 JWT 문자열
   */
  public String create(String email) {

    Date expiration = Date.from(Instant.now().plus(9, ChronoUnit.HOURS));

    Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

    String jwt = null;

    try {

      jwt = Jwts.builder()
        .signWith(key, SignatureAlgorithm.HS256)
        .setSubject(email)
        .setIssuedAt(new Date())
        .setExpiration(expiration)
        .compact();

    } catch (Exception exception) {
      exception.printStackTrace();
    }

    return jwt;

  }

  /**
   * function: JWT 검증 메서드 
   * description: 유효한 경우 사용자 이메일(subject)을 반환합니다.
   * 
   * @param jwt 클라이언트로부터 받은 JWT 문자열
   * @return 유효한 토큰이면 이메일, 아니면 {@code null}
   */
  public String validate(String jwt) {

    String email = null;

    Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

    try {

      email = Jwts.parserBuilder()
        .setSigningKey(key)
        .build()
        .parseClaimsJws(jwt)
        .getBody()
        .getSubject();

    } catch(Exception exception) {
      exception.printStackTrace();
    }

    return email;

  }

}