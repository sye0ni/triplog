package com.ssafy.trip.user.model.service;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;

import org.springframework.stereotype.Service;

import com.ssafy.trip.board.photo.model.mapper.PhotoMapper;
import com.ssafy.trip.user.model.UserDto;
import com.ssafy.trip.user.model.UserPhotoDto;
import com.ssafy.trip.user.model.mapper.UserMapper;
import com.ssafy.trip.util.PwHash;


@Service
public class UserServiceImpl implements UserService{

	private final UserMapper userMapper;
	private EmailService emailService;
	

	public UserServiceImpl( UserMapper userMapper, EmailService emailService) {
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
	public int join(UserDto user) throws Exception {
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
	public UserDto getUser(String userId) throws SQLException {
		return userMapper.getUser(userId);
	}

	// 로그인 
	@Override
	public UserDto login(Map<String, String> map) throws SQLException {
		String userPwd=null;
		UserDto user=getUser(map.get("userId"));
		System.out.println("유저 아이디: " + map.get("userId"));
		if(user==null) { // 유효하지 않은 회원인 경우 
			return null;
		}
		
		try {
			userPwd=PwHash.pwHashing(map.get("userPwd"), user.getSalt());
			System.out.println("로그인 비밀번호 : " + userPwd);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		return userMapper.login(map.get("userId"), userPwd);	
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
			UserDto user=getUser(map.get("userId"));

			String userPwd=map.get("value");
			
			userPwd=PwHash.pwHashing(userPwd, user.getSalt());
			
			map.replace("value", userPwd);

			// 랜덤 토큰 삭제
			userMapper.deleteRandomToken(map.get("userId"));
		}
		
		// 수정 	
		userMapper.modify(map);
		return 1;
	}

	// 패스워드 일치하는지 확인 
	@Override
	public int pwdCheck(Map<String, String> map) throws SQLException { // userId, value 담겨 있음 
		String userPwd = map.get("value");
		UserDto user, user2;
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

	// 회원 탈퇴 -> 여행 계획, 찜 목록, 좋아하는 게시글 삭제 
	@Override
	public int delete(String userId) throws SQLException {
		userMapper.deleteLike(userId);
		userMapper.updateLike(userId);
		return userMapper.delete(userId);
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
			UserDto user=getUser(map.get("value"));
			System.out.println("비밀번호 찾기1!!! " + user);
			if(user==null) {
				return 0; // 아이디 일치하는 사용자 없음 
			}

			emailService.sendPwdReset(user); // 패스워드 리셋 링크
		}
		
		return 1;
	}
	
	@Override
	public String findByRandomToken(String randomToken) {
		return userMapper.findByRandomToken(randomToken);
	}
	
	// token ----
	@Override
	public UserDto userInfo(String userId) throws Exception {
		return userMapper.userInfo(userId);
	}
	
	@Override
	public void saveRefreshToken(String userId, String refreshToken) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("userId", userId);
		map.put("refreshToken", refreshToken);
		userMapper.saveRefreshToken(map);
	}

	@Override
	public Object getRefreshToken(String userId) throws Exception {
		return userMapper.getRefreshToken(userId);
	}

	@Override
	public void deleRefreshToken(String userId) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("userId", userId);
		map.put("refreshToken", null);
		userMapper.deleteRefreshToken(map);
	}

	@Override
	public List<UserPhotoDto> getPhotos(String userId) throws Exception {
		List<UserPhotoDto> list=userMapper.getPhotos(userId); // 게시 사진 정보 가져오기
		
		return list;
		
	}

}
