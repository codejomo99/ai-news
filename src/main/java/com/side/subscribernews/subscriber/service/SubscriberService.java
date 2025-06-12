package com.side.subscribernews.subscriber.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.side.subscribernews.common.exception.BaseException;
import com.side.subscribernews.common.exception.CommonErrorCode;
import com.side.subscribernews.subscriber.Subscriber;
import com.side.subscribernews.subscriber.repository.SubscriberRepository;
import com.side.subscribernews.util.MailService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SubscriberService {
	private final SubscriberRepository subscriberRepository;
	private final MailService mailService;

	public void subscribe(String email) {
		if(subscriberRepository.findByEmail(email).isPresent()){
			throw new BaseException(CommonErrorCode.USER_ALREADY_EXISTS);
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

	public void unsubscribe(String email) {
		Subscriber subscriber = subscriberRepository.findByEmail(email)
			.orElseThrow(() -> new BaseException(CommonErrorCode.USER_NOT_EXISTS));

		String token = UUID.randomUUID().toString();
		subscriber.updateToken(token);
		subscriberRepository.save(subscriber);

		mailService.sendUnsubscribeEmail(email, token);
	}

	public void verifyUnsubscribe(String token) {
		Subscriber subscriber = subscriberRepository.findByToken(token)
			.orElseThrow(() -> new IllegalArgumentException("유효하지 않은 토큰입니다."));

		subscriberRepository.delete(subscriber);
	}
}
