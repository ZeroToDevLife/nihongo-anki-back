package com.example.nihongo.common.dto.request.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * class: 로그인 요청 DTO
 * 
 * description: 사용자의 로그인 요청 정보를 담는 DTO입니다.
 * {@link org.springframework.security.authentication.AuthenticationManager}에서 인증 처리 시 사용됩니다.
 * 
 * <p>유효성 검증 실패 시 {@link com.example.nihongo.handler.CustomExceptionHandler#validExceptionHandler}에서
 * {@code validation fail} 응답을 반환합니다.</p>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SignInRequestDto {

    /**
     * description: 사용자 이메일 주소
     * 
     * <p>유효성 검증:
     * <ul>
     * <li>{@code @NotBlank}: 필수 입력값</li>
     * <li>{@code @Email}: 이메일 형식 검증</li>
     * </ul>
     * </p>
     * 
     * @see jakarta.validation.constraints.NotBlank
     * @see jakarta.validation.constraints.Email
     */
    @Email
    @NotBlank
    private String email;

    /**
     * description: 사용자 비밀번호
     * 
     * <p>유효성 검증:
     * <ul>
     * <li>{@code @NotBlank}: 필수 입력값</li>
     * </ul>
     * </p>
     * 
     * <p>보안:
     * {@link org.springframework.security.crypto.password.PasswordEncoder}를 통해 검증됨
     * </p>
     */
    @NotBlank
    private String password;
}