package kr.or.kosa.cmspluspayment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:secure.properties")
public class CmsplusPaymentApplication {

	public static void main(String[] args) {
		SpringApplication.run(CmsplusPaymentApplication.class, args);
	}

}
