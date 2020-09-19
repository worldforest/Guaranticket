package com.ecommerce.domain;

import java.sql.Date;
import java.sql.Time;
import lombok.Data;


@Data
public class PerformanceDate
{
	private long pid;
	private Date date;
	private Time time;
}

