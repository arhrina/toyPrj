package me.study.externalsettings;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.convert.DurationUnit;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

@Component
@ConfigurationProperties("kee") // key값
/**
 * <dependency>
 *     <groupId>org.springframework.boot</groupId>
 *     <artifactId>spring-boot-configuration-processor</artifactId>
 *     <optional>true</optional>
 * </dependency>
 *
 * 필요
 *
 * bean으로 등록하기 위해 @EnableConfigurationProperties(Class명.class)를
 * springbootapplication class에 넣어줘야되지만, 자동으로 등록되어있음
 */
// @Validated // hibernate validator. @NotEmpty 등 사용 가능
public class SampleProperties {
    int age;

    @DurationUnit(ChronoUnit.SECONDS) // 시간을 받는 형
    Duration sessionTimeout = Duration.ofSeconds(30);

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
