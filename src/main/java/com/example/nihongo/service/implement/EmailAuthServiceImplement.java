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

@Service
@RequiredArgsConstructor
public class EmailAuthServiceImplement implements EmailAuthService {

  private final EmailAuthRepository emailAuthRepository;
  private final UserRepository userRepository;
  private final JavaMailSender javaMailSender;

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

