package study.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication // 이 어노테이션이 있는 패키지 이하만을 component scan하여 bean으로 만든다. 해당 패키지를 default package라고 한다
public class Application {
    /**
     * Spring-boot Application의 가장 기본적인 프로젝트 형태. main이 필요하고, SpringBootApplication Annotation이 필요하다
     * https://start.spring.io에서 이 기본프로젝트 형태의 프로젝트를 생성해주고, 필요한 dependency를 추가해줄 수 있다
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
