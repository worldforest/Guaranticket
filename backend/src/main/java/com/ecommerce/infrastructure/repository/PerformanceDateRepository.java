package com.ecommerce.infrastructure.repository;

import com.ecommerce.domain.PerformanceDate;
import com.ecommerce.domain.PerformancePrice;
import com.ecommerce.domain.exception.RepositoryException;
import com.ecommerce.domain.repository.IPerformanceDateRepository;
import com.ecommerce.domain.repository.IPerformancePriceRepository;
import com.ecommerce.infrastructure.repository.factory.PerformanceDateFactory;
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
public class PerformanceDateRepository implements IPerformanceDateRepository
{
	private static final Logger log = LoggerFactory.getLogger(PerformanceDateRepository.class);

	private JdbcTemplate jdbcTemplate;
	private SimpleJdbcInsert simpleJdbcInsert;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public long create(PerformanceDate performanceDate) {
		try {
			Map<String, Object> paramMap = new HashMap<>();
			paramMap.put("pid", performanceDate.getPid());
			paramMap.put("date", performanceDate.getDate());
			paramMap.put("time", performanceDate.getTime());
			
			this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
					.withTableName("performances_date");
			
			return simpleJdbcInsert.execute(paramMap);
		} catch (Exception e) {
			throw new RepositoryException(e, e.getMessage());
		}
	}

	@Override
	public List<PerformanceDate> getByPid(long pid) {
		StringBuilder sbSql =  new StringBuilder("SELECT * FROM performances_date WHERE pid = ? ");
		try {
			return this.jdbcTemplate.query(sbSql.toString(),
					new Object[]{ pid }, (rs, rowNum) -> PerformanceDateFactory.create(rs));
		} catch (Exception e) {
			throw new RepositoryException(e, e.getMessage());
		}
	}


}
