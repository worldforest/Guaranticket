package com.ecommerce.domain.repository;

import com.ecommerce.domain.PerformancePrice;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

public interface IPerformancePriceRepository
{
	@Transactional
	long create(PerformancePrice performancePrice);
	
	@Transactional
	List<PerformancePrice> getByPid(long pid);
}
