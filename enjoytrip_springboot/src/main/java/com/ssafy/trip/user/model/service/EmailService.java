package com.ssafy.trip.user.model.service;

import javax.mail.MessagingException;
import com.ssafy.trip.user.model.UserDto;

public interface EmailService {

	void sendUserId(String userId, String email);
	void sendPwdReset(UserDto user) throws MessagingException;

}