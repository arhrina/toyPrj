package me.study.externalsettings.config;

import me.study.externalsettings.SampleProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class SampleRun implements ApplicationRunner {

    @Autowired
    private String hello;

    @Autowired
    private ProfileProperties properties;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println(hello);
        System.out.println(properties.getName());
    }
}
