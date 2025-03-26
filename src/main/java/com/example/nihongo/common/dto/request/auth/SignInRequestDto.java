package com.example.nihongo.common.dto.request.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * class: 로그인 요청 시 사용되는 DTO입니다.
 * 
 * description: 사용자가 입력한 이메일과 비밀번호를 포함하며, 유효성 검사(@Email, @NotBlank)를 통해 형식 오류를 사전에 방지합니다.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SignInRequestDto {

  /**
   * description: 사용자 이메일 주소
   * 
   * <p>
   * - 공백 불가 {@link NotBlank}  
   * - 이메일 형식 검사 {@link Email}
   * </p>
   */
  @Email
  @NotBlank
  private String email;

  /**
   * description: 사용자 비밀번호
   * 
   * <p>
   * - 공백 불가 {@link NotBlank}
   * </p>
   */
  @NotBlank
  private String password;
}