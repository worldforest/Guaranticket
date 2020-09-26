package com.ecommerce.domain.repository;

import com.ecommerce.domain.Performance;
import com.ecommerce.domain.PerformanceDetail;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

public interface IPerformanceRepository
{
	List<Performance> latestList();
	List<Performance> list();
//	List<Performance> getByPid(final long pid);
	PerformanceDetail get(long pid);
	
	@Transactional
	long create(Performance performance);

//	@Transactional
//	int update(Performance Performance);
//
	@Transactional
	int delete(long pid);
	
	@Transactional
	int updatePermission(long pid);

}
