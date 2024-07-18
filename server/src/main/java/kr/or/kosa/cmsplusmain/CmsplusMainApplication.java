package kr.or.kosa.cmsplusmain;

import kr.or.kosa.cmsplusmain.domain.kafka.SetProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
@PropertySource("classpath:secure.properties")
//@EnableConfigurationProperties(SetProperties.class)
public class CmsplusMainApplication {

	public static void main(String[] args) {
		SpringApplication.run(CmsplusMainApplication.class, args);
	}

}
