package com.ecommerce.application;

import com.ecommerce.domain.Performance;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

public interface IPerformanceService {
	List<Performance> list();
	Performance get(long pid);

	@Transactional
	Performance create(Performance performance);
	@Transactional
	int delete(long pid);
}