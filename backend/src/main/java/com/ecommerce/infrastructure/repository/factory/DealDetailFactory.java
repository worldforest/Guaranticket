package com.ecommerce.infrastructure.repository.factory;

import com.ecommerce.domain.DealDetail;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DealDetailFactory
{
	public static DealDetail create(ResultSet rs) throws SQLException
	{
		if (rs == null) return null;
		DealDetail dealDetail = new DealDetail();
		dealDetail.setDid(rs.getLong("did"));
		dealDetail.setTid(rs.getLong("tid"));
		dealDetail.setBuyer(rs.getLong("buyer"));
		dealDetail.setSeller(rs.getLong("seller"));
		dealDetail.setDate(rs.getString("date"));
		dealDetail.setTime(rs.getString("time"));
		dealDetail.setGrade(rs.getString("grade"));
		dealDetail.setPrice(rs.getString("price"));
		dealDetail.setCategory(rs.getNString("category"));
		dealDetail.setTitle(rs.getString("title"));
		dealDetail.setPoster(rs.getString("poster"));
		dealDetail.setSeatNumber(rs.getString("seat_number"));
		dealDetail.setPlace(rs.getString("place"));
		return dealDetail;
	}
}
