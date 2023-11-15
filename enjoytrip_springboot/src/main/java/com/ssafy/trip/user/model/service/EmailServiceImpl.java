package com.ssafy.trip.user.model.service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.ssafy.trip.user.model.UserDto;
import com.ssafy.trip.user.model.mapper.UserMapper;
import com.ssafy.trip.util.RandomString;

@Service
public class EmailServiceImpl implements EmailService{
	
	private JavaMailSender mailSender;
	private UserMapper userMapper;

	public EmailServiceImpl(JavaMailSender mailSender, UserMapper userMapper) {
		super();
		this.mailSender = mailSender;
		this.userMapper = userMapper;
	}

	// 아이디 전송 
	@Override
	public void sendUserId(String userId, String email) {
		SimpleMailMessage mailMessage=new SimpleMailMessage();
		mailMessage.setTo(email);
		mailMessage.setSubject("[Enjoy Trip] 아이디 찾기 이메일 입니다.");
		mailMessage.setFrom("EnjoyTrip");
		
		StringBuffer sb=new StringBuffer();
		sb.append("안녕하세요. Enjoy Trip 입니다. \n");
		sb.append("가입하신 아이디는 [ "+userId+" ] 입니다."+"\n");
		
		mailMessage.setText(sb.toString());
		mailSender.send(mailMessage);
		
	}

	// 패스워드 재설정 
	@Override
	public void sendPwdReset(UserDto user) throws MessagingException {
	    String randomToken = RandomString.generateRandomString(20);
	    userMapper.setRandomToken(user.getUserId(), randomToken); // 사용자의 랜덤토큰 저장

	    MimeMessage mimeMessage = mailSender.createMimeMessage();
	    MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

	    helper.setTo(user.getUserEmail());
	    helper.setSubject("[Enjoy Trip] 비밀번호 재설정 이메일 입니다.");
	    helper.setFrom("EnjoyTrip");

	    // HTML 형식의 메일 본문 작성
	    String htmlContent = "<html><body>";
	    htmlContent += "<p>안녕하세요. Enjoy Trip 입니다.</p>";
	    htmlContent += "<p>아래 링크에 접속하여 비밀번호를 재설정 해주세요.</p>";
	    htmlContent += "<a href=\"http://localhost/user/passwordReset?token=" + randomToken + "\">비밀번호 재설정</a>";
	    htmlContent += "</body></html>";

	    // //// 위 href 수정 필요 
	    
	    helper.setText(htmlContent, true);

	    mailSender.send(mimeMessage);
	}

	
	
	
	
}
