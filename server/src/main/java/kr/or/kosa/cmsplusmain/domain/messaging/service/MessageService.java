package kr.or.kosa.cmsplusmain.domain.messaging.service;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MessageService {

	public void sendSms(String phoneNumber, String message) {
		log.info("SMS 발송: {}\n{}", phoneNumber, message);
	}

	public void sendEmail(String email, String message) {
		log.info("EMAIL 발송: {}\n{}", email, message);
	}
}
