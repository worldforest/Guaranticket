package com.ecommerce.domain;

import lombok.Data;


@Data
public class Performance
{
	private long pid;
	private String title;
	private String poster;
	private String category;
	private String location;
	private String place;
	private String running;
	private String ticketingStartDate;
	private String ticketingEndDate;
	private String startDate;
	private String endDate;
	private String attendance;
	private String notice;
	private String detail;
	private Boolean permission;
	private long uid;
}
