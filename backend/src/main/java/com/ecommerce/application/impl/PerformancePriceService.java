package com.ecommerce.application.impl;

import com.ecommerce.application.IPerformanceDateService;
import com.ecommerce.application.IPerformancePriceService;
import com.ecommerce.application.IPerformanceService;
import com.ecommerce.domain.Performance;
import com.ecommerce.domain.PerformanceDate;
import com.ecommerce.domain.PerformancePrice;
import com.ecommerce.domain.repository.IPerformanceDateRepository;
import com.ecommerce.domain.repository.IPerformancePriceRepository;
import com.ecommerce.domain.repository.IPerformanceRepository;
import com.ecommerce.domain.repository.IPerformanceSubmissionRepository;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class PerformancePriceService implements IPerformancePriceService
{
	public static final Logger logger = LoggerFactory.getLogger(PerformancePriceService.class);

	private IPerformancePriceRepository iPerformancePriceRepository;
	
	@Autowired
	public PerformancePriceService(IPerformancePriceRepository iPerformancePriceRepository) {
		this.iPerformancePriceRepository = iPerformancePriceRepository;
	}

	@Override
	public List<PerformancePrice> getByPid(long pid) {
		// TODO Auto-generated method stub
		return this.iPerformancePriceRepository.getByPid(pid);
	}
	


}
