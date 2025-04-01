package com.example.nihongo.common.dto.response.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.nihongo.common.dto.response.ResponseDto;
import com.example.nihongo.common.dto.response.ResponseType;

import lombok.Getter;

/**
 * class: 이메일 인증 응답 DTO
 * 
 * description: 이메일 인증 요청에 대한 응답을 처리하는 DTO입니다. 기본 응답 정보({@code code}, {@code message})와 함께 인증 성공 여부를 포함합니다.
 */
@Getter
public class EmailAuthResponseDto extends ResponseDto {

  /**
   * description: 이메일 인증 성공 여부
   * 
   * <p>
   * <ul>
   * <li>{@code true}: 이메일 인증 성공</li>
   * <li>{@code false}: 이메일 인증 실패</li>
   * </ul>
   * </p>
   */
  private boolean isVerified;

  /**
   * description: 이메일 인증 응답 객체를 생성합니다.
   *
   * @param isVerified 이메일 인증 성공 여부
   */
  private EmailAuthResponseDto(boolean isVerified) {
    super(ResponseType.SUCCESS);
    this.isVerified = isVerified;
  }

  /**
   * description: 이메일 인증 성공 응답을 생성합니다.
   *
   * @param isVerified 이메일 인증 성공 여부
   * @return {@link HttpStatus#OK} (200) 상태의 이메일 인증 응답
   */
  public static ResponseEntity<EmailAuthResponseDto> success(boolean isVerified) {
    EmailAuthResponseDto body = new EmailAuthResponseDto(isVerified);
    return ResponseEntity.status(HttpStatus.OK).body(body);
  }
}
