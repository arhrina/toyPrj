package me.study.bootstart;

import org.springframework.boot.ApplicationArguments;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@Component
public class ArgumentChecker {

    /**
     * bean의 생성자가 1개고, 생성자의 parameter가 bean인 경우는 스프링이 알아서 주입한다
     */
    public ArgumentChecker(ApplicationArguments arguments) {
        System.out.println(arguments.toString());
    }
}
