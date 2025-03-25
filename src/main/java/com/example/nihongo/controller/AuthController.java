package com.example.nihongo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.nihongo.common.dto.request.auth.EmailAuthSendRequestDto;
import com.example.nihongo.common.dto.request.auth.EmailAuthVerifyRequestDto;
import com.example.nihongo.common.dto.request.auth.EmailCheckRequestDto;
import com.example.nihongo.common.dto.request.auth.SignInRequestDto;
import com.example.nihongo.common.dto.request.auth.SignUpRequestDto;
import com.example.nihongo.common.dto.response.ResponseDto;
import com.example.nihongo.common.dto.response.auth.SignInResponseDto;
import com.example.nihongo.service.AuthService;
import com.example.nihongo.service.EmailAuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

  private final AuthService authService;
  private final EmailAuthService emailAuthService;

  @PostMapping("/sign-in")
  public ResponseEntity<? super SignInResponseDto> signIn(
    @RequestBody @Valid SignInRequestDto requestBody
  ) {
    ResponseEntity<? super SignInResponseDto> response = authService.signIn(requestBody);
    return response;
  }

  @PostMapping("/sign-up")
  public ResponseEntity<ResponseDto> signUp(
    @RequestBody @Valid SignUpRequestDto requestBody
  ){
    ResponseEntity<ResponseDto> response = authService.signUp(requestBody);
    return response;
  }

  @PostMapping("/email-check")
  public ResponseEntity<ResponseDto> emailCheck(
    @RequestBody @Valid EmailCheckRequestDto requestBody
  ){
    ResponseEntity<ResponseDto> response = authService.emailCheck(requestBody);
    return response;
  }

  @PostMapping("/send-email")
  public ResponseEntity<ResponseDto> sendEmail(
    @RequestBody @Valid EmailAuthSendRequestDto requestBody
  ){
    ResponseEntity<ResponseDto> response = emailAuthService.sendEmail(requestBody, requestBody.getEmail());
    return response;
  }

  @PostMapping("/verify-email")
  public ResponseEntity<ResponseDto> verifyEmail(
    @RequestBody @Valid EmailAuthVerifyRequestDto requestBody
  ){
    ResponseEntity<ResponseDto> response = emailAuthService.verifyEmail(requestBody, requestBody.getEmail());
    return response;
  }
  
}
