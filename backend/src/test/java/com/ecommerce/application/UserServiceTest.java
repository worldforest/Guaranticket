package com.ecommerce.application;

import com.ecommerce.Application;
import com.ecommerce.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class UserServiceTest {
    @Autowired
    private IUserService userService;

    	@Test
    	public void testAdd() {
    		User user = new User();
    		user.setEmail("test@test.com");
    		user.setPassword("password");
    		user.setName("Jo");
    		user.setGender("남");
    		user.setPhone("010-9378-8047");
    		user.setBusinessNumber("1234567890");
    		user.setCompanyName("삼성전자");
    		user.setRepresentativeName("이건희");
    		user.setBirth("1994-01-20");
    		
    		User userAdded = this.userService.add(user);
    		System.out.println(userAdded);
    		assert userAdded.getId() > 0;
    		assert userAdded.getName().equals("Jo");
    		assert userAdded.getEmail().equals("test@test.com");
    		assert userAdded.getBusinessNumber().equals("1234567890");
    		assert userAdded.getCompanyName().equals("삼성전자");
    		assert userAdded.getRepresentativeName().equals("이건희");
    		assert userAdded.getBirth().equals("1994-01-20");
    	}
    
//    @Test
//    public void testAdd() {
//        User user = new User();
//        user.setName("코인충");
//        user.setEmail("코인충@yahoo.com");
//        user.setPassword("qwer");
//
//        User userAdded = this.userService.add(user);
//        assert userAdded.getId() > 0;
//        assert userAdded.getName().equals("코인충");
//        assert userAdded.getEmail().equals("코인충@yahoo.com");
//    }
//
//
//    @Test
//    public void testList() {
//        List<User> userList = this.userService.list();
//
//        assert userList.size() > 0;
//    }
//
//    @Test
//    public void testGet() {
//        long id = 4;
//        User user = this.userService.get(id);
//
//        assert user != null;
//        assert user.getName().equals("비트맨");
//    }
//
//
//    @Transactional
//    @Test
//    public void testUpdate() {
//        User user = new User();
//        user.setId(4);
//        user.setName("비트우먼");
//        user.setEmail("bitwoman@yahoo.com");
//
//        this.userService.update(user);
//
//        User userUpdated = this.userService.get(4);
//        assert userUpdated.getName().equals("비트우먼");
//    }
//
//    @Transactional
//    @Test
//    public void testDeleted() {
//        long id = 4;
//        this.userService.delete(id);
//
//        User userDeleted = this.userService.get(id);
//        assert userDeleted == null;
//    }
}
