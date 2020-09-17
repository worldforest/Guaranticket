package com.ecommerce.domain.repository;

import com.ecommerce.domain.Performance;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

public interface IPerformanceRepository
{
//	List<Performance> list();
//	List<Performance> getByPid(final long pid);
	Performance get(long pid);
	
	@Transactional
	long create(Performance performance);

//	@Transactional
//	int update(Performance Performance);
//
//	@Transactional
//	int delete(long pid);
	
	@Transactional
	int updatePermission(long pid);
}
