package com.side.subscribernews.Subscriber.service;

import org.springframework.stereotype.Service;

import com.side.subscribernews.Subscriber.repository.SubscriberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SubscribeService {
	private final SubscriberRepository subscriberRepository;
}
