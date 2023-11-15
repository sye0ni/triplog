package com.ssafy.trip.user.model;

public class UserDto {

	private String userId;
	private String userName;
	private String userPwd;
	private String userEmail;
	private String platformType;
	private String refreshToken;
	private String salt;
	private String randomToken;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPwd() {
		return userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getPlatformType() {
		return platformType;
	}

	public void setPlatformType(String platformType) {
		this.platformType = platformType;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getRandomToken() {
		return randomToken;
	}

	public void setRandomToken(String randomToken) {
		this.randomToken = randomToken;
	}

	@Override
	public String toString() {
		return "UserDto [userId=" + userId + ", userName=" + userName + ", userPwd=" + userPwd + ", userEmail="
				+ userEmail + ", platformType=" + platformType + ", refreshToken=" + refreshToken + ", salt=" + salt
				+ ", randomToken=" + randomToken + "]";
	}

}
