package org.example.hmpractice;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootApplication
@Log4j2
public class HmPracticeApplication {

    public static void main(String[] args) {

        // 로그 파일 경로와 이름을 동적으로 생성 후 파일 경로 설정
        System.setProperty("logging.file.name",
                "/Users/NC413/Desktop/개발/hmTestProject/logs/" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + ".log");

        log.trace("TRACE 로그 메시지"); // DEBUG 레벨보다 상세한 메시지
        log.debug("DEBUG 로그 메시지"); // 개발 디버깅 목적용 메시지
        log.info("INFO 로그 메시지");   // 로그인 or 상태 변경 같은 정보성 메시지
        log.warn("WARN 로그 메시지");   // 향후 시스템의 에러의 원인이 될 수 있는 문제
        log.error("ERROR 로그 메시지"); // 사용자 요청을 처리하는 문제

        SpringApplication.run(HmPracticeApplication.class, args);
    }

}
