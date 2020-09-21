package com.ecommerce.api;

import com.ecommerce.application.IPerformanceDateService;
import com.ecommerce.application.IPerformancePriceService;
import com.ecommerce.application.IPerformanceService;
import com.ecommerce.application.IPerformanceSubmissonService;
import com.ecommerce.domain.Item;
import com.ecommerce.domain.Performance;
import com.ecommerce.domain.PerformanceDate;
import com.ecommerce.domain.PerformancePrice;
import com.ecommerce.domain.exception.EmptyListException;
import com.ecommerce.domain.exception.NotFoundException;

import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
	private IPerformanceDateService performanceDateService;
	private IPerformancePriceService performancePriceService;
	@Autowired
	public PerformanceController(IPerformanceService performanceService,
			IPerformanceSubmissonService performanceSubmissonService,
			IPerformanceDateService performanceDateService,
			IPerformancePriceService performancePriceService) {
		
		this.performanceService = performanceService;
		this.performanceSubmissonService = performanceSubmissonService;
		this.performanceDateService = performanceDateService;
		this.performancePriceService = performancePriceService;
	}

	@ApiOperation(value = "모든 공연 검색")
	@RequestMapping(value = "/performance", method = RequestMethod.GET)
	public List<Performance> list() {
		List<Performance> list = performanceService.list();
		if (list == null || list.isEmpty())
			throw new EmptyListException("NO DATA");
		return list;
	}
	
	@ApiOperation(value = "공연검색 with 공연번호")
	@RequestMapping(value = "/performance/{pid}", method = RequestMethod.GET)
	public Performance get(@PathVariable long pid) {
		Performance performance = performanceService.get(pid);
		if (performance == null) {
			logger.error("NOT FOUND ID: ", pid);
			throw new NotFoundException(pid + " 공연 정보를 찾을 수 없습니다.");
		}
		return performance;
	}
	
//	@ApiOperation(value = "공연 등록,(공연등록요청, 공연날짜, 공연가격 테이블 생성")
//	@RequestMapping(value = "/performance", method = RequestMethod.POST)
//	public Performance create(@RequestBody Performance performance) {
//		return performanceService.create(performance);
//	}
	
//	@ApiOperation(value = "공연 등록 요청한것을 관리자가 승락")
//	@RequestMapping(value = "/performance/submission/{sid}", method = RequestMethod.DELETE)
//	public void delete(@PathVariable long sid) {
//		int num = performanceSubmissonService.delete(sid);
//		System.out.println("num 값은 " + num);
//	}
	
	@ApiOperation(value = "공연 날짜,시간 검색")
	@RequestMapping(value = "/performance/date/{pid}", method = RequestMethod.GET)
	public List<PerformanceDate> getPerformanceDateBypid(long pid) {
		List<PerformanceDate> list = performanceDateService.getByPid(pid);
		if (list == null || list.isEmpty())
			throw new EmptyListException("NO DATA");
		return list;
	}
	
	@ApiOperation(value = "공연 좌석,가격 검색")
	@RequestMapping(value = "/performance/price/{pid}", method = RequestMethod.GET)
	public List<PerformancePrice> getPerformancePriceBypid(long pid) {
		List<PerformancePrice> list = performancePriceService.getByPid(pid);
		if (list == null || list.isEmpty())
			throw new EmptyListException("NO DATA");
		return list;
	}
}
