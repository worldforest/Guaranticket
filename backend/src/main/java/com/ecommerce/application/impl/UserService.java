package com.ecommerce.application.impl;

import com.ecommerce.application.IUserService;
import com.ecommerce.domain.User;
import com.ecommerce.domain.exception.ApplicationException;
import com.ecommerce.domain.repository.IUserRepository;
import com.ecommerce.mapper.UserMapper;

import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class UserService implements IUserService {

	private IUserRepository userRepository;

	@Autowired
	private UserMapper userMapper;

	@Autowired
	public UserService(IUserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public List<User> list() {
		return this.userMapper.list();
	}

	@Override
	public User get(long id) {
		return this.userMapper.getById(id);
	}

	@Override
	public User get(String email) {
		return this.userMapper.getByEmail(email);
	}

	@Override
	public User add(User user) {
		this.userMapper.create(user);
		return this.userMapper.getById(user.getId());
	}

	@Override
	public User update(User user) {
		User found = this.userMapper.getByEmail(user.getEmail());
		if (found == null)
			throw new ApplicationException("회원 정보를 찾을 수 없습니다.");
		int affected = this.userMapper.update(user);
		if (affected == 0)
			throw new ApplicationException("회원정보수정 처리가 반영되지 않았습니다.");

		return this.userMapper.getById(user.getId());
	}

	@Override
	public void delete(long id) {
		this.userMapper.delete(id);
	}

	@Override
	public void certifiedPhoneNumber(String phoneNumber, String certNum) {
		String API_KEY = "NCSGNSQNIU6YUBYC";
        String API_SECRET = "ZL3KXTSFK00FSPV7LZVB8EXPFFMVL0QR";
        String FROM = "01093788047";
        Message coolsms = new Message(API_KEY, API_SECRET);

        // 4 params(to, from, type, text) are mandatory. must be filled
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("to", phoneNumber);    // 수신전화번호
        params.put("from", FROM);    // 발신전화번호. 테스트시에는 발신,수신 둘다 본인 번호로 하면 됨
        params.put("type", "SMS");
        params.put("text", "개런티켓 휴대폰인증 테스트 메시지 : 인증번호는" + "["+certNum+"]" + "입니다.");
        params.put("app_version", "test app 1.2"); // application name and version

        try {
            JSONObject obj = (JSONObject) coolsms.send(params);
        } catch (CoolsmsException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getCode());
        }
		
	}
}
