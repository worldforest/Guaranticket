package com.ecommerce.application.impl;

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

	@Override
	public List<Performance> list() {
		// TODO Auto-generated method stub
//		List<Performance> list = null;
//		list = this.iPerformanceRepository.list();
//		if(list != null) {
//			for (Performance performance : list) {
//				long pid = performance.getPid();
//				// 가격 테이블 정보 더하기
//				List<PerformancePrice>pricelist = this.iPerformancePriceRepository.getByPid(pid);
//				ArrayList<String> grades = new ArrayList<>();
//				ArrayList<String> prices = new ArrayList<>();
//				for (PerformancePrice performancePrice : pricelist) {
//					grades.add(performancePrice.getPrice());
//					prices.add(performancePrice.getGrade());
//				}
//				performance.setGrades(grades);
//				performance.setPrices(prices);
//				// 날짜 테이블 정보 더하기
//				List<PerformanceDate>datelist = this.iPerformanceDateRepository.getByPid(pid);
//				ArrayList<String> dates = new ArrayList<>();
//				for (PerformanceDate performanceDate : datelist) {
//					StringBuilder sb = new StringBuilder();
//					sb.append(performanceDate.getDate()+" ");
//					sb.append(performanceDate.getTime());
//					dates.add(sb.toString());
//				}
//				performance.setDates(dates);
//			}
//		}
		return this.iPerformanceRepository.list();
	}

//	@Override
//	public List<Performance> getByPid(int pid) {
//		// TODO Auto-generated method stub
//		return this.iPerformanceRepository.getByPid(pid);
//	}
//
	@Override
	public Performance get(long pid) {
//		// TODO Auto-generated method stub
//		Performance performance = null;
//		// 공연 테이블 정보
//		performance = this.iPerformanceRepository.get(pid);
//		if(performance != null) {
//			// 가격 테이블 정보 더하기
//			List<PerformancePrice>pricelist = this.iPerformancePriceRepository.getByPid(pid);
//			ArrayList<String> grades = new ArrayList<>();
//			ArrayList<String> prices = new ArrayList<>();
//			for (PerformancePrice performancePrice : pricelist) {
//				grades.add(performancePrice.getPrice());
//				prices.add(performancePrice.getGrade());
//			}
//			performance.setGrades(grades);
//			performance.setPrices(prices);
//			// 날짜 테이블 정보 더하기
//			List<PerformanceDate>datelist = this.iPerformanceDateRepository.getByPid(pid);
//			ArrayList<String> dates = new ArrayList<>();
//			for (PerformanceDate performanceDate : datelist) {
//				StringBuilder sb = new StringBuilder();
//				sb.append(performanceDate.getDate()+" ");
//				sb.append(performanceDate.getTime());
//				dates.add(sb.toString());
//			}
//			performance.setDates(dates);
//		}
//		return performance;
		
		return null;
	}

	@Override
	public Performance create(Performance performance) {
		// TODO Auto-generated method stub
//		long pid = this.iPerformanceRepository.create(performance);
//		// 등록요청 db생성
//		ArrayList<String> grades = performance.getGrades();
//		ArrayList<String> prices = performance.getPrices();
//		// 공연 가격 정보 저장
//		PerformancePrice performancePrice = null;
//		for (int i = 0; i < grades.size(); i++) {
//			performancePrice = new PerformancePrice();
//			performancePrice.setPid(pid);
//			performancePrice.setGrade(grades.get(i));
//			performancePrice.setPrice(prices.get(i));
//			this.iPerformancePriceRepository.create(performancePrice);
//		}
//		// 공연 날짜 정보 저장
//		PerformanceDate performancedate = null;
//		ArrayList<String> dates = performance.getDates();
//		for (int i = 0; i < dates.size(); i++) {
//			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//			Date tmpdate = null;
//			try {
//				tmpdate = dateFormat.parse(dates.get(i));
//				performancedate = new PerformanceDate();
//				performancedate.setPid(pid);
//				performancedate.setDate(new java.sql.Date(tmpdate.getTime()));
//				performancedate.setTime(new Time(tmpdate.getTime()));
////				System.out.println(performancedate);
//				this.iPerformanceDateRepository.create(performancedate);
//				
//			} catch (ParseException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//		// 공연 등록 요청 정보 저장
//		this.iPerformanceSubmissionRepository.create(pid, performance.getUid());
		
        return null;
	}

//	@Override
//	public Performance update(Performance performance) {
//		// TODO Auto-generated method stub
//		this.iPerformanceRepository.update(performance);
//		return null;
//	}
//
//	@Override
//	public Performance delete(long pid) {
//		// TODO Auto-generated method stub
//		this.iPerformanceRepository.delete(pid);
//		return null;
//	}

}
