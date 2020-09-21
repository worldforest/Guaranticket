package com.ecommerce.domain;

import lombok.Data;

@Data
public class Deal
{
	private long did;
	private long tid;
	private long buyer;
	private long seller;
	private String date;
	private String time;
	private String grade;
	private String price;
	
}
