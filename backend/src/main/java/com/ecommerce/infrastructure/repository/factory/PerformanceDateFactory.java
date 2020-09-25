package com.ecommerce.infrastructure.repository.factory;

import com.ecommerce.domain.PerformanceDate;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PerformanceDateFactory
{
	public static PerformanceDate create(ResultSet rs) throws SQLException
	{
		if (rs == null) return null;
		PerformanceDate performanceDate = new PerformanceDate();
		performanceDate.setPid(rs.getLong("pid"));
		performanceDate.setDate(rs.getString("date"));
		performanceDate.setTime(rs.getString("time"));
		return performanceDate;
	}
}
