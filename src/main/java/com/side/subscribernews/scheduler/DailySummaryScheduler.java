package com.side.subscribernews.scheduler;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.side.subscribernews.news.NewsArticle;
import com.side.subscribernews.news.NewsCrawler;
import com.side.subscribernews.subscriber.Subscriber;
import com.side.subscribernews.subscriber.service.SubscriberService;
import com.side.subscribernews.summary.Summary;
import com.side.subscribernews.summary.repository.SummaryRepository;
import com.side.subscribernews.util.GeminiClient;
import com.side.subscribernews.util.MailService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class DailySummaryScheduler {

	private final SubscriberService subscriberService;
	private final MailService mailService;
	private final GeminiClient geminiClient;
	private final SummaryRepository summaryRepository;
	private final NewsCrawler newsCrawler;

	// 매일 오전 8시에 실행
	@Scheduled(cron = "0 0 8 * * *", zone = "Asia/Seoul")
	public void sendDailySummaries() throws IOException {
		log.info("🕗 스케줄러 실행 시작: AI 요약 메일 전송");

		// 1. 인증된 구독자 목록 조회
		List<Subscriber> subscriberList = subscriberService.getVerifiedSubscribers();
		log.info("✅ 인증된 구독자 (총 {}명)", subscriberList.size());

		// 2. 오늘 요약 존재하는지 확인
		String today = LocalDate.now(ZoneId.of("Asia/Seoul")).toString();
		Optional<Summary> optionalSummary = summaryRepository.findByDate(today);

		// 3. 정보를 크롤링
		log.info("크롤링 시작 !!");
		List<NewsArticle> allArticles = new ArrayList<>();
		allArticles.addAll(newsCrawler.fetchHaniNews());
		allArticles.addAll(newsCrawler.fetchSeoulNews());
		allArticles.addAll(newsCrawler.fetchYna());
		allArticles.addAll(newsCrawler.fetchJoongang());
		log.info("크롤링 끝 !!");

		String summary;
		if (optionalSummary.isPresent()) {
			summary = optionalSummary.get().getContent();
		} else {
			summary = geminiClient.getDailyNewsSummary(allArticles).block();
			summaryRepository.save(Summary.builder()
				.date(today)
				.content(summary)
				.build());

			log.info("🆕 새 요약 생성 및 저장");
		}

		// 3. 각 구독자에게 이메일 전송
		for (Subscriber subscriber : subscriberList) {
			mailService.sendNews(subscriber.getEmail(), summary);
		}

		log.info("✅ 요약 뉴스 발송 완료 (총 {}명)", subscriberList.size());
	}

	public void testSendDailySummaries() throws IOException {
		sendDailySummaries();
	}
}
