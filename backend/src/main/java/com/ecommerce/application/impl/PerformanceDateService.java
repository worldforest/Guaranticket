package com.ecommerce.application.impl;

import com.ecommerce.application.IPerformanceDateService;
import com.ecommerce.domain.PerformanceDate;
import com.ecommerce.mapper.PerformanceDateMapper;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class PerformanceDateService implements IPerformanceDateService
{
	public static final Logger logger = LoggerFactory.getLogger(PerformanceDateService.class);

	@Autowired
	private PerformanceDateMapper performanceDateMapper;
	
	@Override
	public List<PerformanceDate> getByPid(long pid) {
		// TODO Auto-generated method stub
		return this.performanceDateMapper.getByPid(pid);
	}


}
