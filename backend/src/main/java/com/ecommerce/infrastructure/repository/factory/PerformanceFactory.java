package com.ecommerce.infrastructure.repository.factory;

import com.ecommerce.domain.Performance;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PerformanceFactory
{
	public static Performance create(ResultSet rs) throws SQLException
	{
		if (rs == null) return null;
		Performance performance = new Performance();
		performance.setPid(rs.getLong("pid"));
		performance.setTitle(rs.getString("title"));
		performance.setPoster(rs.getString("poster"));
		performance.setCategory(rs.getString("category"));
		performance.setLocation(rs.getString("location"));
		performance.setPlace(rs.getString("place"));
		performance.setRunning(rs.getString("running"));
		performance.setTerm(rs.getString("term"));
		performance.setAttendance(rs.getString("attendance"));
		performance.setNotice(rs.getString("notice"));
		performance.setDetail(rs.getString("detail"));
		performance.setPermission(rs.getBoolean("permission"));
		return performance;
	}
}
