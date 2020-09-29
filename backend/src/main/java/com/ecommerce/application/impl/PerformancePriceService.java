package com.ecommerce.application.impl;

import com.ecommerce.application.IPerformancePriceService;
import com.ecommerce.domain.PerformancePrice;
import com.ecommerce.mapper.PerformancePriceMapper;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class PerformancePriceService implements IPerformancePriceService
{
	public static final Logger logger = LoggerFactory.getLogger(PerformancePriceService.class);

	@Autowired
	private PerformancePriceMapper performancePriceMapper;
	
	@Override
	public List<PerformancePrice> getByPid(long pid) {
		// TODO Auto-generated method stub
		return this.performancePriceMapper.getByPid(pid);
	}
	


}
