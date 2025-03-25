package com.example.nihongo.service;

import org.springframework.http.ResponseEntity;

import com.example.nihongo.common.dto.request.auth.EmailAuthSendRequestDto;
import com.example.nihongo.common.dto.request.auth.EmailAuthVerifyRequestDto;
import com.example.nihongo.common.dto.response.ResponseDto;

public interface  EmailAuthService {
  ResponseEntity<ResponseDto> sendEmail(EmailAuthSendRequestDto dto, String email);
  ResponseEntity<ResponseDto> verifyEmail(EmailAuthVerifyRequestDto dto, String email);
}
