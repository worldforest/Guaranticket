package com.ecommerce.domain;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

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
	private String term;
	private String attendance;
	private String notice;
	private String detail;
	private Boolean permission;
	private long uid;
	
	private ArrayList<String> prices;
	private ArrayList<String> grades;
	
	private ArrayList<String> dates;
}
