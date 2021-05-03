package me.study.bootstart;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class SimpleAfterRun implements ApplicationRunner { // app이 실행한 뒤 뭔가를 실행하고 싶을 때. @Order로 순서 지정 가능. 오름차순으로 실행. VM Options는 사용x
    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println(args.toString());
    }
}
