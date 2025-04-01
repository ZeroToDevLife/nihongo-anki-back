package com.example.nihongo.common.dto.request.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * class: 이메일 중복 확인 요청 DTO
 * 
 * description: 회원가입 시 이메일 중복 여부를 확인하기 위한 요청에 사용되는 DTO입니다.
 * 
 * <p>유효성 검증 실패 시 {@link com.example.nihongo.handler.CustomExceptionHandler#validExceptionHandler}에서
 * {@code validation fail} 응답을 반환합니다.</p>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmailCheckRequestDto {

    /**
     * description: 확인할 이메일 주소
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
    @NotBlank
    @Email
    private String email;
}
