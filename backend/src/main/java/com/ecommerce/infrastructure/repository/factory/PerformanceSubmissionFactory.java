package com.ecommerce.infrastructure.repository.factory;

import com.ecommerce.domain.PerformanceSubmission;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PerformanceSubmissionFactory
{
	public static PerformanceSubmission create(ResultSet rs) throws SQLException
	{
		if (rs == null) return null;
		PerformanceSubmission performanceSubmission = new PerformanceSubmission();
		performanceSubmission.setPid(rs.getLong("pid"));
		performanceSubmission.setUid(rs.getLong("uid"));
		performanceSubmission.setSubmissionDate(rs.getTimestamp("submit_date").toLocalDateTime());
		return performanceSubmission;
	}
}
