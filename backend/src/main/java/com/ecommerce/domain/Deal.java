package com.ecommerce.domain;

import lombok.Data;

@Data
public class Deal
{
	private long did;
	private long tid;
	private long buyUid;
	private long sellUid;
	private String date;
	private String time;
	private String grade;
	private String price;
	
}
