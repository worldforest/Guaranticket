package com.ecommerce.domain;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Deal
{
	private int did;
	private int tid;
	private int buyUid;
	private int sellUid;
	private String date;
	private String time;
	private String grade;
	private String price;
	
}
