package com.example.nihongo.service;

import org.springframework.http.ResponseEntity;

import com.example.nihongo.common.dto.request.auth.EmailAuthSendRequestDto;
import com.example.nihongo.common.dto.request.auth.EmailAuthVerifyRequestDto;
import com.example.nihongo.common.dto.response.ResponseDto;
import com.example.nihongo.common.dto.response.auth.EmailAuthResponseDto;

/**
 * interface: 이메일 인증 서비스 인터페이스
 * 
 * description: 이메일 인증 코드 발송 및 검증 기능을 정의합니다.
 */
public interface EmailAuthService {

  /**
   * description: 이메일 인증 코드 발송
   *
   * @param dto {@link EmailAuthSendRequestDto} 이메일 인증 요청 정보
   * @param email 인증 코드를 받을 이메일 주소
   * @return 성공 시 {@link HttpStatus#OK} (200), 실패 시 오류 응답
   */
  ResponseEntity<ResponseDto> sendEmail(EmailAuthSendRequestDto dto, String email);

  /**
   * description: 이메일 인증 코드 검증
   *
   * @param dto {@link EmailAuthVerifyRequestDto} 이메일 인증 코드 검증 요청 정보
   * @param email 검증할 이메일 주소
   * @return 성공 시 {@link EmailAuthResponseDto}, 실패 시 {@code auth code not match} 응답
   */
  ResponseEntity<? super EmailAuthResponseDto> verifyEmail(EmailAuthVerifyRequestDto dto, String email);
}
