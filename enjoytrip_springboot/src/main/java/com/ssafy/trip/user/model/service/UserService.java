package com.ssafy.trip.user.model.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;

import com.ssafy.trip.user.model.UserDto;
import com.ssafy.trip.user.model.UserPhotoDto;

public interface UserService {
	
	int dupCheck(String type,String value) throws Exception;
	int join(UserDto user) throws Exception;
	UserDto login(Map<String,String> map) throws Exception;
	UserDto getUser(String userId) throws Exception;
	int modify(Map<String, String> map) throws Exception;
	int pwdCheck(Map<String, String> map) throws Exception;
	int delete(String userId); //
	int find(Map<String, String> map) throws SQLException, MessagingException;

	// pw modify
	String findByRandomToken(String randomToken);
	
	// token
	UserDto userInfo(String userId) throws Exception;
	void saveRefreshToken(String userId, String refreshToken) throws Exception;
	Object getRefreshToken(String userId) throws Exception;
	void deleRefreshToken(String userId) throws Exception;
	List<UserPhotoDto> getPhotos(String userId) throws Exception;
}
