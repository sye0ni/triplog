package com.ssafy.trip.user.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.trip.user.model.UserDto;
import com.ssafy.trip.user.model.UserPhotoDto;

@Mapper
public interface UserMapper {

	int dupCheck(String type, String value) throws SQLException;
	void join(UserDto user) throws SQLException;
	UserDto login(String userId,String userPwd) throws SQLException;
	UserDto getUser(String userId) throws SQLException;
	void modify(Map<String,String> map) throws SQLException;
	int pwdCheck(String userId, String value) throws SQLException;
	int delete(String userId);
	String findIdByEmail(String email);
	void setRandomToken(String userId, String randomToken);
	
	// pw modify
	String findByRandomToken(String randomToken);
	void deleteRandomToken(String userId);
	
	// token
	UserDto userInfo(String userId) throws SQLException;
	void saveRefreshToken(Map<String, String> map) throws SQLException;
	Object getRefreshToken(String userid) throws SQLException;
	void deleteRefreshToken(Map<String, String> map) throws SQLException;
	
	// mypage
	List<UserPhotoDto> getPhotos(String userId) throws SQLException;
	List<String> getPhotoPaths(int photoId) throws SQLException;
	void deleteLike(String userId) throws SQLException;
}
