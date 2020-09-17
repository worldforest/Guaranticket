package com.ecommerce.api;

import com.ecommerce.application.IPerformanceService;
import com.ecommerce.domain.Item;
import com.ecommerce.domain.Performance;
import com.ecommerce.domain.User;
import com.ecommerce.domain.exception.EmptyListException;
import com.ecommerce.domain.exception.NotFoundException;
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

	@Autowired
	public PerformanceController(IPerformanceService performanceService) {
		Assert.notNull(performanceService, "performanceService 개체가 반드시 필요!");
		this.performanceService = performanceService;
	}

	@ApiOperation(value = "Fetch all performances")
	@RequestMapping(value = "/performance", method = RequestMethod.GET)
	public List<Performance> list() {
		List<Performance> list = performanceService.list();

		if (list == null || list.isEmpty())
			throw new EmptyListException("NO DATA");

		return list;
	}
	
	@ApiOperation(value = "Fetch an performance with pid")
	@RequestMapping(value = "/performance/{pid}", method = RequestMethod.GET)
	public Performance get(@PathVariable int pid) {
		Performance performance = performanceService.get(pid);
		if (performance == null) {
			logger.error("NOT FOUND ID: ", pid);
			throw new NotFoundException(pid + " 공연 정보를 찾을 수 없습니다.");
		}
		return performance;
	}
	
	@ApiOperation(value = "create an performance")
	@RequestMapping(value = "/performance", method = RequestMethod.POST)
	public Performance create(@RequestBody Performance performance) {
		return performanceService.create(performance);
	}
	
	@ApiOperation(value = "update an performance")
	@RequestMapping(value = "/performance", method = RequestMethod.PUT)
    public Performance update(@RequestBody Performance performance) {
        return performanceService.update(performance);
    }
	
	@ApiOperation(value = "delete an performance")
	@RequestMapping(value = "/users/{pid}", method = RequestMethod.DELETE)
	public void delete(@PathVariable int pid) {
		performanceService.delete(pid);
	}
	
}
