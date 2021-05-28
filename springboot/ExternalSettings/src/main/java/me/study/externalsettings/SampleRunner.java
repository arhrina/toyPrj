package me.study.externalsettings;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class SampleRunner implements ApplicationRunner {

    @Value("${keesun.age}") // property 우선순위가 존재. Spring Expression Language 지원
    private int age;

    @Autowired
    SampleProperties properties;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("================");
        System.out.println(age);
        System.out.println(properties.getAge());
        System.out.println("================");
    }
}
