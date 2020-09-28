package com.ecommerce.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PerformanceSubmission{
	private long sid;
	private long uid;
	private long pid;
	private LocalDateTime submitDate;
	
}
