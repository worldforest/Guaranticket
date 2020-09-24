package com.ecommerce.infrastructure.repository;

import com.ecommerce.domain.Deal;
import com.ecommerce.domain.DealDetail;
import com.ecommerce.domain.DealList;
import com.ecommerce.domain.exception.RepositoryException;
import com.ecommerce.domain.repository.IDealRepository;
import com.ecommerce.infrastructure.repository.factory.DealDetailFactory;
import com.ecommerce.infrastructure.repository.factory.DealFactory;
import com.ecommerce.infrastructure.repository.factory.DealListFactory;

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
public class DealRepository implements IDealRepository
{
	private static final Logger log = LoggerFactory.getLogger(DealRepository.class);

	private JdbcTemplate jdbcTemplate;
	private SimpleJdbcInsert simpleJdbcInsert;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<DealList> list() {
		StringBuilder sbSql =  new StringBuilder("SELECT * FROM deals as a ");
		sbSql.append("inner join tickets as b on a.tid = b.tid ");
		sbSql.append("inner join performances as c on b.pid = c.pid ");
		try {
			return this.jdbcTemplate.query(sbSql.toString(),
							   new Object[]{}, (rs, rowNum) -> DealListFactory.create(rs));
		} catch (Exception e) {
			throw new RepositoryException(e, e.getMessage());
		}
	}

	@Override
	public DealDetail get(long did) {
		StringBuilder sbSql =  new StringBuilder("SELECT * FROM deals as a ");
		sbSql.append("inner join tickets as b on a.tid = b.tid ");
		sbSql.append("inner join performances as c on b.pid = c.pid ");
		sbSql.append("where a.did = ? ");
		try {
			return this.jdbcTemplate.queryForObject(sbSql.toString(),
								new Object[] { did }, (rs, rowNum) -> DealDetailFactory.create(rs) );
		} catch (EmptyResultDataAccessException e) {
			return null;
		} catch (Exception e) {
			throw new RepositoryException(e, e.getMessage());
		}
	}

	@Override
	public long create(Deal deal) {
		try {
			Map<String, Object> paramMap = new HashMap<>();
			paramMap.put("tid", deal.getTid());
			paramMap.put("buyer", deal.getBuyer());
			paramMap.put("seller", deal.getSeller());
			paramMap.put("date", deal.getDate());
			paramMap.put("time", deal.getTime());
			paramMap.put("grade", deal.getGrade());
			paramMap.put("price", deal.getPrice());
			
			
			this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
					.withTableName("deals")
					.usingGeneratedKeyColumns("did");

			Number newId = simpleJdbcInsert.executeAndReturnKey(paramMap);
			log.debug("INSERTED: " + newId.longValue());
			return newId.longValue();

		} catch (Exception e) {
			throw new RepositoryException(e, e.getMessage());
		}
	}

	@Override
	public List<DealList> getBySeller(long seller) {
		StringBuilder sbSql =  new StringBuilder("SELECT * FROM deals as a "); // where available
		sbSql.append("inner join tickets as b on a.tid = b.tid ");
		sbSql.append("inner join performances as c on b.pid = c.pid ");
		sbSql.append("where seller = ? ");
		try {
			return this.jdbcTemplate.query(sbSql.toString(),
							   new Object[]{seller}, (rs, rowNum) -> DealListFactory.create(rs));
		} catch (Exception e) {
			throw new RepositoryException(e, e.getMessage());
		}
	}

	

}
