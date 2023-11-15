package com.ssafy.trip.user.model.service;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Map;

import javax.mail.MessagingException;

import org.springframework.stereotype.Service;

import com.ssafy.trip.user.model.User;
import com.ssafy.trip.user.model.mapper.UserMapper;
import com.ssafy.trip.util.PwHash;


@Service
public class UserServiceImpl implements UserService{

	private UserMapper userMapper;
	private EmailService emailService;

	public UserServiceImpl(UserMapper userMapper, EmailService emailService) {
		super();
		this.userMapper = userMapper;
		this.emailService = emailService;
	}

	// 아이디, 이메일 중복 체크 용
	@Override
	public int dupCheck(String type,String value) throws Exception {
		return userMapper.dupCheck(type,value);
	}
	
	// 회원 가입
	@Override
	public int join(User user) throws Exception {
		// 패스워드 암호화 후 insert
		String userPw=user.getUserPwd();
		user.setSalt(PwHash.makeSalt());
		try {
			user.setUserPwd(PwHash.pwHashing(userPw, user.getSalt()));
			userMapper.join(user);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		return 1; 
	}

	// 회원 조회 
	@Override
	public User getUser(String userId) throws SQLException {
		return userMapper.getUser(userId);
	}

	// 로그인 
	@Override
	public User login(Map<String, String> map) throws SQLException {
		String userPwd=null;
		User user=getUser(map.get("userId"));
		
		if(user==null) { // 유효하지 않은 회원인 경우 
			return null;
		}
		
		try {
			userPwd=PwHash.pwHashing(map.get("userPwd"), user.getSalt());
			System.out.println("로그인 비밀번호 : "+userPwd);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		return userMapper.login(map.get("userId"),userPwd);	
	}

	// 내 정보 수정 
	@Override
	public int modify(Map<String, String> map) throws Exception {
		// "userId"는 사용자 아이디, "type" 은 name or email or password , "value" 는 변경 원하는 값 
		
		// 이메일 변경이라면 중복  확인 
		if(map.get("type").equals("email")) {
			if(dupCheck("email",map.get("value"))==1) {
				return 0; 
			} // 중복 이메일 .. 
		}
		
		// password 의 경우에는 암호화하여 전달 
		if(map.get("type").equals("password")) {
			User user=getUser(map.get("userId"));
			String userPwd=map.get("value");
			userPwd=PwHash.pwHashing(userPwd, user.getSalt());
			map.replace("value", userPwd);
		}
		
		// 수정 	
		userMapper.modify(map);
		return 1;
	}

	// 패스워드 일치하는지 확인 
	@Override
	public int pwdCheck(Map<String, String> map) throws SQLException { // userId, value 담겨 있음 
		String userPwd=map.get("value");
		User user, user2;
		int result=0;
		
		user = getUser(map.get("userId"));

		try {
			userPwd=PwHash.pwHashing(userPwd, user.getSalt());
			user2=userMapper.login(user.getUserId(),userPwd);
			if(user2!=null) result=1;  // 패스워드가 일치하면 1 반환 
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		return result;	
	}

	// 회원 탈퇴 -> 여행 계획, 찜 목록도 삭제 해야함 
	@Override
	public void delete(String userId) {
		
		// TODO : 여행 계획 삭제 
		
		// TODO : 찜 목록 삭제 
		
		userMapper.delete(userId);
		
	}

	// 아이디 찾기 : 이메일로 아이디 조회, 아이디가 있다면 메일 보내기 
	@Override
	public int findId(String email) {
		
		String userId=userMapper.findIdByEmail(email);
		System.out.println("아이디 : " + userId);
		
		if(userId==null) { // 아이디 없음 
			return 0;
		}
		
		emailService.sendUserId(userId,email);
		
		return 1;
	}

	// 패스워드 찾기 : 아이디 조회 -> 아이디로 이메일 조회 , 유효한 아이디라면 메일 보내기 
	@Override
	public int findPw(String userId) throws SQLException, MessagingException {
		
		User user=getUser(userId);
		if(user==null) return 0; // 아이디 일치하는 사용자 없음 

		emailService.sendPwdReset(user); // 패스워드 리셋 링크
		return 1;
	}

	@Override
	public int find(Map<String, String> map) throws SQLException, MessagingException {
		
		if(map.get("type").equals("id")) { // 아이디 찾기 
			String userId=userMapper.findIdByEmail(map.get("value"));
			System.out.println("아이디 : " + userId);
			
			if(userId==null) { // 아이디 없음 
				return 0;
			}	
			emailService.sendUserId(userId,map.get("value"));
		}
		
		else { // 비밀번호 찾기 
			User user=getUser(map.get("value"));
			if(user==null) return 0; // 아이디 일치하는 사용자 없음 

			emailService.sendPwdReset(user); // 패스워드 리셋 링크
		}
		
		return 1;
	}
	
}
