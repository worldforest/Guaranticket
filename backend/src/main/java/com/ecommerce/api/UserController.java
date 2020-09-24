package com.ecommerce.api;

import com.ecommerce.application.IUserService;
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

import java.util.List;
import java.util.Random;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class UserController {
	public static final Logger logger = LoggerFactory.getLogger(UserController.class);

	private IUserService userService;

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	public UserController(IUserService userService) {
		Assert.notNull(userService, "userService 개체가 반드시 필요!");
		this.userService = userService;
	}

	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public List<User> list() {
		List<User> userList = userService.list();

		if (userList == null || userList.isEmpty())
			throw new EmptyListException("NO DATA");

		return userList;
	}

	@RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
	public User get(@PathVariable int id) {
		User user = userService.get(id);

		if (user == null) {
			logger.error("NOT FOUND ID: ", id);
			throw new NotFoundException(id + " 회원 정보를 찾을 수 없습니다.");
		}

		return user;
	}

	@RequestMapping(value = "/users/email/{email}", method = RequestMethod.GET)
	public Object get(@PathVariable String email) {
		User user = userService.get(email);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	@RequestMapping(value = "/users/login", method = RequestMethod.POST)
	public User login(@RequestBody User user) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		User userFetched = userService.get(user.getEmail());
		
		if (!passwordEncoder.matches(user.getPassword(), userFetched.getPassword()))
			throw new DomainException("비밀번호가 일치하지 않습니다.");

		userFetched.setPassword("");
		return userFetched;
	}

	@RequestMapping(value = "/users", method = RequestMethod.POST)
	public User create(@RequestBody User user) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		User newUser = userService.add(user);
		return newUser;
	}

	@RequestMapping(value = "/users", method = RequestMethod.PUT)
	public User update(@RequestBody User user) {
		return userService.update(user);
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
	public User sendEmail(@RequestBody User user) {
		// 임시 비밀번호 생성 저장 변수
		String userPwd = "";
		for (int i = 0; i < 12; i++) {
			userPwd += (char) ((Math.random() * 26) + 97);
		}
		userPwd += (int) (Math.random() * 9) + 1;

		// 임시비밀번호로 업데이트
		user.setPassword(userPwd);
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
