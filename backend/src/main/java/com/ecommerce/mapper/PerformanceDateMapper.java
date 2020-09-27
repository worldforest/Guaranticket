package com.ecommerce.mapper;

import java.util.List;
import com.ecommerce.domain.PerformanceDate;

public interface PerformanceDateMapper {
	long create(PerformanceDate performancedate);
	List<PerformanceDate> getByPid(long pid);
}
