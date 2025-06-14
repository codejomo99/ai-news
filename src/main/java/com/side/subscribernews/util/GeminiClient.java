package com.side.subscribernews.util;

import java.util.List;
import java.util.Map;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.side.subscribernews.news.NewsArticle;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class GeminiClient {

	private final WebClient webClient = WebClient.builder()
		.baseUrl("https://generativelanguage.googleapis.com")
		.build();

	@Value("${spring.gemini.secretKey}")
	private String apiKey;

	private Mono<String> summarize(String content) {

		Map<String, Object> requestBody = Map.of(
			"contents", new Object[] {
				Map.of("parts", new Object[] {
					Map.of("text", content)
				})
			}
		);

		return webClient.post()
			.uri("/v1/models/gemini-2.0-flash:generateContent?key=" + apiKey)
			.header("Content-Type", "application/json")
			.bodyValue(requestBody)
			.retrieve()
			.bodyToMono(String.class)
			.map(this::extractSummaryFromResponse);
	}

	private String extractSummaryFromResponse(String response) {
		JsonObject jsonResponse = JsonParser.parseString(response).getAsJsonObject();
		JsonArray candidates = jsonResponse.getAsJsonArray("candidates");
		JsonObject firstCandidates = candidates.get(0).getAsJsonObject();
		JsonObject content = firstCandidates.getAsJsonObject("content");
		JsonArray parts = content.getAsJsonArray("parts");

		return parts.get(0).getAsJsonObject().get("text").getAsString();
	}

	public Mono<String> getDailyNewsSummary(List<NewsArticle> articles) {
		StringBuilder sb = new StringBuilder();

		// 프롬프트 도입부
		sb.append("""
        다음은 여러 언론사(연합뉴스, 중앙일보 등)에서 실시간으로 보도한 주요 뉴스 목록입니다.

        이 뉴스들에서 제목과 본문에 공통적으로 많이 등장한 단어나 키워드를 기준으로, 유사한 주제를 가진 뉴스들을 자동으로 분류해주세요,
       
        그리고 그 중 언급량이 많은 3개의 주제를 선택하여, 각 주제별로 대표 뉴스 하나를 링크에 접속해서 형식에 맞게 요약해주세요.
       
       
        형식은 반드시 아래처럼 출력해주세요:

        📰 제목: [뉴스 제목]
        📌 요약: [뉴스 핵심 요약]
        🔗 링크: [뉴스 URL]

        (아래는 뉴스 데이터입니다)

        """);

		int maxLength = 18000;  // Gemini 안전 기준
		int currentLength = sb.length();

		for (int i = 0; i < articles.size(); i++) {
			NewsArticle article = articles.get(i);

			// 기사 본문이 너무 길면 자름
			String content = article.getContent();
			content = content.length() > 100 ? content.substring(0, 100) + "..." : content;

			String entry = String.format("""
            기사 %d
            제목: %s
            본문: %s
            링크: %s

            """, i + 1, article.getTitle(), content, article.getUrl());

			// 추가했을 때 총 길이 초과하면 break
			if (currentLength + entry.length() > maxLength) break;

			sb.append(entry);
			currentLength += entry.length();
		}
		log.info("요청 글자 수: {}",sb.length());
		return summarize(sb.toString());
	}
}

