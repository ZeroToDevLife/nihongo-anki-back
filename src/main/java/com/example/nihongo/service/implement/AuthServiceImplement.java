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

@Service
@RequiredArgsConstructor
public class AuthServiceImplement implements AuthService{

  private final UserRepository userRepository;
  private final JwtProvider jwtProvider;
  private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

  @Override
  public ResponseEntity<? super SignInResponseDto> signIn(SignInRequestDto dto) {

    String accessToken = null;

    try {
        
      String email = dto.getEmail();
      UserEntity userEntity = userRepository.findByEmail(email);
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

    return SignInResponseDto.success(accessToken);

  }

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
