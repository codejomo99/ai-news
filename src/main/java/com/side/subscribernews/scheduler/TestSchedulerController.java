package com.side.subscribernews.scheduler;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/admin/test")
@RequiredArgsConstructor
public class TestSchedulerController {

	private final DailySummaryScheduler scheduler;

	@GetMapping("/run-scheduler")
	public String runScheduler() {
		scheduler.sendDailySummaries();
		return "스케줄러 수동 실행 완료!";

	}
}
