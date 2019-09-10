package com.mapper;

import com.domain.User;

public interface IUserMapper {
	
	User formalUserLogin(User user);
	
	User unformalUserLogin(User user);
	
	Integer formalUserRegister(User user);
	
	Integer unformalUserRegister(User user);
	
	int judgeEmailNotRegister(String email);
	
	int judgePhoneNotRegister(String phone);
	
	User formalUserFindOne(int id);
	
	User unformalUserFindOne(int id);
	
	void formalUserUpdate(User user);
	
	void unformalUserDelete(Integer id);
	
	void formalUserChangePassword(User user);
	
	void unformalUserChangePassword(User user);
	
	void formalUserChangeEmail(User user);
	
	void formalUserChangePhone(User user);
}
