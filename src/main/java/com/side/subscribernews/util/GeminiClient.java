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
		String prompt = content + "\n\n 추가 할 예정"; // 추가 할 예정

		Map<String, Object> requestBody = Map.of(
			"contents", new Object[]{
				Map.of("parts", new Object[]{
					Map.of("text", prompt)
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
}

