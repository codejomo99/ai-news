package com.side.subscribernews.scheduler;


import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.side.subscribernews.subscriber.Subscriber;
import com.side.subscribernews.subscriber.repository.SubscriberRepository;

@SpringBootTest
class TestSchedulerControllerTest {
	@Autowired
	private DailySummaryScheduler dailySummaryScheduler;

	@Autowired
	private SubscriberRepository subscriberRepository;

	@BeforeEach
	void setUp() {
		subscriberRepository.save(Subscriber.builder()
			.email("test@example.com")
			.verified(true)
			.active(true)
			.createdAt(LocalDateTime.now())
			.build());

		subscriberRepository.save(Subscriber.builder()
			.email("codejomo99@gmail.com")
			.verified(true)
			.active(true)
			.createdAt(LocalDateTime.now())
			.build());
	}

	@Test
	@DisplayName("스케줄러 성공")
	void testSummaryEmailSend() {
		dailySummaryScheduler.testSendDailySummaries();
	}
}