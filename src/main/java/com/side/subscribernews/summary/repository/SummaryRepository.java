package com.side.subscribernews.summary.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.side.subscribernews.summary.Summary;

@Repository
public interface SummaryRepository extends JpaRepository<Summary, Long> {
	Optional<Summary> findByDate(String today);
}
