package com.ecommerce.domain;

import lombok.Data;

@Data
public class DealDetail
{
	private long did;
	private long tid;
	private long buyer;
	private long seller;
	private String date;
	private String time;
	private String grade;
	private String price;
	private String category;
	private String title;
	private String place;
	private String seatNumber;
	private String poster;
}
