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
	private String term;
	private String attendance;
	private String notice;
	private String detail;
	private Boolean permission =false;
	@Override
	public String toString() {
		return "Performance [pid=" + pid + ", title=" + title + ", poster=" + poster + ", category=" + category
				+ ", location=" + location + ", place=" + place + ", running=" + running + ", term=" + term
				+ ", attendance=" + attendance + ", notice=" + notice + ", detail=" + detail + ", permission="
				+ permission + "]";
	}
	
}

