package com.example.nihongo.filter;

import java.io.IOException;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.nihongo.provider.JwtProvider;
import com.example.nihongo.repository.UserRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

/**
 * class: JWT Bearer Token 인증 처리를 위한 필터입니다.
 * <p>
 * description: 요청 헤더에서 JWT 토큰을 추출하고 유효성을 검증한 후, 인증 정보(email)를 Spring Security의 SecurityContext에 주입합니다.
 * </p>
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final JwtProvider jwtProvider;
  private final UserRepository userRepository;

  /**
   * function: JWT 토큰을 추출하고 유효한 경우 인증 컨텍스트를 설정합니다.
   *
   * @param request     HTTP 요청
   * @param response    HTTP 응답
   * @param filterChain 필터 체인
   * @throws ServletException
   * @throws IOException
   */
  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

    try {

      String token = getToken(request);
      if (token == null) {
        filterChain.doFilter(request, response);
        return;
      }

      String email = jwtProvider.validate(token);
      if (email == null) {
        filterChain.doFilter(request, response);
        return;
      }

      boolean existemail = userRepository.existsByEmail(email);
      if (!existemail) {
        filterChain.doFilter(request, response);
        return;
      }

      setContext(email, request);

    } catch(Exception exception) {
      exception.printStackTrace();
    }

    filterChain.doFilter(request, response);

  }

  /**
   * function: 요청의 Authorization 헤더에서 JWT 토큰을 추출합니다.
   *
   * @param request HTTP 요청
   * @return Bearer 토큰 문자열, 없으면 {@code null}
   */
  private String getToken(HttpServletRequest request) {

    // description: Request 객체에서 Authorization header 값 추출 //
    String authorization = request.getHeader("Authorization");
    boolean hasAuthorization = StringUtils.hasText(authorization);
    if (!hasAuthorization) return null;

    // description: Bearer 인증 방식인지 확인 //
    boolean isBearer = authorization.startsWith("Bearer ");
    if (!isBearer) return null;

    // description: Authorization 필드 값에서 Token 추출 //
    String token = authorization.substring(7);
    return token;

  }

  /**
   * function: 인증된 사용자의 이메일을 기반으로 SecurityContext를 설정합니다.
   *
   * @param email   인증된 사용자 이메일
   * @param request HTTP 요청
   */
  private void setContext(String email, HttpServletRequest request) {

    /**
     * description: 접근 주체의 정보가 담길 인증 토큰 생성 
     */
    AbstractAuthenticationToken authenticationToken =
      new UsernamePasswordAuthenticationToken(email, null, AuthorityUtils.NO_AUTHORITIES);

    /**
     * description: 생성한 인증 토큰이 어떤 요청의 정보인지 상세 내역 추가
     */
    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

    /**
     * description: 빈 Security Context 생성
     */
    SecurityContext securityContext = SecurityContextHolder.createEmptyContext();

    /**
     * description: 생성한 Security Context에 접근 주체 정보 주입
     */
    securityContext.setAuthentication(authenticationToken);

    /**
     * description: 생성한 Security Context 등록
     */
    SecurityContextHolder.setContext(securityContext);

  }
  
}