package com.example.nihongo.service.implement;

import java.util.Random;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.example.nihongo.common.dto.request.auth.EmailAuthSendRequestDto;
import com.example.nihongo.common.dto.request.auth.EmailAuthVerifyRequestDto;
import com.example.nihongo.common.dto.response.ResponseDto;
import com.example.nihongo.common.dto.response.auth.EmailAuthResponseDto;
import com.example.nihongo.common.entity.EmailAuthEntity;
import com.example.nihongo.common.entity.UserEntity;
import com.example.nihongo.repository.EmailAuthRepository;
import com.example.nihongo.repository.UserRepository;
import com.example.nihongo.service.EmailAuthService;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

/**
 * class: 이메일 인증 서비스 구현체
 * 
 * description: 이메일 인증 코드 발송 및 검증 기능을 구현합니다.
 */
@Service
@RequiredArgsConstructor
public class EmailAuthServiceImplement implements EmailAuthService {

  private final EmailAuthRepository emailAuthRepository;
  private final UserRepository userRepository;
  private final JavaMailSender javaMailSender;

  /**
   * description: 이메일 인증 코드 발송
   * 
   * <p>
   * <ul>
   * <li>6자리 랜덤 인증 코드 생성</li>
   * <li>이메일 인증 정보 저장 또는 업데이트</li>
   * <li>인증 코드 이메일 발송</li>
   * </ul>
   * </p>
   *
   * @param dto {@link EmailAuthSendRequestDto} 이메일 인증 요청 정보
   * @return 성공 시 {@link HttpStatus#OK} (200), 실패 시 오류 응답
   */
  @Override
  public ResponseEntity<ResponseDto> sendEmail(EmailAuthSendRequestDto dto, String email) {

    Integer code = new Random().nextInt(900000) + 100000;

    try {

      MimeMessage message = javaMailSender.createMimeMessage();
      MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
    
      helper.setTo(email);
      helper.setSubject("[Nihongo Anki] 이메일 인증 코드입니다.");
      helper.setText("""
          <div style="font-family: sans-serif; padding: 10px;">
            <h2>안녕하세요! Nihongo Anki 인증 안내입니다.</h2>
            <p>아래 코드를 인증 페이지에 입력해주세요:</p>
            <h3 style="color: blue;">인증 코드: <strong>"""
             + code + """
             </strong></h3>
          </div>
          """
          , true); // true → HTML 사용
      
      javaMailSender.send(message);

      EmailAuthEntity emailAuthEntity = new EmailAuthEntity(
      email,
      code
      );
        
      emailAuthRepository.save(emailAuthEntity);

    } catch (Exception exception) {
      exception.printStackTrace();
      return ResponseDto.databaseError();
    }

    return ResponseDto.success(HttpStatus.CREATED);

  }

  /**
   * description: 이메일 인증 코드 검증
   * 
   * <p>
   * <ul>
   * <li>이메일로 인증 정보 조회</li>
   * <li>인증 코드 일치 여부 확인</li>
   * <li>인증 상태 업데이트</li>
   * </ul>
   * </p>
   *
   * @param dto {@link EmailAuthResponseDto} 이메일 인증 코드 검증 요청 정보
   * @return 성공 시 {@link HttpStatus#OK} (200), 실패 시 {@code auth code not match} 응답
   */
  @Override
  public ResponseEntity<? super EmailAuthResponseDto> verifyEmail(EmailAuthVerifyRequestDto dto, String email) {

    Integer code = Integer.valueOf(dto.getCode());
    UserEntity userEntity = null;


    try {

      EmailAuthEntity emailAuthEntity = emailAuthRepository.findByEmail(email);
      if (emailAuthEntity == null || !emailAuthEntity.getCode().equals(code)) return ResponseDto.validationFail();
   
      userEntity = userRepository.findByEmail(email);
      if (userEntity == null) return ResponseDto.validationFail();

      userEntity.setVerified(true);
      userRepository.save(userEntity);
      
        
    } catch (Exception exception) {
      exception.printStackTrace();
      return ResponseDto.databaseError();
    }

    return EmailAuthResponseDto.success(userEntity.isVerified());

  }
  
}

