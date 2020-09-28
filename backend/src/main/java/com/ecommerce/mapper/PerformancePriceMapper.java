package com.ecommerce.mapper;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import com.ecommerce.domain.PerformancePrice;

public interface PerformancePriceMapper {
	@Transactional
	long create(PerformancePrice performancePrice);
	
	@Transactional
	List<PerformancePrice> getByPid(long pid);
}
