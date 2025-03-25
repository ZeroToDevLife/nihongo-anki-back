package com.example.nihongo.service;

import org.springframework.http.ResponseEntity;

import com.example.nihongo.common.dto.request.auth.EmailCheckRequestDto;
import com.example.nihongo.common.dto.request.auth.SignInRequestDto;
import com.example.nihongo.common.dto.request.auth.SignUpRequestDto;
import com.example.nihongo.common.dto.response.ResponseDto;
import com.example.nihongo.common.dto.response.auth.SignInResponseDto;

/**
 * interface: 인증 관련 서비스 인터페이스
 * 
 * description: 인증 관련 기능을 정의
 */
public interface  AuthService {
  ResponseEntity<? super SignInResponseDto> signIn(SignInRequestDto dto);
  ResponseEntity<ResponseDto> signUp(SignUpRequestDto dto);
  ResponseEntity<ResponseDto> emailCheck(EmailCheckRequestDto dto);
}
