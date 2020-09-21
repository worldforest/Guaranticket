package com.ecommerce.infrastructure.repository;

import com.ecommerce.domain.Item;
import com.ecommerce.domain.Performance;
import com.ecommerce.domain.exception.RepositoryException;
import com.ecommerce.domain.repository.IItemRepository;
import com.ecommerce.domain.repository.IPerformanceRepository;
import com.ecommerce.infrastructure.repository.factory.ItemFactory;
import com.ecommerce.infrastructure.repository.factory.PerformanceFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class PerformanceRepository implements IPerformanceRepository
{
	private static final Logger log = LoggerFactory.getLogger(PerformanceRepository.class);

	private JdbcTemplate jdbcTemplate;
	private SimpleJdbcInsert simpleJdbcInsert;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<Performance> list() {
		StringBuilder sbSql =  new StringBuilder("SELECT * FROM performances "); // where available
		try {
			return this.jdbcTemplate.query(sbSql.toString(),
							   new Object[]{}, (rs, rowNum) -> PerformanceFactory.create(rs));
		} catch (Exception e) {
			throw new RepositoryException(e, e.getMessage());
		}
	}
//
//	@Override
//	public List<Performance> getByPid(long pid) {
//		StringBuilder sbSql =  new StringBuilder("SELECT * FROM performances WHERE pid=? ");
//		try {
//			return this.jdbcTemplate.query(sbSql.toString(),
//					new Object[]{ pid }, (rs, rowNum) -> PerformanceFactory.create(rs));
//		} catch (Exception e) {
//			throw new RepositoryException(e, e.getMessage());
//		}
//	}
//
	@Override
	public Performance get(long pid) {
		StringBuilder sbSql =  new StringBuilder("SELECT * FROM performances WHERE pid = ? ");
		try {
			return this.jdbcTemplate.queryForObject(sbSql.toString(),
								new Object[] { pid }, (rs, rowNum) -> PerformanceFactory.create(rs) );
		} catch (EmptyResultDataAccessException e) {
			return null;
		} catch (Exception e) {
			throw new RepositoryException(e, e.getMessage());
		}
	}
	
	@Override
	public long create(Performance performance) {
		try {
			log.debug(performance.toString());
			Map<String, Object> paramMap = new HashMap<>();
			paramMap.put("title", performance.getTitle());
			paramMap.put("poster", performance.getPoster());
			paramMap.put("category", performance.getCategory());
			paramMap.put("location", performance.getLocation());
			paramMap.put("place", performance.getPlace());
			paramMap.put("running", performance.getRunning());
			paramMap.put("term", performance.getTerm());
			paramMap.put("attendance", performance.getAttendance());
			paramMap.put("notice", performance.getNotice());
			paramMap.put("detail", performance.getDetail());
			paramMap.put("uid", performance.getUid());
			paramMap.put("permission", false);
			
			this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
					.withTableName("performances")
					.usingGeneratedKeyColumns("pid");

			Number newId = simpleJdbcInsert.executeAndReturnKey(paramMap);
			log.debug("INSERTED: " + newId.longValue());
			return newId.longValue();

		} catch (Exception e) {
			throw new RepositoryException(e, e.getMessage());
		}
	}

	@Override
	public int updatePermission(long pid) {
		StringBuilder sbSql =  new StringBuilder("UPDATE performances ");
		sbSql.append("SET permission = ? ");
		sbSql.append("where pid = ?");
		try {
			return this.jdbcTemplate.update(sbSql.toString(),
								new Object[] {
										true,
										pid
								});
		} catch (Exception e) {
			throw new RepositoryException(e, e.getMessage());
		}
	}

//	@Override
//	public int update(Performance performance) {
//		StringBuilder sbSql =  new StringBuilder("UPDATE performances ");
//		sbSql.append("SET name=?, category=?, explanation=?, available=? ");
//		sbSql.append("where id=?");
//		try {
//			return this.jdbcTemplate.update(sbSql.toString(),
//								new Object[] {
//										performance.getName(),
//										performance.getCategory(),
//										performance.getExplanation(),
//										performance.getAvailable(),
//										performance.getId()
//								});
//		} catch (Exception e) {
//			throw new RepositoryException(e, e.getMessage());
//		}
//		return 0;
//	}
//
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

}