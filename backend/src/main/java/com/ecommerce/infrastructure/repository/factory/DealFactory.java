package com.ecommerce.infrastructure.repository.factory;

import com.ecommerce.domain.Deal;
import com.ecommerce.domain.Performance;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DealFactory
{
	public static Deal create(ResultSet rs) throws SQLException
	{
		if (rs == null) return null;
		Deal deal = new Deal();
		deal.setDid(rs.getInt("did"));
		deal.setTid(rs.getInt("tid"));
		deal.setBuyUid(rs.getInt("buy_uid"));
		deal.setSellUid(rs.getInt("sell_uid"));
		deal.setDate(rs.getDate("date").toString());
		deal.setTime(rs.getTime("time").toString());
		deal.setGrade(rs.getString("grade"));
		deal.setPrice(rs.getString("price"));
		return deal;
	}
}
