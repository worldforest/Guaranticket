package com.ecommerce.infrastructure.repository;

import com.ecommerce.domain.PerformancePrice;
import com.ecommerce.domain.exception.RepositoryException;
import com.ecommerce.domain.repository.IPerformancePriceRepository;
import com.ecommerce.infrastructure.repository.factory.PerformancePriceFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class PerformancePriceRepository implements IPerformancePriceRepository
{
	private static final Logger log = LoggerFactory.getLogger(PerformancePriceRepository.class);

	private JdbcTemplate jdbcTemplate;
	private SimpleJdbcInsert simpleJdbcInsert;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	
	@Override
	public long create(PerformancePrice performancePrice) {
		try {
			Map<String, Object> paramMap = new HashMap<>();
			paramMap.put("pid", performancePrice.getPid());
			paramMap.put("price", performancePrice.getPrice());
			paramMap.put("grade", performancePrice.getGrade());
			
			this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
					.withTableName("performances_price");
			return simpleJdbcInsert.execute(paramMap);
			

		} catch (Exception e) {
			throw new RepositoryException(e, e.getMessage());
		}
	}


	@Override
	public List<PerformancePrice> getByPid(long pid) {
		StringBuilder sbSql =  new StringBuilder("SELECT * FROM performances_price WHERE pid=? ");
		try {
			return this.jdbcTemplate.query(sbSql.toString(),
					new Object[]{ pid }, (rs, rowNum) -> PerformancePriceFactory.create(rs));
		} catch (Exception e) {
			throw new RepositoryException(e, e.getMessage());
		}
	}


}
