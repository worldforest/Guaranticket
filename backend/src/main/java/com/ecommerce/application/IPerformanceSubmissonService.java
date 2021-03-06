package com.ecommerce.application;

import com.ecommerce.domain.Performance;
import com.ecommerce.domain.PerformanceSubmission;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

public interface IPerformanceSubmissonService {
	List<PerformanceSubmission> list();
//	List<PerformanceSubmission> getByPid(int pid);
//	PerformanceSubmission get(long pid);

	@Transactional
	PerformanceSubmission create(PerformanceSubmission performanceSubmission);
		
//	@Transactional
//	PerformanceSubmission update(Performance performance);

	@Transactional
	int delete(long sid);
	
}