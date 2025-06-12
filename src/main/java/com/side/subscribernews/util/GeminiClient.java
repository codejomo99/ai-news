package com.side.subscribernews.util;

import java.util.Map;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
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

	public Mono<String> getDailyNewsSummary() {
		String prompt = """
			오늘 날짜 기준으로 대한민국에서 주요 언론사들이 가장 많이 보도한 뉴스 3건을 알려줘.
			각 뉴스는 다음과 같은 형식으로 제공해줘:
			
			1. 📰 제목: [뉴스 제목] \s
			📌 요약: [뉴스 요약] \s
			🔗 링크: 반드시 실제 기사 URL 포함
			
			기사 링크는 Yonhap, Chosun, Hankyoreh, KBS, JTBC 등의 신뢰할 수 있는 뉴스 사이트에서 선택해줘.
			링크가 없으면 절대 '(링크 없음)' 같은 문장을 넣지 말고, 최소한 유사한 기사라도 찾아서 넣어줘. 형식만 보내주고 다른 말은 절대 보내지 마
			""";

		return summarize(prompt);
	}
}

