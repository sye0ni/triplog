package com.ssafy.trip.user.model.service;

import java.sql.SQLException;
import java.util.Map;

import javax.mail.MessagingException;

import com.ssafy.trip.user.model.User;

public interface UserService {
	
	int dupCheck(String type,String value) throws Exception;
	int join(User user) throws Exception;
	User login(Map<String,String> map) throws Exception;
	User getUser(String userId) throws Exception;
	int modify(Map<String, String> map) throws Exception;
	int pwdCheck(Map<String, String> map) throws Exception;
	void delete(String userId);
	int findId(String email);
	int findPw(String id) throws Exception;
	int find(Map<String, String> map) throws SQLException, MessagingException;
	
}
