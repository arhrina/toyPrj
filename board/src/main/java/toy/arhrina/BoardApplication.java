package toy.arhrina;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

// @Profile("dev") // JVM에 직접 -Dspring.profiles.active를 준것과 같음
@EnableJpaAuditing// (modifyOnCreate = false) // create 말고 update값은 최초 null로 생성하는 옵션. data jpa 사용시 필요한 어노테이션
@SpringBootApplication
public class BoardApplication {

	@Autowired
	private Environment environment;

	@Autowired
	private DataSource dataSource;

	public static void main(String[] args) {
		SpringApplication.run(BoardApplication.class, args);
	}

	@PostConstruct
	public void print() throws SQLException {
		System.out.println(environment.getActiveProfiles());
		System.out.println(this.dataSource.getConnection());
	}

	@Bean
	public AuditorAware<String> auditorAware() { // 등록자 수정자에 필요
		return () -> Optional.of(UUID.randomUUID().toString());
	}

}
