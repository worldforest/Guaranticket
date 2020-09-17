package com.ecommerce.api;

import com.ecommerce.application.IPerformanceService;
import com.ecommerce.application.IPerformanceSubmissonService;
import com.ecommerce.domain.Item;
import com.ecommerce.domain.Performance;
import com.ecommerce.domain.PerformanceSubmission;
import com.ecommerce.domain.User;
import com.ecommerce.domain.exception.EmptyListException;
import com.ecommerce.domain.exception.NotFoundException;
import com.ecommerce.domain.repository.IPerformanceSubmissionRepository;

import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class PerformanceController
{
	public static final Logger logger = LoggerFactory.getLogger(PerformanceController.class);

	private IPerformanceService performanceService;
	
	private IPerformanceSubmissonService performanceSubmissonService;

	@Autowired
	public PerformanceController(IPerformanceService performanceService,
			IPerformanceSubmissonService performanceSubmissonService) {
		Assert.notNull(performanceService, "performanceService 개체가 반드시 필요!");
		Assert.notNull(performanceSubmissonService, "performanceSubmissonService 개체가 반드시 필요!");
		
		this.performanceService = performanceService;
		this.performanceSubmissonService = performanceSubmissonService;
	}

//	@ApiOperation(value = "Fetch all performances")
//	@RequestMapping(value = "/performance", method = RequestMethod.GET)
//	public List<Performance> list() {
//		List<Performance> list = performanceService.list();
//
//		if (list == null || list.isEmpty())
//			throw new EmptyListException("NO DATA");
//
//		return list;
//	}
//	
//	@ApiOperation(value = "Fetch an performance with pid")
//	@RequestMapping(value = "/performance/{pid}", method = RequestMethod.GET)
//	public Performance get(@PathVariable int pid) {
//		Performance performance = performanceService.get(pid);
//		if (performance == null) {
//			logger.error("NOT FOUND ID: ", pid);
//			throw new NotFoundException(pid + " 공연 정보를 찾을 수 없습니다.");
//		}
//		return performance;
//	}
	
	@ApiOperation(value = "공연 등록(공연등록요청, 공연날짜, 공연가격 테이블 생성")
	@RequestMapping(value = "/performance", method = RequestMethod.POST)
	public Performance create(@RequestBody Performance performance) {
		return performanceService.create(performance);
	}
	
//	@ApiOperation(value = "update an performance")
//	@RequestMapping(value = "/performance", method = RequestMethod.PUT)
//    public Performance update(@RequestBody Performance performance) {
//        return performanceService.update(performance);
//    }
//	
//	@ApiOperation(value = "delete an performance")
//	@RequestMapping(value = "/users/{pid}", method = RequestMethod.DELETE)
//	public void delete(@PathVariable int pid) {
//		performanceService.delete(pid);
//	}
	
	
	@ApiOperation(value = "공연등록요청한것을 관리자가 승락(요청리스트에서 삭제되고 공연테이블의 등록여부(permission)값이 true로 변경)")
	@RequestMapping(value = "/performanceSubmission/{sid}", method = RequestMethod.DELETE)
	public void delete(@PathVariable long sid) {
		int num = performanceSubmissonService.delete(sid);
		System.out.println("num 값은 " + num);
	}
}
