package com.example.nihongo.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.example.nihongo.common.dto.response.ResponseDto;

/**
 * class: 전역 예외 처리를 위한 핸들러 클래스입니다.
 * <p>
 * description: 요청 데이터 검증 실패 또는 RequestBody 누락 등으로 발생하는 예외를 처리하여, 클라이언트에게 일관된 응답을 반환합니다.
 * </p>
 */
@RestController
public class CustomExceptionHandler {

  /**
   * function: 요청 값 유효성 검사 실패 또는 RequestBody 파싱 실패 시 처리합니다.
   *
   * <ul>
   *   <li>{@link MethodArgumentNotValidException} - @Valid 검증 실패</li>
   *   <li>{@link HttpMessageNotReadableException} - RequestBody 누락 또는 파싱 불가</li>
   * </ul>
   * 
   * @param exception 발생한 예외
   * @return 400 Bad Request와 함께 Validation 실패 응답 반환
   */
  @ExceptionHandler({
    MethodArgumentNotValidException.class,
    HttpMessageNotReadableException.class
  })
  public ResponseEntity<ResponseDto> validExceptionHandler(Exception exception) {
    exception.printStackTrace();
    return ResponseDto.validationFail();
  }
  
}
