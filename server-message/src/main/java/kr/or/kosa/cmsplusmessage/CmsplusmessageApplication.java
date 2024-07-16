package kr.or.kosa.cmsplusmessage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:secure.properties")
public class CmsplusmessageApplication {

	public static void main(String[] args) {
		SpringApplication.run(CmsplusmessageApplication.class, args);
	}

}
