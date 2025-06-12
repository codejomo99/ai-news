package com.side.subscribernews.Subscriber.controller;

import org.springframework.web.bind.annotation.RestController;

import com.side.subscribernews.Subscriber.service.SubscribeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class SubscribeController {
	private final SubscribeService subscribeService;
}
