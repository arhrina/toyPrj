package me.study.externalsettings;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@EnableConfigurationProperties(SampleProperties.class)
public class ExternalSettingsApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(ExternalSettingsApplication.class);
        app.setWebApplicationType(WebApplicationType.NONE);
        app.run(args);
    }

}
