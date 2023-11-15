package com.ssafy.trip.user.model.service;

import javax.mail.MessagingException;
import com.ssafy.trip.user.model.User;

public interface EmailService {

	void sendUserId(String userId, String email);
	void sendPwdReset(User user) throws MessagingException;

}