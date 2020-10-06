package com.ecommerce.mapper;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.domain.PerformanceSubmission;

public interface PerformanceSubmissionMapper {
	List<PerformanceSubmission> list();
	
	PerformanceSubmission get(long sid);

	@Transactional
	long create(PerformanceSubmission performanceSubmission);

	@Transactional
	int delete(long sid);
}
