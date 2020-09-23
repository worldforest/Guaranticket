package com.ecommerce.infrastructure.repository;

import com.ecommerce.domain.Ticket;
import com.ecommerce.domain.TicketJoinData;
import com.ecommerce.domain.exception.RepositoryException;
import com.ecommerce.domain.repository.ITicketRepository;
import com.ecommerce.infrastructure.repository.factory.TicketFactory;
import com.ecommerce.infrastructure.repository.factory.TicketJoinDataFactory;

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
	public List<TicketJoinData> getByUid(long uid) {
		StringBuilder sbSql =  new StringBuilder("SELECT * FROM tickets as a ");
		sbSql.append("inner join performances as b on a.pid = b.pid ");
		sbSql.append("where a.uid = ? ");
		try {
			return this.jdbcTemplate.query(sbSql.toString(),
								new Object[] { uid }, (rs, rowNum) -> TicketJoinDataFactory.create(rs) );
		} catch (EmptyResultDataAccessException e) {
			return null;
		} catch (Exception e) {
			throw new RepositoryException(e, e.getMessage());
		}
	}
	@Override
	public Ticket get(long tid) {
		StringBuilder sbSql =  new StringBuilder("SELECT * FROM tickets WHERE tid = ? ");
		try {
			return this.jdbcTemplate.queryForObject(sbSql.toString(),
								new Object[] { tid }, (rs, rowNum) -> TicketFactory.create(rs) );
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
			paramMap.put("time",ticket.getTime());
			paramMap.put("grade",ticket.getGrade());
			paramMap.put("price",ticket.getPrice());
			
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

	@Override
	public List<Ticket> getByPidAndDateAndTime(long pid, String date, String time) {
		StringBuilder sbSql =  new StringBuilder("SELECT * FROM tickets WHERE pid = ? ");
		sbSql.append("AND date = ? ");
		sbSql.append("AND time = ?");
		try {
			return this.jdbcTemplate.query(sbSql.toString(),
							   new Object[]{pid, date, time}, (rs, rowNum) -> TicketFactory.create(rs));
		} catch (Exception e) {
			throw new RepositoryException(e, e.getMessage());
		}
	}
	
}
