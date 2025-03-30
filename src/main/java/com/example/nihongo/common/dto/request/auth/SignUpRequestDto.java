package com.example.nihongo.common.dto.request.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * class: 회원가입 요청 시 사용되는 DTO입니다.
 * 
 * description: 사용자가 입력한 이메일, 비밀번호, 닉네임을 포함하며, 유효성 검사(@Email, @Pattern, @NotBlank)를 통해 형식 오류를 사전에 방지합니다.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequestDto {

  /**
   * description: 사용자 이메일 주소
   * 
   * <p>
   * - 공백 불가 {@link NotBlank}  
   * - 이메일 형식 검사 {@link Email}
   * </p>
   */
  @NotBlank
  @Email
  private String email;

  /**
   * description: 사용자 비밀번호
   * 
   * <p>
   * - 공백 불가 {@link NotBlank}
   * - 8자 이상, 영문, 숫자 포함 {@link Pattern}
   * </p>
   */
  @NotBlank
  @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*])[a-zA-Z\\d!@#$%^&*]{8,20}$")
  private String password;

  /**
   * description: 사용자 닉네임
   * 
   * <p>
   * - 공백 불가 {@link NotBlank}
   * - 3자 이상, 영문, 숫자 포함 {@link Pattern}
   * </p>
   */
  @NotBlank
  @Pattern(regexp = "^[a-zA-Z\\d]{3,20}$")
  private String nickname;
}
