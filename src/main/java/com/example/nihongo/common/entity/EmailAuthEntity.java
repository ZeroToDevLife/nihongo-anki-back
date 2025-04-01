package com.example.nihongo.common.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * class: 이메일 인증 정보를 저장하는 엔티티 클래스입니다.
 * 
 * description: 해당 클래스는 데이터베이스의 {@code email_auth} 테이블과 매핑되며, 회원가입 시 이메일 인증에 필요한 정보를 저장합니다.
 * 
 * <p>
 * table: {@code email_auth}
 * primary key: {@code email}
 * </p>
 */
@Entity(name = "email_auth")
@Table(name = "email_auth")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmailAuthEntity {

    /**
     * description: 인증을 진행할 이메일 주소
     * 
     * <p>
     * - {@code @Id}: Primary Key로 사용됨
     * - {@link UserEntity#email}과 매핑됨
     * - {@code @Column(unique = true)}: 중복 불가
     * </p>
     */
    @Id
    private String email;

    /**
     * description: 이메일 인증 코드
     * 
     * <p>
     * - {@code Integer} 타입의 6자리 숫자
     * - {@code 100000 ~ 999999} 범위의 난수
     * - 이메일로 전송되어 사용자 확인에 사용
     * </p>
     */
    private Integer code;
}
