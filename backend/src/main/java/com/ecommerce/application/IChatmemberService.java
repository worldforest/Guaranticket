package com.ecommerce.application;

import java.util.List;

public interface IChatmemberService {
	public boolean insert(String no, String name);

	public List<String> selectAll(String name);

	public String select(String myname, String yourname);
}
