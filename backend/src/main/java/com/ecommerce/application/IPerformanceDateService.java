package com.ecommerce.application;

import com.ecommerce.domain.PerformanceDate;

import java.util.List;

public interface IPerformanceDateService {
	List<PerformanceDate> getByPid(long pid);
}