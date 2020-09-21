package com.ecommerce.application;

import com.ecommerce.domain.PerformancePrice;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

public interface IPerformancePriceService {
	List<PerformancePrice> getByPid(long pid);

}