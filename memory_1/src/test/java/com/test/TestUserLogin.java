package com.test;

import org.junit.Test;

import com.service.impl.UserAccountImpl;
import com.domain.RegisterStatus;
import com.domain.User;

public class TestUserLogin {
	@Test
	public void testUserLogin() {
		User user=new User();
//		user.setEmail("huangmingcx@163.com");
//		user.setPhone("18279669898");
//		user.setPassword("123");
		user.setId(1450000000);
		user.setPassword("qwertyuiop");
		System.out.println(new UserAccountImpl(user).userLogin());
	}
	
	@Test
	public void testUserRegister() {
		User user=new User();
//		user.setEmail("huangmingx@163.com");
		user.setPhone("18279669889");
		user.setPassword("1234");
		UserAccountImpl accountDao=new UserAccountImpl(user);
		RegisterStatus status=accountDao.userRegister();
		if(status.isSuccess()) {
			System.out.println("注册成功");
		}else {
			if(!status.isEmail()) {
				System.out.println("email已注册");
			}
			if(!status.isPhone()) {
				System.out.println("phone已注册");
			}
		}
	}
	
	@Test
	public void testFindOne() {
		User user=new User();
		user.setEmail("huangmingcx@163.com");
		user.setId(1);
//		user.setPhone("");
//		user.setId(1450000000);
		UserAccountImpl accountDao=new UserAccountImpl(user);
		user=accountDao.findUserMassageOne();
		System.out.println(user);
	}
	
	@Test
	public void testUpdateMassage() {
		User user=new User();
		boolean formal=true;
		user.setName("王武");
		user.setId(1);
		user.setGender(1);//男
		user.setAddress("北京市");
		
//		boolean formal=false;
//		user.setId(1450000003);
//		user.setPassword("1234");
//		user.setEmail("huangmxcx@163.com");
//		user.setPhone("18279567798");
//		user.setName("李涛");
//		user.setGender(1);
		UserAccountImpl accountDao=new UserAccountImpl(user);
		RegisterStatus status=accountDao.updateUserMassage(formal);
		if(status.isSuccess()) {
			System.out.println("修改成功");
		}else {
			if(!status.isEmail()) {
				System.out.println("email已注册");
			}
			if(!status.isPhone()) {
				System.out.println("phone已注册");
			}
		}
	}
	
	@Test
	public void testUserChangePassword() {
		User user=new User();
//		user.setEmail("huangmingcx@163.com");
		user.setPassword("123456");
//		user.setId(1);
		user.setId(1450000002);
		UserAccountImpl accountDao=new UserAccountImpl(user);
		accountDao.userChangePassword();
	}
	
	@Test
	public void testUserChangeEmail() {
		User user=new User();
		user.setEmail("wangyi@163.com");
		user.setId(8);
		UserAccountImpl accountDao=new UserAccountImpl(user);
		accountDao.userChangeEmail();
	}
	
	@Test
	public void testUserChangePhone() {
		User user=new User();
		user.setPhone("15878984569");
		user.setId(8);
		UserAccountImpl accountDao=new UserAccountImpl(user);
		accountDao.userChangePhone();
	}
}
