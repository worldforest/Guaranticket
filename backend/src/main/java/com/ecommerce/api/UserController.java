package com.ecommerce.api;

import com.ecommerce.application.IUserService;
import com.ecommerce.application.impl.JwtService;
import com.ecommerce.domain.User;
import com.ecommerce.domain.exception.DomainException;
import com.ecommerce.domain.exception.EmptyListException;
import com.ecommerce.domain.exception.NotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class UserController {
	public static final Logger logger = LoggerFactory.getLogger(UserController.class);

	private IUserService userService;

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private JwtService jwtService;

	@Autowired
	public UserController(IUserService userService) {
		Assert.notNull(userService, "userService 개체가 반드시 필요!");
		this.userService = userService;
	}
	
	// 기업회원 가입 신청 리스트 가져오기
	@RequestMapping(value = "/use", method = RequestMethod.GET)
	public List<User> getlist() {
		List<User> userList = userService.list();

		if (userList == null || userList.isEmpty())
			throw new EmptyListException("NO DATA");

		return userList;
	}
	
	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public List<User> list() {
		List<User> userList = userService.list();

		if (userList == null || userList.isEmpty())
			throw new EmptyListException("NO DATA");

		return userList;
	}

	@RequestMapping(value = "/users/info", method = RequestMethod.GET)
	public Object get(HttpServletRequest request) {
		String token = request.getHeader("jwt-auth-token");
		Map<String, Object> userInfo = jwtService.get(token);
		long uid = Long.parseLong(userInfo.get("USER").toString());
		Map<String, Object> result = new 
				HashMap<>();
		result.put("status", true);
		result.put("data", uid);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
	public Object get(@PathVariable int id) {
		User user = userService.get(id);
		Map<String, Object> result = new HashMap<>();
		if (user == null) {
			logger.error("NOT FOUND ID: ", id);
			throw new NotFoundException(id + " 회원 정보를 찾을 수 없습니다.");
		}

//		result.putAll(jwtService.get(request.getHeader("jwt-auth-token")));
//		result.put("status", true);
		result.put("data", user);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@RequestMapping(value = "/users/email/{email}", method = RequestMethod.GET)
	public Object get(@PathVariable String email) {
		User user = userService.get(email);
		Map<String, Object> result = new HashMap<>();
		if (user == null) {
			result.put("status", true);
			result.put("data", "");
		} else {
			result.put("status", false);
			result.put("data", "isExist");
		}
//		if (user == null) {
//			logger.error("NOT FOUND EMAIL: ", email);
//			throw new NotFoundException(email + " 회원 정보를 찾을 수 없습니다.");
//		}
//		result.putAll(jwtService.get(request.getHeader("jwt-auth-token")));
//		result.put("status", true);
//		result.put("data", user);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@RequestMapping(value = "/users/login", method = RequestMethod.POST)
	public Object login(@RequestBody User user, HttpServletResponse response) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		User userFetched = userService.get(user.getEmail());
		Map<String, Object> result = new HashMap<>();
		if (!passwordEncoder.matches(user.getPassword(), userFetched.getPassword())) {
			result.put("status", false);
			result.put("data", "request invalid");
		}
		else {
			String token = jwtService.create(userFetched.getId());
			response.setHeader("jwt-auth-token", token);
			result.put("status", true);
			result.put("data", token);
			
		}
		return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
	}

	@RequestMapping(value = "/users", method = RequestMethod.POST)
	public User create(@RequestBody User user) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		User newUser = userService.add(user);
		System.out.println(newUser);
		return newUser;
	}
	
	// 회원정보 수정 (비밀번호 미포함)
	@RequestMapping(value = "/users", method = RequestMethod.PUT)
	public Object update(@RequestBody User user, HttpServletRequest request) {
		Map<String, Object> result = new HashMap<>();
		user = userService.update(user);
		result.putAll(jwtService.get(request.getHeader("jwt-auth-token")));
		result.put("status", true);
		result.put("data", user);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	// 비밀번호 변경
	@RequestMapping(value = "/users/pw", method = RequestMethod.PUT)
	public Object update(@RequestBody Map<String, String> params, HttpServletRequest request) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		Map<String, Object> result = new HashMap<>();
		
		String token = request.getHeader("jwt-auth-token");
		Map<String, Object> userInfo = jwtService.get(token);
		long uid = Long.parseLong(userInfo.get("USER").toString());
		User user = userService.get(uid);

		String originPassword = params.get("originPassword");
		String newPassword = params.get("newPassword");
		
		// 입력한 현재 비밀번호와 DB에 저장된 비밀번호가 다르면
		if(!passwordEncoder.matches(originPassword, user.getPassword())) {
			result.put("status", false);
			result.put("data", "INVALID_PASSWORD");
		}
		// 입력한 새 비밀번호와 DB에 저장된 비밀먼호가 같으면
		else if(passwordEncoder.matches(newPassword, user.getPassword())) {
			result.put("status", false);
			result.put("data", "SAME_PASSWORD");
		}
		// 유효하면
		else {
			user.setPassword(passwordEncoder.encode(newPassword));
			user = userService.update(user);
			result.put("status", true);
			result.put("data", user);
		}
		result.putAll(jwtService.get(request.getHeader("jwt-auth-token")));
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@RequestMapping(value = "/users/{id}", method = RequestMethod.DELETE)
	public void delete(@PathVariable long id) {
		userService.delete(id);
	}

	// 비밀번호 찾기
	@RequestMapping(value = "/users/findpw", method = RequestMethod.POST)
	public User get(@RequestBody User user) {
		User users = userService.get(user.getEmail());
		if (users == null || !users.getName().equals(user.getName())) {
			logger.error("NOT FOUND : ", user.getName());
			throw new NotFoundException(user.getName() + " 회원 정보를 찾을 수 없습니다.");
		}
		return users;
	}

	// 비밀번호 찾기
	@RequestMapping(value = "/users/sendemail", method = RequestMethod.POST)
	public User sendEmail(@RequestBody User users) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		User user = userService.get(users.getEmail());
		// 임시 비밀번호 생성 저장 변수
		String userPwd = "";
		String encryptPwd = "";
		for (int i = 0; i < 12; i++) {
			userPwd += (char) ((Math.random() * 26) + 97);
		}
		userPwd += (int) (Math.random() * 9) + 1;
		encryptPwd = passwordEncoder.encode(userPwd);

		// 임시비밀번호로 업데이트
		user.setPassword(encryptPwd);
		userService.update(user);
		
		// 메일보내기
		try {
			MimeMessage msg = mailSender.createMimeMessage();
			MimeMessageHelper messageHelper = new MimeMessageHelper(msg, true, "UTF-8");
			messageHelper.setFrom("tmdwkd2.gmail.com"); // 보내는사람의 주소(없으면 동작x)
			messageHelper.setTo(user.getEmail()); // 받는사람의 주소
			messageHelper.setSubject(user.getName() + "님의 비밀번호 찾기 메일입니다."); // 제목
			messageHelper.setText("임시 패스워드는  " + userPwd + " 입니다."); // 내용
			msg.setRecipients(MimeMessage.RecipientType.TO, InternetAddress.parse(user.getEmail()));
			mailSender.send(msg);
		} catch (MessagingException e) {

		}
		return user;
	}

	// SMS 인증
	@GetMapping("/users/sms")
	public String sendSMS(String phoneNumber) {
		Random rand = new Random();
		String certNum = "";
		for (int i = 0; i < 4; i++) {
			String ran = Integer.toString(rand.nextInt(10));
			certNum += ran;
		}
		userService.certifiedPhoneNumber(phoneNumber, certNum);
		return certNum;
	}
}
