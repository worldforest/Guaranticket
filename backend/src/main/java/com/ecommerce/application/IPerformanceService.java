package com.ecommerce.application;

import com.ecommerce.domain.Performance;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

public interface IPerformanceService {
	List<Performance> list();
//	List<Performance> getByPid(int pid);
	Performance get(long pid);

	@Transactional
	Performance create(Performance performance);

//	@Transactional
//	Performance update(Performance performance);
//
//	@Transactional
//	Performance delete(long pid);
	
}