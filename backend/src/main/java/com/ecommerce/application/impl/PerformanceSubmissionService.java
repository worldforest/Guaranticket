package com.ecommerce.application.impl;

import com.ecommerce.application.IPerformanceService;
import com.ecommerce.application.IPerformanceSubmissonService;
import com.ecommerce.domain.Performance;
import com.ecommerce.domain.PerformanceSubmission;
import com.ecommerce.domain.repository.IPerformanceRepository;
import com.ecommerce.domain.repository.IPerformanceSubmissionRepository;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class PerformanceSubmissionService implements IPerformanceSubmissonService
{
	public static final Logger logger = LoggerFactory.getLogger(PerformanceSubmissionService.class);

	private IPerformanceRepository iPerformanceRepository;
	private IPerformanceSubmissionRepository iPerformanceSubmissionRepository;

	@Autowired
	public PerformanceSubmissionService(IPerformanceRepository iPerformanceRepository,
			IPerformanceSubmissionRepository iPerformanceSubmissionRepository) {
		this.iPerformanceRepository = iPerformanceRepository;
		this.iPerformanceSubmissionRepository = iPerformanceSubmissionRepository;
	}

	@Override
	public PerformanceSubmission create(Performance performance) {		
		long sid = this.iPerformanceSubmissionRepository.create(performance.getPid(), performance.getUid());
		return this.iPerformanceSubmissionRepository.get(sid);
	}

	@Override
	public int delete(long sid) {
		PerformanceSubmission performanceSubmission = this.iPerformanceSubmissionRepository.get(sid);
		// 공연 테이블의 등록여부 true로 업데이트
		this.iPerformanceRepository.updatePermission(performanceSubmission.getPid());
		return this.iPerformanceSubmissionRepository.delete(sid);
	}

	@Override
	public List<PerformanceSubmission> list() {
		// TODO Auto-generated method stub
		return this.iPerformanceSubmissionRepository.list();
	}


	

}
