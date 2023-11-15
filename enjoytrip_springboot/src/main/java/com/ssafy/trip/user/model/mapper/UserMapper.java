package com.ssafy.trip.user.model.mapper;

import java.sql.SQLException;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.trip.user.model.UserDto;

@Mapper
public interface UserMapper {

	int dupCheck(String type, String value) throws SQLException;
	void join(UserDto user) throws SQLException;
	UserDto login(String userId,String userPwd) throws SQLException;
	UserDto getUser(String userId) throws SQLException;
	void modify(Map<String,String> map) throws SQLException;
	int pwdCheck(String userId, String value) throws SQLException;
	void delete(String userId);
	String findIdByEmail(String email);
	void setRandomToken(String userId, String randomToken);
	
	// token
	UserDto userInfo(String userId) throws SQLException;
	void saveRefreshToken(Map<String, String> map) throws SQLException;
	Object getRefreshToken(String userid) throws SQLException;
	void deleteRefreshToken(Map<String, String> map) throws SQLException;

}
