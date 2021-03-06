package com.ecommerce.api;

import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import com.ecommerce.application.IChatlistService;
import com.ecommerce.application.IChatmemberService;
import com.ecommerce.application.IUserService;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class ChatController {
	public static final Logger logger = LoggerFactory.getLogger(ChatController.class);

	@Autowired
	private IChatmemberService chatmemberService;

	@Autowired
	private IChatlistService chatlistService;
	@Autowired
	private IUserService userService;
	
	// 이름으로 채팅방 만들기
	@ApiOperation(value = "1:1방 이름 설정", notes = "1:1방 가져오기 했는데 없으면 만들고 있으면 채팅방이름 반환")
	@PostMapping("privateCaht")
	public ResponseEntity<String> privateChat(@RequestParam("myname") String myName,
			@RequestParam("yourname") String yourName) {

		String roomName = null;
		// 채팅방이 없으면
		// myname인 사람이 있는 방에서 상대가 yourname인 방번호가 없으면
		if (chatmemberService.select(myName, yourName) == null) {
			// 새로 만들기
			roomName = myName.concat(",").concat(yourName);
			chatlistService.insert(roomName);
			
			String roomnum = chatlistService.selectno(roomName);
			chatmemberService.insert(roomnum, myName);
			chatmemberService.insert(roomnum, yourName);
			
		}
		// 있으면
		String roomno = chatmemberService.select(myName, yourName);
		roomName = chatlistService.select(roomno);
		return new ResponseEntity<String>(roomName, HttpStatus.OK);
	}

	@ApiOperation(value = "내가 속한 채팅방 이름 목록", notes = "name보내면 방 이름 목록")
	@PostMapping("selectAll")
	public ResponseEntity<List<String>> selectAll(@RequestParam String name) {
		List<String> roomno = chatmemberService.selectAll(name);
		List<String> roomname = new ArrayList<String>();
		for (String no : roomno) {
			roomname.add(chatlistService.select(no));
		}
		return new ResponseEntity<List<String>>(roomname, HttpStatus.OK);
	}

}
