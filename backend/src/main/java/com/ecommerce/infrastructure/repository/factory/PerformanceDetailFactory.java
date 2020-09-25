package com.ecommerce.infrastructure.repository.factory;

import com.ecommerce.domain.PerformanceDetail;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PerformanceDetailFactory
{
	public static PerformanceDetail create(ResultSet rs) throws SQLException
	{
		if (rs == null) return null;
		PerformanceDetail performanceDetail = new PerformanceDetail();
		performanceDetail.setPid(rs.getLong("pid"));
		performanceDetail.setTitle(rs.getString("title"));
		performanceDetail.setPoster(rs.getString("poster"));
		performanceDetail.setCategory(rs.getString("category"));
		performanceDetail.setLocation(rs.getString("location"));
		performanceDetail.setPlace(rs.getString("place"));
		performanceDetail.setRunning(rs.getString("running"));
		performanceDetail.setTicketingStartDate(rs.getString("ticketing_start_date"));
		performanceDetail.setTicketingEndDate(rs.getString("ticketing_end_date"));
		performanceDetail.setStartDate(rs.getString("start_date"));
		performanceDetail.setEndDate(rs.getString("end_date"));
		performanceDetail.setAttendance(rs.getString("attendance"));
		performanceDetail.setNotice(rs.getString("notice"));
		performanceDetail.setDetail(rs.getString("detail"));
		performanceDetail.setPermission(rs.getBoolean("permission"));
		performanceDetail.setUid(rs.getLong("uid"));
		
		return performanceDetail;
	}
}
