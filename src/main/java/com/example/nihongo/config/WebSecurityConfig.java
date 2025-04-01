package com.example.nihongo.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.example.nihongo.filter.JwtAuthenticationFilter;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

/**
 * class: Spring Security 설정 클래스입니다.
 * 
 * description: JWT 인증 기반의 REST API 보안을 구성합니다. Basic 인증 비활성화, 세션 사용 안 함(Stateless), CSRF 비활성화, CORS 허용 등을 포함합니다.
 */
@Configurable
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

  private final JwtAuthenticationFilter jwtAuthenticationFilter;

  /** 
   * description: Spring Security 필터 체인을 구성합니다.
   *
   * <p>
   * <ul>
   * <li>Basic 인증 비활성화</li>
   * <li>세션 비활성화 (Stateless)</li>
   * <li>CSRF 비활성화</li>
   * <li>CORS 설정 적용</li>
   * <li>인가(Authorization) 설정</li>
   * <li>인증 실패 처리 설정</li>
   * <li>JWT 필터 등록</li>
   * </ul>
   * </p>
   *
   * @param security {@link HttpSecurity} 설정 객체
   * @return 구성된 {@link SecurityFilterChain}
   * @throws Exception 보안 설정 중 예외 발생 시
   */
  @Bean
  protected SecurityFilterChain configure(HttpSecurity security) throws Exception {

    security
      // description: Basic 인증 비활성화
      .httpBasic(HttpBasicConfigurer::disable)
      // description: 세션 비활성화 (Stateless)
      .sessionManagement(management -> management
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
      )
      // description: CSRF 비활성화
      .csrf(CsrfConfigurer::disable)
      // description: CORS 설정 적용
      .cors(cors -> cors.configurationSource(corsConfigurationSource()))
      // description: 인가(Authorization) 설정
      .authorizeHttpRequests(request -> request
        .requestMatchers("/api/v1/auth", "/api/v1/auth/**").permitAll()
        .anyRequest().authenticated()
      )
      // description: 인증 실패 처리 설정
      .exceptionHandling(exception -> exception
        .authenticationEntryPoint(new AuthenticationFailEntryPoint())
      )
      // description: JWT 필터 등록 (기존 UsernamePasswordAuthenticationFilter 앞에 배치)
      .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

    return security.build();
  }

  /**
   * description: CORS 설정을 반환합니다.
   *
   * <p>
   * <ul>
   * <li>모든 헤더 허용 ({@code *})</li>
   * <li>모든 메서드 허용 ({@code *})</li>
   * <li>모든 출처 허용 ({@code *})</li>
   * </ul>
   * </p>
   *
   * @return {@link CorsConfigurationSource} CORS 설정 소스
   */
  @Bean
  protected CorsConfigurationSource corsConfigurationSource() {

    CorsConfiguration configuration = new CorsConfiguration();
    configuration.addAllowedHeader("*");
    configuration.addAllowedMethod("*");
    configuration.addAllowedOrigin("*");

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);

    return source;
  }
}

/**
 * class: 인증 실패 시 호출되는 엔트리포인트입니다.
 * 
 * description: 인증되지 않은 요청이 들어올 경우, JSON 형식의 오류 메시지를 반환합니다. 응답 형식: {@code { "code": "AF", "message": "Auth Fail." }}
 */
class AuthenticationFailEntryPoint implements AuthenticationEntryPoint {

  /**
   * description: 인증 실패 처리 메서드
   *
   * @param request 인증 실패 요청
   * @param response 인증 실패 응답 ({@link HttpServletResponse#SC_UNAUTHORIZED} 401)
   * @param authException 인증 예외
   * @throws IOException
   * @throws ServletException
   */
  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response,AuthenticationException authException) throws IOException, ServletException {

    authException.printStackTrace();

    response.setContentType("application/json");
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    response.getWriter().write("{ \"code\": \"AF\", \"message\": \"Auth Fail.\" }");
  }

}