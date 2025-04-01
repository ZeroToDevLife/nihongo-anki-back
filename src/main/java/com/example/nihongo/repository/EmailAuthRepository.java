package com.example.nihongo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.nihongo.common.entity.EmailAuthEntity;

/**
 * interface: 이메일 인증 정보를 데이터베이스에서 조회하는 Repository
 * 
 * description: {@link EmailAuthEntity} 엔티티의 데이터베이스 작업을 처리합니다.
 */
@Repository
public interface EmailAuthRepository extends JpaRepository<EmailAuthEntity, String> {
  
  /**
   * description: 이메일로 인증 정보 조회
   *
   * @param email 조회할 이메일
   * @return 해당 이메일의 인증 정보 엔티티, 없으면 {@code null}
   */
  EmailAuthEntity findByEmail(String email);
}
