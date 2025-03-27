package com.example.nihongo.common.dto.response.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.nihongo.common.dto.response.ResponseDto;
import com.example.nihongo.common.dto.response.ResponseType;

import lombok.Getter;

@Getter
public class EmailAuthResponseDto extends ResponseDto {

  private boolean isVerified;

  private EmailAuthResponseDto(boolean isVerified) {
    super(ResponseType.SUCCESS);
    this.isVerified = isVerified;
  }

  public static ResponseEntity<EmailAuthResponseDto> success(boolean isVerified) {
    EmailAuthResponseDto body = new EmailAuthResponseDto(isVerified);
    return ResponseEntity.status(HttpStatus.OK).body(body);
  }
  
}
