package com.ecommerce.application.impl;

import com.ecommerce.application.IPerformanceService;
import com.ecommerce.domain.Performance;
import com.ecommerce.domain.repository.IPerformanceRepository;
import com.ecommerce.domain.repository.IPerformanceSubmissionRepository;

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

	@Autowired
	public PerformanceService(IPerformanceRepository iPerformanceRepository,
			IPerformanceSubmissionRepository iPerformanceSubmissionRepository) {
		this.iPerformanceRepository = iPerformanceRepository;
		this.iPerformanceSubmissionRepository = iPerformanceSubmissionRepository;
	}

//	@Override
//	public List<Performance> list() {
//		// TODO Auto-generated method stub
//		return this.iPerformanceRepository.list();
//	}
//
//	@Override
//	public List<Performance> getByPid(int pid) {
//		// TODO Auto-generated method stub
//		return this.iPerformanceRepository.getByPid(pid);
//	}
//
	@Override
	public Performance get(long pid) {
		// TODO Auto-generated method stub
		return this.iPerformanceRepository.get(pid);
	}

	@Override
	public Performance create(Performance performance) {
		// TODO Auto-generated method stub
		long pid = this.iPerformanceRepository.create(performance);
		// 등록요청 db생성
		this.iPerformanceSubmissionRepository.create(pid, performance.getUid());
		
        return this.iPerformanceRepository.get(pid);
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
