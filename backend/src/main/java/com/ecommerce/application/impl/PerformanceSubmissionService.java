package com.ecommerce.application.impl;

import com.ecommerce.application.IPerformanceSubmissonService;
import com.ecommerce.domain.Performance;
import com.ecommerce.domain.PerformanceSubmission;
import com.ecommerce.mapper.PerformanceMapper;
import com.ecommerce.mapper.PerformanceSubmissionMapper;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class PerformanceSubmissionService implements IPerformanceSubmissonService
{
	public static final Logger logger = LoggerFactory.getLogger(PerformanceSubmissionService.class);

	@Autowired
	private PerformanceMapper performanceMapper;
	@Autowired
	private PerformanceSubmissionMapper performanceSubmissionMapper;
	
	@Override
	public PerformanceSubmission create(PerformanceSubmission performanceSubmission) {		
		this.performanceSubmissionMapper.create(performanceSubmission);
		return this.performanceSubmissionMapper.get(performanceSubmission.getUid());
	}

	@Override
	public int delete(long sid) {
		PerformanceSubmission performanceSubmission = this.performanceSubmissionMapper.get(sid);
		// 공연 테이블의 등록여부 true로 업데이트
		this.performanceMapper.updatePermission(performanceSubmission.getPid());
		return this.performanceSubmissionMapper.delete(sid);
	}

	@Override
	public List<PerformanceSubmission> list() {
		// TODO Auto-generated method stub
		return this.performanceSubmissionMapper.list();
	}
}
