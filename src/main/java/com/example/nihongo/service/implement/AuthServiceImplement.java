package com.example.nihongo.service.implement;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.nihongo.common.dto.request.auth.EmailCheckRequestDto;
import com.example.nihongo.common.dto.request.auth.SignInRequestDto;
import com.example.nihongo.common.dto.request.auth.SignUpRequestDto;
import com.example.nihongo.common.dto.response.ResponseDto;
import com.example.nihongo.common.dto.response.auth.SignInResponseDto;
import com.example.nihongo.common.entity.UserEntity;
import com.example.nihongo.provider.JwtProvider;
import com.example.nihongo.repository.UserRepository;
import com.example.nihongo.service.AuthService;

import lombok.RequiredArgsConstructor;

/**
 * class: 인증 관련 서비스 구현체
 * 
 * description: 회원가입, 로그인, 이메일 중복 확인 등의 인증 관련 기능을 구현합니다.
 */
@Service
@RequiredArgsConstructor
public class AuthServiceImplement implements AuthService{

  private final UserRepository userRepository;
  private final JwtProvider jwtProvider;
  private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

  /**
   * description: 로그인 처리
   * 
   * <p>
   * <ul>
   * <li>이메일로 사용자 조회</li>
   * <li>비밀번호 일치 여부 확인</li>
   * <li>JWT 토큰 생성</li>
   * </ul>
   * </p>
   *
   * @param dto {@link SignInRequestDto} 로그인 요청 정보
   * @return 성공 시 {@link SignInResponseDto}, 실패 시 {@code sign in fail} 응답
   */
  @Override
  public ResponseEntity<? super SignInResponseDto> signIn(SignInRequestDto dto) {

    String accessToken = null;
    UserEntity userEntity = null;

    try {
        
      String email = dto.getEmail();
      userEntity = userRepository.findByEmail(email);
      if (userEntity == null) return ResponseDto.signInFail();

      String password = dto.getPassword();
      String encodedPassword = userEntity.getPassword();
      boolean isMatch = passwordEncoder.matches(password, encodedPassword);
      if (!isMatch) return ResponseDto.signInFail();

      accessToken = jwtProvider.create(email);

    } catch (Exception exception) {
      exception.printStackTrace();
      return ResponseDto.databaseError();
    }

    return SignInResponseDto.success(accessToken, userEntity.isVerified());

  }

  /**
   * description: 회원가입 처리
   * 
   * <p>
   * <ul>
   * <li>이메일 중복 확인</li>
   * <li>비밀번호 암호화</li>
   * <li>사용자 정보 저장</li>
   * </ul>
   * </p>
   *
   * @param dto {@link SignUpRequestDto} 회원가입 요청 정보
   * @return 성공 시 {@link HttpStatus#CREATED} (201), 실패 시 오류 응답
   */
  @Override
  public ResponseEntity<ResponseDto> signUp(SignUpRequestDto dto) {
    
    try {

      String email = dto.getEmail();
      boolean existEmail = userRepository.existsByEmail(email);
      if (existEmail) return ResponseDto.existEmail();

      String password = dto.getPassword();
      String encodedPassword = passwordEncoder.encode(password);
      dto.setPassword(encodedPassword);

      UserEntity userEntity = new UserEntity(dto);
      userRepository.save(userEntity);
        
    } catch (Exception exception) {
      exception.printStackTrace();
      return ResponseDto.databaseError();
    }

    return ResponseDto.success(HttpStatus.CREATED);

  }

  /**
   * description: 이메일 중복 확인
   * 
   * <p>
   * <ul>
   * <li>이메일 존재 여부 확인</li>
   * <li>중복된 이메일이면 {@code exist email} 응답</li>
   * </ul>
   * </p>
   *
   * @param dto {@link EmailCheckRequestDto} 이메일 중복 확인 요청 정보
   * @return 성공 시 {@link HttpStatus#OK} (200), 실패 시 오류 응답
   */
  @Override
  public ResponseEntity<ResponseDto> emailCheck(EmailCheckRequestDto dto) {

    try {

      String email = dto.getEmail();
      boolean existEmail = userRepository.existsByEmail(email);
      if (existEmail) return ResponseDto.existEmail();
        
    } catch (Exception exception) {
      exception.printStackTrace();
      return ResponseDto.databaseError();
    }

    return ResponseDto.success(HttpStatus.OK);

  }
  
}
