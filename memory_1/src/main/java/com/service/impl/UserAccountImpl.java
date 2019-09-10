package com.service.impl;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.domain.RegisterStatus;
import com.domain.User;
import com.mapper.IUserMapper;
/**
 * function:userLogin()游客与正式用户的登录
 * 			userRegister()游客与正式用户的注册
 * 			findUserMassageOne()游客与正式用户通过id查询用户信息
 * 			updateUserMassage(boolean formal)游客通过绑定邮箱和手机号转换为正式用户，同时将游客账号删除；修改正式用户的基本信息
 * 			userChangePassword()修改游客和正式用户的密码
 * 			userChangeEmail()修改正式用户的邮箱
 * 			userChangePhone()修改正式用户的手机号
 * @author Administrator
 *
 */
public class UserAccountImpl {
	static InputStream inputStream=null;
	static SqlSession sqlSession=null;
	static IUserMapper userMapper=null;
	static User user=null;
	RegisterStatus status=new RegisterStatus();
	
	@SuppressWarnings("static-access")
	public UserAccountImpl(User user) {
		this.user=user;
	}
	
	private static void init() {
		try {
			inputStream=Resources.getResourceAsStream("SqlMapConfig.xml");
			SqlSessionFactory factory=new SqlSessionFactoryBuilder().build(inputStream);
			sqlSession=factory.openSession();
			userMapper=sqlSession.getMapper(IUserMapper.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void destory() {
		try {
			sqlSession.close();
			inputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 
	 * @param userLogin in servlet must be judge loginAccout belong to what property
	 * @return	null show login lose or user
	 */
	public User userLogin() {
		init();
		User userOne=null;
		if(user.getEmail()!=null||user.getPhone()!=null) {
			userOne=userMapper.formalUserLogin(user);
		}else {
			userOne=userMapper.unformalUserLogin(user);
		}
		destory();
		return userOne;
	}
	
	private static boolean judgeEmailNotRegister() {
		init();
		int flag=userMapper.judgeEmailNotRegister(user.getEmail());
		if(flag==0) {
			destory();
			return true;
		}
		destory();
		return false;
	}
	
	private static boolean judgePhoneNotRegister() {
		init();
		int flag=userMapper.judgePhoneNotRegister(user.getPhone());
		if(flag==0) {
			destory();
			return true;
		}
		destory();
		return false;
	}
	
	/**
	 * 
	 * @param user register massages at first must run judgeEmailNotRegister 
	 * and judgePhoneNotRegister method
	 * @return	true show register success
	 */
	private static boolean userRegister0() {
		init();
		Integer id=null;
		if(user.getPhone()!=null||user.getPhone()!=null) {
			id=userMapper.formalUserRegister(user);
		}else {
			id=userMapper.unformalUserRegister(user);
		}
		if(id!=null) {
			sqlSession.commit();
			destory();
			return true;
		}
		sqlSession.rollback();
		destory();
		return false;
	}
	
	public RegisterStatus userRegister() {
		status.setEmail(true);
		status.setPhone(true);
		status.setSuccess(false);
		if(user.getName()==null) {
			user.setName("匿名用户");
		}
		boolean emailNotUse=true;
		boolean phoneNotUse=true;
		boolean register=false;
		if(user.getEmail()!=null||user.getPhone()!=null) {
			if(user.getEmail()!=null) {
				emailNotUse=judgeEmailNotRegister();
				if(!emailNotUse) {
					status.setEmail(false);
				}
			}
			if(user.getPhone()!=null) {
				phoneNotUse=judgePhoneNotRegister();
				if(!phoneNotUse) {
					status.setPhone(false);
				}
			}
		}
		if(emailNotUse&&phoneNotUse) {
			register=userRegister0();
			if(register) {
				status.setSuccess(true);
			}
		}
		return status;
	}
	
	public User findUserMassageOne() {
		init();
		User userOne=new User();
		if(user.getEmail()!=null||user.getPhone()!=null) {
			userOne=userMapper.formalUserFindOne(user.getId());
		}else {
			userOne=userMapper.unformalUserFindOne(user.getId());
		}
		destory();
		return userOne;
	}
	/**
	 * 
	 * @param formal true show is formal User
	 */
	public RegisterStatus updateUserMassage(boolean formal) {
		status.setEmail(true);
		status.setPhone(true);
		status.setSuccess(false);
		if(formal) {
			init();
			userMapper.formalUserUpdate(user);
			status.setSuccess(true);
			sqlSession.commit();
			destory();
			return status;
		}else {
			Integer id=user.getId();
			user.setId(null);
			status=new UserAccountImpl(user).userRegister();
			if(status.isSuccess()) {
				init();
				userMapper.unformalUserDelete(id);
				sqlSession.commit();
			}
			destory();
			return status;
		}
	}
	
	public void userChangePassword() {
		init();
		if(user.getEmail()!=null||user.getPhone()!=null) {
			userMapper.formalUserChangePassword(user);
		}else {
			userMapper.unformalUserChangePassword(user);
		}
		sqlSession.commit();
		destory();
	}
	
	public void userChangeEmail() {
		init();
		userMapper.formalUserChangeEmail(user);
		sqlSession.commit();
		destory();
	}
	
	public void userChangePhone() {
		init();
		userMapper.formalUserChangePhone(user);
		sqlSession.commit();
		destory();
	}
}
