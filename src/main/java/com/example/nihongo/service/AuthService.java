package com.example.nihongo.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.nihongo.common.dto.request.auth.EmailCheckRequestDto;
import com.example.nihongo.common.dto.request.auth.SignInRequestDto;
import com.example.nihongo.common.dto.request.auth.SignUpRequestDto;
import com.example.nihongo.common.dto.response.ResponseDto;
import com.example.nihongo.common.dto.response.auth.SignInResponseDto;

/**
 * interface: 인증 서비스 인터페이스
 * 
 * description: 회원가입, 로그인, 이메일 중복 확인 등의 인증 관련 기능을 정의합니다.
 */
public interface AuthService {

  /**
   * description: 로그인 처리
   *
   * @param dto {@link SignInRequestDto} 로그인 요청 정보
   * @return 성공 시 {@link SignInResponseDto}, 실패 시 {@code sign in fail} 응답
   */
  ResponseEntity<? super SignInResponseDto> signIn(SignInRequestDto dto);

  /**
   * description: 회원가입 처리
   *
   * @param dto {@link SignUpRequestDto} 회원가입 요청 정보
   * @return 성공 시 {@link HttpStatus#CREATED} (201), 실패 시 오류 응답
   */
  ResponseEntity<ResponseDto> signUp(SignUpRequestDto dto);

  /**
   * description: 이메일 중복 확인
   *
   * @param dto {@link EmailCheckRequestDto} 이메일 중복 확인 요청 정보
   * @return 성공 시 {@link HttpStatus#OK} (200), 실패 시 오류 응답
   */
  ResponseEntity<ResponseDto> emailCheck(EmailCheckRequestDto dto);
}
