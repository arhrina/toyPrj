package me.study.bootstart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

// @SpringBootApplication

/**
 * bean을 componentScan과 enableAutoConfiguration으로 2단계를 통해 등록한다
 */
@SpringBootConfiguration // @Configuration
@ComponentScan // 스캔이 있는 클래스 패키지 포함, 이하 패키지에 @Component를 가진 class를 bean으로 등록
@EnableAutoConfiguration // spring.factories 파일에 있는 설정되있는 파일들을 bean으로 등록
public class BootStartBaekgisunApplication {

    public static void main(String[] args) {
//        SpringApplication application = new SpringApplication(BootStartBaekgisunApplication.class);
//        application.setWebApplicationType(WebApplicationType.NONE);// auto configuration을 하지 않고 web application으로 띄우지 않고 run하는 설정
//        application.run(args);


        SpringApplication.run(BootStartBaekgisunApplication.class, args);
        // Web Application으로 만들려고 기본값이 설정되어 있는 것이 있다
    }

}
