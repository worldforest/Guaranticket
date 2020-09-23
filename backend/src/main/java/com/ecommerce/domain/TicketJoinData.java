package com.ecommerce.domain;

import lombok.Data;

@Data
public class TicketJoinData
{
	private long tid;
	private long pid;
	private long uid;
	private int seatNumber;
	private String date;
	private String time;
	private String grade;
	private String price;
	private String category;
	private String title;
}