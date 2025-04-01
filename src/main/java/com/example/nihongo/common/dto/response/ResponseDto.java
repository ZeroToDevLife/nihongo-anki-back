package com.example.nihongo.common.dto.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * class: 공통 응답 DTO
 * 
 * description: 모든 API 응답은 {@code code}와 {@code message} 필드를 포함하며, {@link ResponseType} 열거형을 통해 일관된 응답 형식을 제공합니다.
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ResponseDto {

  /** 
   * description: API 응답의 상태를 나타내는 코드
   * 
   * <p>
   * <ul>
   * <li>{@code "SU"}: 성공</li>
   * <li>{@code "VF"}: 유효성 검증 실패</li>
   * <li>{@code "SF"}: 로그인 실패</li>
   * <li>{@code "DBE"}: 데이터베이스 오류</li>
   * </ul>
   * </p>
   * 
   * @see ResponseType#getCode()
   */
  private String code;

  /** 
   * description: API 응답의 상세 메시지
   * 
   * <p>
   * <ul>
   * <li>{@code "Success."}: 성공</li>
   * <li>{@code "Validation fail."}: 유효성 검증 실패</li>
   * <li>{@code "Sign In Fail."}: 로그인 실패</li>
   * <li>{@code "Database Error."}: 데이터베이스 오류</li>
   * </ul>
   * </p>
   * 
   * @see ResponseType#getMessage()
   */
  private String message;

  /**
   * description: 지정된 응답 타입으로 응답 객체를 생성합니다.
   *
   * @param responseType 응답 코드와 메시지를 담고 있는 {@link ResponseType}
   */
  protected ResponseDto(ResponseType responseType) {
    this.code = responseType.getCode();
    this.message = responseType.getMessage();
  }

  /**
   * description: 성공 응답을 생성합니다.
   *
   * @param status HTTP 상태 코드 (예: {@link HttpStatus#OK})
   * @return {@link HttpStatus#OK} (200) 응답
   */
  public static ResponseEntity<ResponseDto> success(HttpStatus status) {
    ResponseDto body = new ResponseDto(ResponseType.SUCCESS);
    return ResponseEntity.status(status).body(body);
  }

  /**
   * description: 유효성 검사 실패 응답을 생성합니다.
   *
   * @return {@link HttpStatus#BAD_REQUEST} (400) 응답
   */
  public static ResponseEntity<ResponseDto> validationFail() {
      ResponseDto body = new ResponseDto(ResponseType.VALIDATION_FAIL);
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
  }

  /**
   * description: 이메일 코드 유효성 검사 실패 응답을 생성합니다.
   * 
   * @return {@link HttpStatus#BAD_REQUEST} (400) 응답
   */
  public static ResponseEntity<ResponseDto> emailCodeValidationFail() {
    ResponseDto body = new ResponseDto(ResponseType.EMAIL_CODE_VALIDATION_FAIL);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
  }

  /**
   * description: 존재하는 이메일 응답을 생성합니다.
   * 
   * @return {@link HttpStatus#BAD_REQUEST} (400) 응답
   */
  public static ResponseEntity<ResponseDto> existEmail() {
    ResponseDto body = new ResponseDto(ResponseType.EXIST_EMAIL);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
  }

  /**
   * description: 로그인 실패 응답을 생성합니다.
   *
   * @return {@link HttpStatus#UNAUTHORIZED} (401) 응답
   */
  public static ResponseEntity<ResponseDto> signInFail() {
    ResponseDto body = new ResponseDto(ResponseType.SIGN_IN_FAIL);
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(body);
  }

  /**
   * description: 데이터베이스 오류 응답을 생성합니다.
   *
   * @return {@link HttpStatus#INTERNAL_SERVER_ERROR} (500) 응답
   */
  public static ResponseEntity<ResponseDto> databaseError() {
    ResponseDto body = new ResponseDto(ResponseType.DATABASE_ERROR);
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
  }
}