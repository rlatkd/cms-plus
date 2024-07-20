package kr.or.kosa.cmsplusmessaging.config;

import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SmsConfig {

    @Value("${sms.key}")
    private String smsKey;

    @Value("${sms.secret}")
    private String smsSecret;

    @Value("${sms.domain}")
    private String smsDomain;

    @Bean
    public DefaultMessageService defaultMessageService() {
        return new DefaultMessageService(smsKey, smsSecret, smsDomain);
    }

}
