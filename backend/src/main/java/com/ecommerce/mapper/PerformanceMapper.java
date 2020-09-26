package com.ecommerce.mapper;

import java.util.List;

import com.ecommerce.domain.Performance;
import com.ecommerce.domain.PerformanceDetail;

public interface PerformanceMapper {
	List<Performance> latestList();
	List<Performance> list();
	Performance get(long pid);

	long create(Performance performance);
	int delete(long pid);
	int updatePermission(long pid);
}
