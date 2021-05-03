package me.study.bootstart;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class SimpleAfterRun2 implements CommandLineRunner { // app이 실행한 뒤 뭔가를 실행하고 싶을 때. 순서 지정 가능. VM Options는 사용x
    @Override
    public void run(String... args) throws Exception {
        Arrays.stream(args).forEach(System.out::println);
    }

}
