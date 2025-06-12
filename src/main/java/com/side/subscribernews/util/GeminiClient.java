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
			지금 시각 기준, 대한민국 주요 언론사(예: 조선일보, 동아일보, 한겨레 등)에서 가장 많이 언급되고 있는 뉴스 3개를 요약해줘.
			
			요약 형식은 다음과 같아:
			
			1. 📰 제목: [뉴스 제목]
				    📌 요약: [핵심 내용 (3줄 이내)]
			 		🔗 링크: [출처 URL 또는 생략 가능]
			
			2. 📰 제목: ...
			📌 요약: ...
			 		🔗 링크: ...
			
			3. 📰 제목: ...
			 		📌 요약: ...
			 		🔗 링크: ...
			
			가능한 한 명확하고 깔끔하게 요약해줘. 그리고 요약한 내용만 보내주고 다른 내용을 일절 보내지 마
			""";

		return summarize(prompt);
	}
}

