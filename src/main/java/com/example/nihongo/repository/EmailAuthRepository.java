package com.example.nihongo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.nihongo.common.entity.EmailAuthEntity;

@Repository
public interface EmailAuthRepository extends JpaRepository<EmailAuthEntity, String> {
  EmailAuthEntity findByEmail(String email);
}
