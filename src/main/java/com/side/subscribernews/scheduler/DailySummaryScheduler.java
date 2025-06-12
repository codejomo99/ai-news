package com.side.subscribernews.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class DailySummaryScheduler {

	// 매일 오전 8시에 실행
	@Scheduled(cron = "0 0 8 * * *", zone = "Asia/Seoul")
	public void sendDailySummaries() {
		log.info("🕗 스케줄러 실행 시작: AI 요약 메일 전송");

		// 여기에 요약 생성 및 이메일 전송 로직 추가 예정
	}
}
