package com.ecommerce.domain;

import lombok.Data;

@Data
public class Ticket
{
	private long tid;
	private long pid;
	private int seatNumber;
	private String date;
	private String time;
	private long uid;
}
