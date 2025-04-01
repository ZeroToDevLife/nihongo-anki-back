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
 * 
 * description: 해당 클래스는 데이터베이스의 {@code user} 테이블과 매핑되며, 회원 가입, 로그인, 이메일 인증 등의 기능에 사용됩니다.
 * 
 * <p>
 * table: {@code user}
 * primary key: {@code email}
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
     * description: 사용자의 이메일 주소
     * 
     * <p>
     * <ul>
     * <li>{@code @Id}: Primary Key로 사용됨</li>
     * <li>{@code @Column(unique = true)}: 중복 불가</li>
     * <li>로그인 시 아이디로 사용</li>
     * </ul>
     * </p>
     */
    @Id
    private String email;

    /**
     * description: 사용자의 비밀번호
     * 
     * <p>
     * <ul>
     * <li>{@code BCryptPasswordEncoder}로 암호화되어 저장</li>
     * <li>{@link org.springframework.security.crypto.password.PasswordEncoder} 사용</li>
     * </ul>
     * </p>
     */
    private String password;

    /**
     * description: 사용자 닉네임
     * 
     * <p>
     * <ul>
     * <li>{@code @Column(nullable = false)}: 필수 입력값</li>
     * <li>커뮤니티 활동에 사용되는 표시 이름</li>
     * </ul>
     * </p>
     */
    private String nickname;

    /**
     * description: 이메일 인증 여부
     * 
     * <p>
     * <ul>
     * <li>{@code true}: 이메일 인증 완료</li>
     * <li>{@code false}: 이메일 인증 대기 중</li>
     * <li>{@code @Column(name = "is_verified")}: DB 컬럼명 매핑</li>
     * </ul>
     * </p>
     */
    @Column(name = "is_verified")
    private boolean isVerified;

    /**
     * description: 회원가입 요청 DTO를 통한 사용자 엔티티 생성
     *
     * @param dto {@link SignUpRequestDto} 회원가입 요청 정보
     */
    public UserEntity(SignUpRequestDto dto) {
        this.email = dto.getEmail();
        this.password = dto.getPassword();
        this.nickname = dto.getNickname();
        this.isVerified = false;
    }
}