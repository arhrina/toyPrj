package me.study.bootstart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class SpringApplicationResearch {
    public static void main(String[] args) {
//        SpringApplication.run(SpringApplicationResearch.class, args);

        SpringApplication app = new SpringApplication(SpringApplicationResearch.class);
        app.addListeners(new SampleListener()); // Context 생성 전에 발생하는 이벤트는 bean으로 등록할 필요 없고, 따로 리스너를 등록해줘야한다
//        app.setWebApplicationType(WebApplicationType.SERVLET);
// MVC가 있으면 기본적으로 SERVLET WebApplication 타입으로 동작. WebFlux면 Reactive로 동작. 둘 다 없으면 NONE. MVC의 여부 판단이 WebFlux보다 먼저이므로 REACTIVE로 동작하려면 명시해줘야한다

        app.run(args);
        /**
         * VM options -Ddebug / Program Argument --debug
         * debug로 logging됨. 자동설정의 적용과 미적용 원인이 기록됨
         *
         * Banner -> resources에 banner.txt 파일. 다른 위치에 두고 싶으면 property 파일에 banner의 location을 변경. 텍스트 말고, 이미지로 변경 가능.
         * 파일 말고 클래스로 구현하고 SpringApplication.setBanner로 설정 가능
         *
         *
         * SpringApplicationBuilder로 빌더 패턴 사용 가능
         */

//        new SpringApplicationBuilder()
//                .sources(SpringApplicationResearch.class)
//                .run(args);
    }
}
