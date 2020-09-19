package com.ecommerce.infrastructure.repository.factory;

import com.ecommerce.domain.PerformancePrice;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PerformancePriceFactory
{
	public static PerformancePrice create(ResultSet rs) throws SQLException
	{
		if (rs == null) return null;
		PerformancePrice performancePrice = new PerformancePrice();
		performancePrice.setPid(rs.getLong("pid"));
		performancePrice.setGrade(rs.getString("grade"));
		performancePrice.setPrice(rs.getString("price"));
		return performancePrice;
	}
}
