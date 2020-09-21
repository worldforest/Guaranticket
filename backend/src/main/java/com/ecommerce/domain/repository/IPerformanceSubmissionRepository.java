package com.ecommerce.domain.repository;

import com.ecommerce.domain.PerformanceSubmission;
import org.springframework.transaction.annotation.Transactional;

public interface IPerformanceSubmissionRepository
{
//	List<PerformanceSubmission> list();
	
	PerformanceSubmission get(long sid);

	@Transactional
	long create(long pid, long uid);

//	@Transactional
//	int update(PerformanceSubmission performancesubmission);
//
	@Transactional
	int delete(long sid);
}
