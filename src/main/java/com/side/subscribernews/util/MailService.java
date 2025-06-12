package com.side.subscribernews.util;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MailService {
	private final JavaMailSender mailSender;

	public void sendVerificationEmail(String to, String token) {
		String subject = "AI 뉴스 요약 구독 인증 메일";
		String link = "http://localhost:8080/api/verify?token=" + token;

		String text = "구독 인증을 완료하려면 아래 링크를 클릭해주세요:\n" + link;

		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(to);
		message.setSubject(subject);
		message.setText(text);

		mailSender.send(message);
	}

	public void sendUnsubscribeEmail(String email, String token) {
		String subject = "AI 뉴스 요약 구독 해제 인증 메일";
		String link = "http://localhost:8080/api/verify-unsubscribe?token=" + token;

		String text = "구독 인증을 완료하려면 아래 링크를 클릭해주세요:\n" + link;

		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(email);
		message.setSubject(subject);
		message.setText(text);

		mailSender.send(message);
	}
}
