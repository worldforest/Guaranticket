package com.ecommerce.domain.repository;

import com.ecommerce.domain.PerformanceDate;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

public interface IPerformanceDateRepository
{
	@Transactional
	long create(PerformanceDate performancedate);
	
	@Transactional
	List<PerformanceDate> getByPid(final long pid);
}
