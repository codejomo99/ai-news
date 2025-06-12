package com.side.subscribernews.subscriber;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Subscriber {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true)
	private String email;

	private String token;
	// true 일 때만 메일 발송 대상
	private boolean isVerified;
	// true 일 때만 살아있는 구독자
	private boolean isActive;
	private LocalDateTime createdAt;

	public void updateVerified(Subscriber subscriber) {
		subscriber.isVerified = true;
	}
}
