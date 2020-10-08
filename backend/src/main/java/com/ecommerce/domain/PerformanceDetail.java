package com.ecommerce.domain;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;


@Data
public class PerformanceDetail
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

	private List<String> prices;
	private List<String> grades;
	private List<String> times;
	private long uid;
}
