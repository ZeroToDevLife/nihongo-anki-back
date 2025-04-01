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
import com.example.nihongo.common.dto.response.auth.EmailAuthResponseDto;
import com.example.nihongo.common.dto.response.auth.SignInResponseDto;
import com.example.nihongo.service.AuthService;
import com.example.nihongo.service.EmailAuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * class: 인증 관련 API 컨트롤러
 * 
 * description: 회원가입, 로그인, 이메일 인증 등의 인증 관련 요청을 처리합니다. 모든 엔드포인트는 {@code /api/v1/auth} 경로 하위에 위치합니다.
 */
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

  private final AuthService authService;
  private final EmailAuthService emailAuthService;

  /**
   * description: 로그인 요청을 처리합니다.
   *
   * @param requestBody {@link SignInRequestDto} 로그인 요청 정보
   * @return {@link SignInResponseDto} 로그인 성공 시 JWT 토큰 반환
   */
  @PostMapping("/sign-in")
  public ResponseEntity<? super SignInResponseDto> signIn(
    @RequestBody @Valid SignInRequestDto requestBody
  ) {
    ResponseEntity<? super SignInResponseDto> response = authService.signIn(requestBody);
    return response;
  }

  /**
   * description: 회원가입 요청을 처리합니다.
   *
   * @param requestBody {@link SignUpRequestDto} 회원가입 요청 정보
   * @return {@link ResponseDto} 회원가입 결과
   */
  @PostMapping("/sign-up")
  public ResponseEntity<ResponseDto> signUp(
    @RequestBody @Valid SignUpRequestDto requestBody
  ){
    ResponseEntity<ResponseDto> response = authService.signUp(requestBody);
    return response;
  }

  /**
   * description: 이메일 중복 확인 요청을 처리합니다.
   *
   * @param requestBody {@link EmailCheckRequestDto} 이메일 중복 확인 요청 정보
   * @return {@link ResponseDto} 이메일 중복 확인 결과
   */
  @PostMapping("/email-check")
  public ResponseEntity<ResponseDto> emailCheck(
    @RequestBody @Valid EmailCheckRequestDto requestBody
  ){
    ResponseEntity<ResponseDto> response = authService.emailCheck(requestBody);
    return response;
  }

  /**
   * description: 이메일 인증 코드 전송 요청을 처리합니다.
   *
   * @param requestBody {@link EmailAuthSendRequestDto} 이메일 인증 코드 전송 요청 정보
   * @return {@link ResponseDto} 이메일 전송 결과
   */
  @PostMapping("/send-email")
  public ResponseEntity<ResponseDto> sendEmail(
    @RequestBody @Valid EmailAuthSendRequestDto requestBody
  ){
    ResponseEntity<ResponseDto> response = emailAuthService.sendEmail(requestBody, requestBody.getEmail());
    return response;
  }

  /**
   * description: 이메일 인증 코드 확인 요청을 처리합니다.
   *
   * @param requestBody {@link EmailAuthVerifyRequestDto} 이메일 인증 코드 확인 요청 정보
   * @return {@link EmailAuthResponseDto} 이메일 인증 결과
   */
  @PostMapping("/verify-email")
  public ResponseEntity<? super EmailAuthResponseDto> verifyEmail(
    @RequestBody @Valid EmailAuthVerifyRequestDto requestBody
  ){
    ResponseEntity<? super EmailAuthResponseDto> response = emailAuthService.verifyEmail(requestBody, requestBody.getEmail());
    return response;
  }
}
