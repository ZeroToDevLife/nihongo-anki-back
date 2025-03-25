package com.example.nihongo.common.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "email_auth")
@Table(name = "email_auth")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmailAuthEntity {
  @Id
  private String email;
  private Integer code;
  @Column(name = "is_verified")
  private boolean isVerified;
  
}
