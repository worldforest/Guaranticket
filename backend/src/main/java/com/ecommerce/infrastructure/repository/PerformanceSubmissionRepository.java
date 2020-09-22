package com.ecommerce.infrastructure.repository;

import com.ecommerce.domain.Item;
import com.ecommerce.domain.Performance;
import com.ecommerce.domain.PerformanceSubmission;
import com.ecommerce.domain.exception.RepositoryException;
import com.ecommerce.domain.repository.IItemRepository;
import com.ecommerce.domain.repository.IPerformanceRepository;
import com.ecommerce.domain.repository.IPerformanceSubmissionRepository;
import com.ecommerce.infrastructure.repository.factory.ItemFactory;
import com.ecommerce.infrastructure.repository.factory.PerformanceFactory;
import com.ecommerce.infrastructure.repository.factory.PerformanceSubmissionFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class PerformanceSubmissionRepository implements IPerformanceSubmissionRepository
{
	private static final Logger log = LoggerFactory.getLogger(PerformanceSubmissionRepository.class);

	private JdbcTemplate jdbcTemplate;
	private SimpleJdbcInsert simpleJdbcInsert;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<PerformanceSubmission> list() {
		StringBuilder sbSql =  new StringBuilder("SELECT * FROM performances_submission"); // where available
		try {
			return this.jdbcTemplate.query(sbSql.toString(),
							   new Object[]{}, (rs, rowNum) -> PerformanceSubmissionFactory.create(rs));
		} catch (Exception e) {
			throw new RepositoryException(e, e.getMessage());
		}
	}



//	@Override
//	public int delete(final long pid) {
//		StringBuilder sbSql =  new StringBuilder("UPDATE items ");
//		sbSql.append("SET available=? ");
//		sbSql.append("where id=?");
//
//		try {
//			return this.jdbcTemplate.update(sbSql.toString(),
//								new Object[] { false, id });
//		} catch (Exception e) {
//			throw new RepositoryException(e, e.getMessage());
//		}
//		return 0;
//	}
	@Override
	public PerformanceSubmission get(long sid) {
		StringBuilder sbSql =  new StringBuilder("SELECT * FROM performances_submission WHERE sid = ?");
		try {
			return this.jdbcTemplate.queryForObject(sbSql.toString(),
								new Object[] { sid }, (rs, rowNum) -> PerformanceSubmissionFactory.create(rs) );
		} catch (EmptyResultDataAccessException e) {
			return null;
		} catch (Exception e) {
			throw new RepositoryException(e, e.getMessage());
		}
	}
	
	@Override
	public long create(long pid, long uid) {
		try {
			Map<String, Object> paramMap = new HashMap<>();
			paramMap.put("pid", pid);
			paramMap.put("uid", uid);
			paramMap.put("submit_date", LocalDateTime.now());
			
			this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
					.withTableName("performances_submission")
					.usingGeneratedKeyColumns("sid");

			Number newId = simpleJdbcInsert.executeAndReturnKey(paramMap);
			log.debug("INSERTED: " + newId.longValue());
			return newId.longValue();
		} catch (Exception e) {
			throw new RepositoryException(e, e.getMessage());
		}
	}

	@Override
	public int delete(long sid) {
		StringBuilder sbSql =  new StringBuilder("DELETE FROM performances_submission ");
		sbSql.append("where sid = ?");
		try {
			return this.jdbcTemplate.update(sbSql.toString(),
					new Object[] { sid });
		} catch (Exception e) {
			throw new RepositoryException(e, e.getMessage());
		}
	}

	

//	@Override
//	public int update(PerformanceSubmission performancesubmission) {
//		// TODO Auto-generated method stub
//		return 0;
//	}

}
