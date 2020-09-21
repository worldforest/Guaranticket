package com.ecommerce.application;

import com.ecommerce.domain.PerformanceDate;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

public interface IPerformanceDateService {
	List<PerformanceDate> getByPid(long pid);

}