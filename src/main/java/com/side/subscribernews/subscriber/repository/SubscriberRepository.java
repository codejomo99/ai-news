package com.side.subscribernews.subscriber.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.side.subscribernews.subscriber.Subscriber;

@Repository
public interface SubscriberRepository extends JpaRepository<Subscriber, Long> {

	Optional<Subscriber> findByEmail(String email);
	Optional<Subscriber> findByToken(String token);
}
