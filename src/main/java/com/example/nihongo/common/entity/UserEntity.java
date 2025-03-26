package com.example.nihongo.common.entity;

import com.example.nihongo.common.dto.request.auth.SignUpRequestDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * class: 사용자 정보를 저장하는 엔티티 클래스입니다.
 * <p>
 * description: 해당 클래스는 데이터베이스의 {@code user} 테이블과 매핑되며, 회원 가입, 로그인, 이메일 인증 등의 기능에 사용됩니다.
 * </p>
 */
@Entity(name = "user")
@Table(name = "user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

  /**
  * description: 사용자의 이메일 주소 (Primary Key)
  */
  @Id
  private String email;

  /**
  * description: 사용자의 비밀번호 (암호화되어 저장됨)
  */
  private String password;

  /**
   * description: 사용자 닉네임
   */
  private String nickname;

  /**
   * description: 이메일 인증 여부
   * <ul>
   *  <li>{@code true} - 이메일 인증 완료</li>
   *  <li>{@code false} - 인증되지 않음</li>
   * </ul>
   */
  @Column(name = "is_verified")
  private boolean isVerified;

  public UserEntity(SignUpRequestDto dto) {
    this.email = dto.getEmail();
    this.password = dto.getPassword();
    this.nickname = dto.getNickname();
    this.isVerified = false;
  }
}