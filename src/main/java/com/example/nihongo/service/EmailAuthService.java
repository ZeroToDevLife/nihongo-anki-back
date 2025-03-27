package com.example.nihongo.service;

import org.springframework.http.ResponseEntity;

import com.example.nihongo.common.dto.request.auth.EmailAuthSendRequestDto;
import com.example.nihongo.common.dto.request.auth.EmailAuthVerifyRequestDto;
import com.example.nihongo.common.dto.response.ResponseDto;
import com.example.nihongo.common.dto.response.auth.EmailAuthResponseDto;

public interface  EmailAuthService {
  ResponseEntity<ResponseDto> sendEmail(EmailAuthSendRequestDto dto, String email);
  ResponseEntity<? super EmailAuthResponseDto> verifyEmail(EmailAuthVerifyRequestDto dto, String email);
}
