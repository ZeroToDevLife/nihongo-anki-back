package com.example.nihongo.common.dto.request.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmailAuthVerifyRequestDto {
  @NotBlank
  @Email
  private String email;
  @NotBlank
  @Pattern(regexp = "^[0-9]{6}$")
  private String code;
  
}
