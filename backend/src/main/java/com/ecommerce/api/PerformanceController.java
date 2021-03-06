package com.ecommerce.api;

import com.ecommerce.application.IFileService;
import com.ecommerce.application.IJwtService;
import com.ecommerce.application.IPerformanceDateService;
import com.ecommerce.application.IPerformancePriceService;
import com.ecommerce.application.IPerformanceService;
import com.ecommerce.application.IPerformanceSubmissonService;
import com.ecommerce.domain.Performance;
import com.ecommerce.domain.PerformanceDetail;
import com.ecommerce.domain.PerformanceDate;
import com.ecommerce.domain.PerformancePrice;
import com.ecommerce.domain.PerformanceSubmission;
import com.ecommerce.domain.exception.EmptyListException;
import com.ecommerce.domain.exception.NotFoundException;

import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
	private IFileService fileService;
	private IJwtService jwtService;
	
	@Autowired
	public PerformanceController(IPerformanceService performanceService,
			IPerformanceSubmissonService performanceSubmissonService,
			IPerformanceDateService performanceDateService,
			IPerformancePriceService performancePriceService,
			IFileService fileService,
			IJwtService jwtService) {
		
		this.performanceService = performanceService;
		this.performanceSubmissonService = performanceSubmissonService;
		this.performanceDateService = performanceDateService;
		this.performancePriceService = performancePriceService;
		this.fileService = fileService;
		this.jwtService = jwtService;
	}
	@ApiOperation(value = "카테고리별 최근순 5개씩 총 15개 공연 검색")
	@RequestMapping(value = "/performance/latest", method = RequestMethod.GET)
	public List<Performance> latestList() {
		List<Performance> list = performanceService.latestList();
		if (list == null || list.isEmpty())
			throw new EmptyListException("NO DATA");
		return list;
	}
	@ApiOperation(value = "모든 공연 검색")
	@RequestMapping(value = "/performance", method = RequestMethod.GET)
	public List<Performance> list() {
		List<Performance> list = performanceService.list();
		if (list == null || list.isEmpty())
			throw new EmptyListException("NO DATA");
		return list;
	}
	
	@ApiOperation(value = "공연 상세 검색")
	@RequestMapping(value = "/performance/{pid}", method = RequestMethod.GET)
	public Performance get(@PathVariable long pid) {
		Performance performance = performanceService.get(pid);
		if (performance == null) {
			logger.error("NOT FOUND ID: ", pid);
			throw new NotFoundException(pid + " 공연 정보를 찾을 수 없습니다.");
		}
		return performance;
	}
	
	@ApiOperation(value = "공연 등록")
	@RequestMapping(value = "/performance", method = RequestMethod.POST)
	public Performance create(@RequestBody PerformanceDetail performanceDetail, HttpServletRequest request) {
		String token = request.getHeader("jwt-auth-token");
		Map<String,Object>userinfo = jwtService.get(token);
		long uid = (Long.parseLong(userinfo.get("USER").toString()));
		performanceDetail.setUid(uid);
		return performanceService.create(performanceDetail);
	}
	
	@ApiOperation(value = "공연 삭제")
	@RequestMapping(value = "/performance/{pid}", method = RequestMethod.DELETE)
	public Object delete(@PathVariable long pid) {
		ResponseEntity<String> result = new ResponseEntity<>("success",HttpStatus.OK);
		int num = performanceService.delete(pid);
		if(num > 0) {
			return new ResponseEntity<>("success",HttpStatus.OK);
		}
		return new ResponseEntity<>("fail",HttpStatus.OK);
	}
	
	@ApiOperation(value = "공연 등록 요청한것을 관리자가 승락")
	@RequestMapping(value = "/performance/submission/{sid}", method = RequestMethod.DELETE)
	public Object deleteSubmission(@PathVariable long sid) {
		ResponseEntity<String> result = new ResponseEntity<>("success",HttpStatus.OK);
		int num = performanceSubmissonService.delete(sid);
		if(num > 0) {
			return new ResponseEntity<>("success",HttpStatus.OK);
		}
		return new ResponseEntity<>("fail",HttpStatus.OK);
	}
	
	@ApiOperation(value = "공연 날짜,시간 검색")
	@RequestMapping(value = "/performance/date/{pid}", method = RequestMethod.GET)
	public List<PerformanceDate> getPerformanceDateBypid(@PathVariable long pid) {
		List<PerformanceDate> list = performanceDateService.getByPid(pid);
		if (list == null || list.isEmpty())
			throw new EmptyListException("NO DATA");
		return list;
	}
	
	@ApiOperation(value = "공연 좌석,가격 검색")
	@RequestMapping(value = "/performance/price/{pid}", method = RequestMethod.GET)
	public List<PerformancePrice> getPerformancePriceBypid(@PathVariable long pid) {
		List<PerformancePrice> list = performancePriceService.getByPid(pid);
		if (list == null || list.isEmpty())
			throw new EmptyListException("NO DATA");
		return list;
	}
	
	@ApiOperation(value = "공연 등록 요청리스트 검색")
	@RequestMapping(value = "/performance/submission", method = RequestMethod.GET)
	public List<PerformanceSubmission> Submissionlist() {
		List<PerformanceSubmission> list = performanceSubmissonService.list();
		if (list == null || list.isEmpty())
			throw new EmptyListException("NO DATA");
		return list;
	}
	
	
}
