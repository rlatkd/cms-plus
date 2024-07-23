package kr.or.kosa.cmsplusmessaging;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:secure.properties")
public class CmsplusMessagingApplication {

	public static void main(String[] args) {
		SpringApplication.run(CmsplusMessagingApplication.class, args);
	}

}
