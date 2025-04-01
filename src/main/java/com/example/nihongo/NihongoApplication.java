package com.example.nihongo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * class: 일본어 학습 애플리케이션 메인 클래스
 * 
 * description: Spring Boot 애플리케이션의 진입점입니다.
 */
@SpringBootApplication
public class NihongoApplication {

	/**
	 * description: 애플리케이션 실행 메서드
	 *
	 * @param args 명령행 인자
	 */
	public static void main(String[] args) {
		SpringApplication.run(NihongoApplication.class, args);
	}

}
