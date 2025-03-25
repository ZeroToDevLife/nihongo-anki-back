package com.example.nihongo.common.dto.response;

/**
 * enum: API 응답 코드와 메시지를 포함하는 열거형(enum)입니다.
 * 
 * description: 각 항목은 고유한 응답 코드(`code`)와 그에 해당하는 메시지(`message`)를 가지고 있으며, 클라이언트에게 일관된 형식으로 응답을 제공하기 위해 사용됩니다.
 *
 * <p><b>예시:</b> ResponseType.SUCCESS.getCode() → "SU", ResponseType.SUCCESS.getMessage() → "Success."</p>
 */
public enum ResponseType {

  /** 
   * description: 요청 성공 
   */
  SUCCESS("SU", "Success."),

  /** 
   * description: 유효성 검사 실패 
   */ 
  VALIDATION_FAIL("VF", "Validation fail."),

  /**
   * description: 이메일 코드 유효성 검사 실패
   */
  EMAIL_CODE_VALIDATION_FAIL("ECVF", "Email code validation fail."),

  /**
   * description: 존재하는 이메일
   */
  EXIST_EMAIL("EE", "Exist email."),

  /** 
   * description: 로그인 실패 
   */ 
  SIGN_IN_FAIL("SF", "Sign In Fail."),

  /** 
   * description: 데이터베이스 오류
   */ 
  DATABASE_ERROR("DBE", "Database Error.");

  private final String code;
  private final String message;

  /**
   * description: 응답 타입 생성자입니다.
   *
   * @param code 응답 코드
   * @param message 응답 메시지
   */
  ResponseType(String code, String message) {
    this.code = code;
    this.message = message;
  }

  /**
   * description: 응답 코드를 반환합니다.
   *
   * @return 응답 코드 (예: "SU", "VF" 등)
   */
  public String getCode() {
    return code;
  }

  /**
   * description: 응답 메시지를 반환합니다.
   *
   * @return 응답 메시지 (예: "Success.", "Validation fail." 등)
   */
  public String getMessage() {
    return message;
  }
}