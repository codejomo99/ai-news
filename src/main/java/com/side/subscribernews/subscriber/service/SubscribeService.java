package com.side.subscribernews.subscriber.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.side.subscribernews.subscriber.Subscriber;
import com.side.subscribernews.subscriber.repository.SubscriberRepository;
import com.side.subscribernews.util.MailService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SubscribeService {
	private final SubscriberRepository subscriberRepository;
	private final MailService mailService;

	public void subscribe(String email) {
		if(subscriberRepository.findByEmail(email).isPresent()){
			throw new IllegalArgumentException("이미 구독된 메일입니다.");
		}

		String token = UUID.randomUUID().toString();

		Subscriber subscriber = Subscriber.builder()
			.email(email)
			.token(token)
			.isVerified(false)
			.isActive(true)
			.createdAt(LocalDateTime.now())
			.build();

		subscriberRepository.save(subscriber);
		mailService.sendVerificationEmail(email, token);
	}

	public void verify(String token) {
		Subscriber subscriber = subscriberRepository.findByToken(token)
			.orElseThrow(() -> new IllegalArgumentException("유효하지 않은 토큰입니다."));

		subscriber.updateVerified(subscriber);
		subscriberRepository.save(subscriber);
	}
}
