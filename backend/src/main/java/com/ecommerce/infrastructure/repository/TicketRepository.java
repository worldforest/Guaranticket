package com.ecommerce.infrastructure.repository;

import com.ecommerce.domain.Deal;
import com.ecommerce.domain.Item;
import com.ecommerce.domain.Performance;
import com.ecommerce.domain.Ticket;
import com.ecommerce.domain.exception.RepositoryException;
import com.ecommerce.domain.repository.IDealRepository;
import com.ecommerce.domain.repository.IItemRepository;
import com.ecommerce.domain.repository.IPerformanceRepository;
import com.ecommerce.domain.repository.ITicketRepository;
import com.ecommerce.infrastructure.repository.factory.DealFactory;
import com.ecommerce.infrastructure.repository.factory.ItemFactory;
import com.ecommerce.infrastructure.repository.factory.PerformanceFactory;
import com.ecommerce.infrastructure.repository.factory.TicketFactory;

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
public class TicketRepository implements ITicketRepository
{
	private static final Logger log = LoggerFactory.getLogger(TicketRepository.class);

	private JdbcTemplate jdbcTemplate;
	private SimpleJdbcInsert simpleJdbcInsert;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<Ticket> getByPid(long pid) {
		StringBuilder sbSql =  new StringBuilder("SELECT * FROM tickets WHERE pid = ? ");
		try {
			return this.jdbcTemplate.query(sbSql.toString(),
							   new Object[]{pid}, (rs, rowNum) -> TicketFactory.create(rs));
		} catch (Exception e) {
			throw new RepositoryException(e, e.getMessage());
		}
	}

	@Override
	public List<Ticket> getByUid(long uid) {
		StringBuilder sbSql =  new StringBuilder("SELECT * FROM tickets WHERE uid = ? ");
		try {
			return this.jdbcTemplate.query(sbSql.toString(),
								new Object[] { uid }, (rs, rowNum) -> TicketFactory.create(rs) );
		} catch (EmptyResultDataAccessException e) {
			return null;
		} catch (Exception e) {
			throw new RepositoryException(e, e.getMessage());
		}
	}

	@Override
	public long create(Ticket ticket) {
		try {
			Map<String, Object> paramMap = new HashMap<>();
			paramMap.put("uid", ticket.getUid());
			paramMap.put("pid", ticket.getPid());
			paramMap.put("seat_number",ticket.getSeatNumber());
			paramMap.put("date",ticket.getDate());
			
			this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
					.withTableName("tickets")
					.usingGeneratedKeyColumns("tid");

			Number newId = simpleJdbcInsert.executeAndReturnKey(paramMap);
			log.debug("INSERTED: " + newId.longValue());
			return newId.longValue();

		} catch (Exception e) {
			throw new RepositoryException(e, e.getMessage());
		}
	}


}
