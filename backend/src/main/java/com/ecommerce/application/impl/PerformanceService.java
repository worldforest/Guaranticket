package com.ecommerce.application.impl;

import com.ecommerce.application.IPerformanceService;
import com.ecommerce.domain.Performance;
import com.ecommerce.domain.PerformanceDetail;
import com.ecommerce.domain.PerformanceDate;
import com.ecommerce.domain.PerformancePrice;
import com.ecommerce.domain.repository.IPerformanceDateRepository;
import com.ecommerce.domain.repository.IPerformancePriceRepository;
import com.ecommerce.domain.repository.IPerformanceRepository;
import com.ecommerce.domain.repository.IPerformanceSubmissionRepository;
import com.ecommerce.mapper.PerformanceMapper;
import com.ecommerce.mapper.UserMapper;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class PerformanceService implements IPerformanceService
{
	public static final Logger logger = LoggerFactory.getLogger(PerformanceService.class);

	private IPerformanceRepository iPerformanceRepository;
	private IPerformanceSubmissionRepository iPerformanceSubmissionRepository;
	private IPerformancePriceRepository iPerformancePriceRepository;
	private IPerformanceDateRepository iPerformanceDateRepository;
	
	@Autowired
	public PerformanceService(IPerformanceRepository iPerformanceRepository,
			IPerformanceSubmissionRepository iPerformanceSubmissionRepository,
			IPerformancePriceRepository iPerformancePriceRepository,
			IPerformanceDateRepository iPerformanceDateRepository) {
		
		this.iPerformanceRepository = iPerformanceRepository;
		this.iPerformanceSubmissionRepository = iPerformanceSubmissionRepository;
		this.iPerformancePriceRepository = iPerformancePriceRepository;
		this.iPerformanceDateRepository = iPerformanceDateRepository;
	}

	@Autowired
	private PerformanceMapper performanceMapper;

	@Override
	public List<Performance> latestList() {
		return this.performanceMapper.latestList();
	}
	@Override
	public List<Performance> list() {
		return this.performanceMapper.list();
	}

	@Override
	public Performance get(long pid) {
		// TODO Auto-generated method stub
		return this.performanceMapper.get(pid);
	}

	@Override
	public PerformanceDetail create(PerformanceDetail performanceAllData) {
		// TODO Auto-generated method stub
		
		Performance performance = new Performance();
		performance.setTitle(performanceAllData.getTitle());
		performance.setPoster(performanceAllData.getPoster());
		performance.setCategory(performanceAllData.getTitle());
		performance.setLocation(performanceAllData.getTitle());
		performance.setPlace(performanceAllData.getTitle());
		performance.setRunning(performanceAllData.getRunning());
		performance.setTicketingStartDate(performanceAllData.getTicketingStartDate());
		performance.setTicketingEndDate(performanceAllData.getTicketingEndDate());
		performance.setStartDate(performanceAllData.getStartDate());
		performance.setEndDate(performanceAllData.getEndDate());
		performance.setAttendance(performanceAllData.getAttendance());
		performance.setNotice(performanceAllData.getNotice());
		performance.setDetail(performanceAllData.getDetail());
		performance.setUid(performanceAllData.getUid());
		performance.setPermission(performanceAllData.getPermission());
		
		long pid = this.performanceMapper.create(performance);
		
//		// 등록요청 db생성
		List<String> grades = performanceAllData.getGrades();
		List<String> prices = performanceAllData.getPrices();
//		// 공연 가격 정보 저장
		PerformancePrice performancePrice = null;
		for (int i = 0; i < grades.size(); i++) {
			performancePrice = new PerformancePrice();
			performancePrice.setPid(pid);
			performancePrice.setGrade(grades.get(i));
			performancePrice.setPrice(prices.get(i));
			this.iPerformancePriceRepository.create(performancePrice);
		}
//		// 공연 날짜 정보 저장
		PerformanceDate performancedate = null;
		List<String>times = performanceAllData.getTimes();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-DD");
		
		Date start = null;
		Date end = null;
		try {
			start = df.parse(performanceAllData.getStartDate());
			end	= df.parse(performanceAllData.getEndDate());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Calendar startDate = Calendar.getInstance();
		Calendar endDate = Calendar.getInstance();
		
		startDate.setTime(start);
		endDate.setTime(end);
		// 시작날짜가 끝 날짜보다 작거나 같은 경우
		while(startDate.compareTo(endDate) != 1) {
			performancedate = new PerformanceDate();
			performancedate.setPid(pid);
			performancedate.setDate(df.format(startDate.getTime()));
			// 날짜 마다 시간대 반복
			for (String time : times) {
				performancedate.setTime(time);
				this.iPerformanceDateRepository.create(performancedate);
			}
			// 하루 증가
			startDate.add(Calendar.DATE, 1);
		}
		System.out.println(performanceAllData);
//		// 공연 등록 요청 정보 저장
		this.iPerformanceSubmissionRepository.create(pid, performanceAllData.getUid());
        return this.iPerformanceRepository.get(pid);
	}

//	@Override
//	public Performance update(Performance performance) {
//		// TODO Auto-generated method stub
//		this.iPerformanceRepository.update(performance);
//		return null;
//	}
//
	@Override
	public int delete(long pid) {
		// TODO Auto-generated method stub
		return this.performanceMapper.delete(pid);
	}

}
