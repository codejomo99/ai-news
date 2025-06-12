package com.side.subscribernews.subscriber.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import com.side.subscribernews.subscriber.service.SubscriberService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class SubscriberController {
	private final SubscriberService subscriberService;

	@PostMapping("/subscribe")
	public ResponseEntity<String> subscribe(@RequestBody Map<String, String> request) {
		subscriberService.subscribe(request.get("email"));
		return ResponseEntity.ok("인증 메일이 발송되었습니다.");
	}

	@GetMapping("/verify")
	public RedirectView verify(@RequestParam("token") String token) {
		subscriberService.verify(token);
		return new RedirectView("/verify-success.html");
	}

	@PostMapping("/unsubscribe")
	public ResponseEntity<String> unsubscribe(@RequestBody Map<String, String> request) {
		subscriberService.unsubscribe(request.get("email"));
		return ResponseEntity.ok("인증 메일이 발송되었습니다.");
	}

	@GetMapping("/verify-unsubscribe")
	public RedirectView verifyUnsubscribe(@RequestParam("token") String token) {
		subscriberService.verifyUnsubscribe(token);
		return new RedirectView("/unsubscribe-success.html");
	}
}
