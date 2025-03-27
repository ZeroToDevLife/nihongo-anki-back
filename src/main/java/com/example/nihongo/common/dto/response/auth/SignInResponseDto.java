package com.example.nihongo.common.dto.response.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.nihongo.common.dto.response.ResponseDto;
import com.example.nihongo.common.dto.response.ResponseType;

import lombok.Getter;
import lombok.Setter;

/**
 * class: 로그인 성공 시 반환되는 응답 DTO입니다.
 * 
 * description: 기본 응답 정보(code, message), 인증 토큰(accessToken), 토큰 만료 시간(expiration)을 포함합니다.
 */
@Getter
@Setter
public class SignInResponseDto extends ResponseDto {

  /** 
   * description: 액세스 토큰 (JWT 등) 
   */
  private String accessToken;

  /** 
   * description: 토큰 유효 시간 (초 단위)
   */
  private Integer expiration;

  private boolean isVerified;

  /**
   * description: 응답 객체를 생성합니다.
   *
   * @param accessToken 클라이언트에게 발급할 액세스 토큰
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
   * @param accessToken 발급할 액세스 토큰
   * @return 200 OK 상태의 로그인 응답
   */
  public static ResponseEntity<SignInResponseDto> success(String accessToken, boolean isVerified) {
    SignInResponseDto body = new SignInResponseDto(accessToken, isVerified);
    return ResponseEntity.status(HttpStatus.OK).body(body);
  }
}