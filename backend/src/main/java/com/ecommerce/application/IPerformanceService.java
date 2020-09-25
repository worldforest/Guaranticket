package com.ecommerce.application;

import com.ecommerce.domain.Performance;
import com.ecommerce.domain.PerformanceDetail;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

public interface IPerformanceService {
	List<Performance> latestList();
	List<Performance> list();
	PerformanceDetail get(long pid);

	@Transactional
	PerformanceDetail create(PerformanceDetail performanceAllData);
	@Transactional
	int delete(long pid);
}