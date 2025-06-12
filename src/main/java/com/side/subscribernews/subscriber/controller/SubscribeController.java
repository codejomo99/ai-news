package com.side.subscribernews.subscriber.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.side.subscribernews.subscriber.service.SubscribeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class SubscribeController {
	private final SubscribeService subscribeService;

	@PostMapping("/subscribe")
	public ResponseEntity<String> subscribe(@RequestBody Map<String, String> request) {
		subscribeService.subscribe(request.get("email"));
		return ResponseEntity.ok("인증 메일이 발송되었습니다.");
	}

	@GetMapping("/verify")
	public ResponseEntity<String> verify(@RequestParam("token") String token) {
		subscribeService.verify(token);
		return ResponseEntity.ok("구독 인증이 완료되었습니다. 감사합니다!");
	}
}
