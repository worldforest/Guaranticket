package com.ecommerce.infrastructure.repository.factory;

import com.ecommerce.domain.Deal;
import com.ecommerce.domain.DealList;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DealListFactory
{
	public static DealList create(ResultSet rs) throws SQLException
	{
		if (rs == null) return null;
		DealList dealJoinData = new DealList();
		dealJoinData.setDid(rs.getLong("did"));
		dealJoinData.setTid(rs.getLong("tid"));
		dealJoinData.setBuyer(rs.getLong("buyer"));
		dealJoinData.setSeller(rs.getLong("seller"));
		dealJoinData.setDate(rs.getString("date"));
		dealJoinData.setTime(rs.getString("time"));
		dealJoinData.setGrade(rs.getString("grade"));
		dealJoinData.setPrice(rs.getString("price"));
		dealJoinData.setCategory(rs.getNString("category"));
		dealJoinData.setTitle(rs.getString("title"));
		return dealJoinData;
	}
}
