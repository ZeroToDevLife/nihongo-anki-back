package com.example.nihongo.common.dto.response.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.nihongo.common.dto.response.ResponseDto;
import com.example.nihongo.common.dto.response.ResponseType;

import lombok.Getter;
import lombok.Setter;

/**
 * class: 로그인 응답 DTO
 * 
 * description: 로그인 성공 시 반환되는 응답 DTO입니다. 기본 응답 정보({@code code}, {@code message})와 함께 인증 토큰(accessToken), 토큰 만료 시간(expiration), 이메일 인증 여부(isVerified)를 포함합니다.
 */
@Getter
@Setter
public class SignInResponseDto extends ResponseDto {

  /** 
   * description: 액세스 토큰
   * 
   * <p>
   * <ul>
   * <li>JWT 형식의 인증 토큰</li>
   * <li>API 요청 시 Authorization 헤더에 포함되어야 함</li>
   * <li>Bearer 인증 방식 사용 ({@code Bearer <token>})</li>
   * </ul>
   * </p>
   */
  private String accessToken;

  /** 
   * description: 토큰 유효 시간
   * 
   * <p>
   * <ul>
   * <li>단위: 초</li>
   * <li>기본값: 9시간 ({@code 32,400}초)</li>
   * </ul>
   * </p>
   */
  private Integer expiration;

  /**
   * description: 이메일 인증 완료 여부
   * 
   * <p>
   * <ul>
   * <li>{@code true}: 이메일 인증 완료</li>
   * <li>{@code false}: 이메일 인증 미완료</li>
   * </ul>
   * </p>
   */
  private boolean isVerified;

  /**
   * description: 로그인 응답 객체를 생성합니다.
   *
   * @param accessToken 클라이언트에게 발급할 JWT 액세스 토큰
   * @param isVerified 이메일 인증 완료 여부
   */
  public SignInResponseDto(String accessToken, boolean isVerified) {
    super(ResponseType.SUCCESS);
    this.accessToken = accessToken;
    this.expiration = 60 * 60 * 9; // 9시간 (초 단위)
    this.isVerified = isVerified;
  }

  /**
   * description: 로그인 성공 응답을 생성합니다.
   *
   * @param accessToken 발급할 JWT 액세스 토큰
   * @param isVerified 이메일 인증 완료 여부
   * @return {@link HttpStatus#OK} (200) 상태의 로그인 응답
   */
  public static ResponseEntity<SignInResponseDto> success(String accessToken, boolean isVerified) {
    SignInResponseDto body = new SignInResponseDto(accessToken, isVerified);
    return ResponseEntity.status(HttpStatus.OK).body(body);
  }
}