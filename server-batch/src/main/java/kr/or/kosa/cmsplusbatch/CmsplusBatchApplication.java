package kr.or.kosa.cmsplusbatch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
@EnableJpaAuditing
@PropertySource("classpath:secure.properties")
public class CmsplusBatchApplication {

	public static void main(String[] args) {
		SpringApplication.run(CmsplusBatchApplication.class, args);

	}

}
