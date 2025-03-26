package com.example.nihongo.common.dto.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * class: API 응답에 사용되는 공통 DTO입니다.
 * <p>
 * description: 모든 API 응답은 {@code code}와 {@code message} 필드를 포함하며, {@link ResponseType} 열거형을 통해 일관된 응답 형식을 제공합니다.
 * </p>
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ResponseDto {

  /** 
   * description: 응답 코드 (예: SU, VF, SF, DBE 등)
   */
  private String code;

  /** 
   * description: 응답 메시지 (예: "Success.", "Validation fail." 등)
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
   * @param status HTTP 상태 코드 (예: 200 OK)
   * @return 성공 응답을 담은 {@code ResponseEntity}
   */
  public static ResponseEntity<ResponseDto> success(HttpStatus status) {
    ResponseDto body = new ResponseDto(ResponseType.SUCCESS);
    return ResponseEntity.status(status).body(body);
  }

  /**
   * description: 유효성 검사 실패 응답을 생성합니다.
   *
   * @return 400 Bad Request 응답
   */
  public static ResponseEntity<ResponseDto> validationFail() {
      ResponseDto body = new ResponseDto(ResponseType.VALIDATION_FAIL);
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
  }

  /**
   * description: 이메일 코드 유효성 검사 실패 응답을 생성합니다.
   * 
   * @return
   */
  public static ResponseEntity<ResponseDto> emailCodeValidationFail() {
    ResponseDto body = new ResponseDto(ResponseType.EMAIL_CODE_VALIDATION_FAIL);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
  }

  /**
   * description: 존재하는 이메일 응답을 생성합니다.
   * 
   * @return 400 Bad Request 응답
   */
  public static ResponseEntity<ResponseDto> existEmail() {
    ResponseDto body = new ResponseDto(ResponseType.EXIST_EMAIL);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
  }

  /**
   * description: 로그인 실패 응답을 생성합니다.
   *
   * @return 401 Unauthorized 응답
   */
  public static ResponseEntity<ResponseDto> signInFail() {
    ResponseDto body = new ResponseDto(ResponseType.SIGN_IN_FAIL);
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(body);
  }

  /**
   * description: 데이터베이스 오류 응답을 생성합니다.
   *
   * @return 500 Internal Server Error 응답
   */
  public static ResponseEntity<ResponseDto> databaseError() {
    ResponseDto body = new ResponseDto(ResponseType.DATABASE_ERROR);
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
  }
}