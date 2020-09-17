package com.ecommerce.domain;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class User {
	private long id;
	private String name;
	private String email;
	private LocalDateTime signupDate;
	private String password;
	private String gender;
	private int updatePwd;
	private String birth;
	private int type;
	private String companyName;
	private String businessNumber;
	private String representativeName;
}
