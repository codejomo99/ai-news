package com.side.subscribernews.news;

import static org.assertj.core.api.AssertionsForInterfaceTypes.*;


import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class NewsCrawlerTest {

	@Test
	@DisplayName("연합 뉴스 크롤링 성공")
	void fetchYna_정상작동_확인() throws Exception {
		// given
		NewsCrawler crawler = new NewsCrawler();
		// when
		List<NewsArticle> articles = crawler.fetchYna();
		// then
		assertThat(articles).isNotEmpty();
		for (NewsArticle article : articles) {
			System.out.println("📰 제목: " + article.getTitle());
			System.out.println("🔗 링크: " + article.getUrl());
			System.out.println("📄 본문 일부: " + article.getContent().substring(0, Math.min(100, article.getContent().length())));
			System.out.println("-------------------------------------------------");
		}
	}

	@Test
	@DisplayName("중앙일보 크롤링 성공")
	void fetchJoonang_정상작동_확인() throws Exception {
		// given
		NewsCrawler crawler = new NewsCrawler();
		// when
		List<NewsArticle> articles = crawler.fetchJoongang();
		// then
		assertThat(articles).isNotEmpty();
		for (NewsArticle article : articles) {
			System.out.println("📰 제목: " + article.getTitle());
			System.out.println("🔗 링크: " + article.getUrl());
			System.out.println("📄 본문 일부: " + article.getContent().substring(0, Math.min(100, article.getContent().length())));
			System.out.println("-------------------------------------------------");
		}
	}

}