package com.ssafy.trip.user.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.trip.user.model.User;
import com.ssafy.trip.user.model.service.UserService;


@RestController
@RequestMapping("/users")
@CrossOrigin("*")
public class UserController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	private UserService userService;

	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	// 아이디, 이메일 중복 검사
	@PostMapping("/dupCheck") // 임시
	public ResponseEntity<?> dupCheck(@RequestBody Map<String,String> map) throws Exception{
		// map : type, value 
		
		int result=userService.dupCheck(map.get("type"),map.get("value"));
		
		if(result==0) {
			return new ResponseEntity<String>("사용 가능한 "+map.get("type")+" 입니다.", HttpStatus.CREATED); // 201
		}
		else {
			return new ResponseEntity<String>("이미 존재하는 "+map.get("type")+" 입니다.",HttpStatus.CONFLICT); // 409 
		}
	}
	
	// 회원가입
	@PostMapping
	public ResponseEntity<?> join(@RequestBody User user) throws Exception{
		logger.debug("join userDto : {}",user);
		
		int result;
		try {
			result = userService.join(user);
			if(result==1) {
				return new ResponseEntity<String>("회원가입이 완료되었습니다.", HttpStatus.CREATED); // 201
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new ResponseEntity<String>("회원가입 오류! 중복 검사를 진행해주세요.",HttpStatus.CONFLICT); // 409 

	}
	
	// 로그인
	@PostMapping("/login")	// 임시 
	public ResponseEntity<?> login(@RequestBody Map<String,String> map, HttpSession session) throws Exception{
		logger.debug("login userId : {}", map.get("userId"));
		logger.debug("login userPw : {}", map.get("userPwd"));

		User user=userService.login(map);
		
		if(user!=null) { // 로그인 성공 
			session.setAttribute("userInfo", user); // setAttribute 이후 확인 필요
			return new ResponseEntity<String>("로그인이 완료되었습니다.",HttpStatus.OK);
		}
		else { // 로그인 실패 
			return new ResponseEntity<String>("아이디 혹은 패스워드를 다시 확인하세요.",HttpStatus.UNAUTHORIZED); // 401 에러 
		}
	}
	
	// 내정보 수정 - 아이디, 이메일 , 비밀번호 
	@PutMapping
	public ResponseEntity<?> modify(@RequestBody Map<String,String> map) throws Exception{
		// map 에 담겨야 하는 값 
		// 1. userId 
		// 2. type - name or email or password
		// 3. value - 변경하고자 하는 값
		
		logger.debug("type : {}", map.get("type")); 
		logger.debug("value : {}", map.get("value"));
		
		int result=userService.modify(map);
		
		if(result==1) { // 수정 성공 
			return new ResponseEntity<String>("수정이 완료되었습니다.", HttpStatus.OK); // 200 
		} else {
			return new ResponseEntity<String>("중복된 이메일입니다.",HttpStatus.CONFLICT); // 409 -> 아이디는 not unique 이기 때문에  
		}
	}
	
	// 패스워드 변경 전, 일치하는지 확인 
	@PostMapping("/password") // 임시 
	public ResponseEntity<?> pwdCheck(@RequestBody Map<String,String> map) throws Exception {
		// map 에 담겨야 하는 값 
		// 1. userId 
		// 2. value - 확인하고자 하는 값
		
		int result=userService.pwdCheck(map);
		
		if(result==1) { // 일치 
			return new ResponseEntity<String>("일치하는 패스워드입니다.", HttpStatus.OK); // 200 
		} else {
			return new ResponseEntity<String>("패스워드를 다시 확인하세요.", HttpStatus.UNAUTHORIZED); // 401
		}
	}
	
	// 내 정보 조회 
	@GetMapping("/{userId}")
	public ResponseEntity<?> userInfo(@PathVariable String userId) throws Exception{
		// User 반환 
		User user=userService.getUser(userId);
		return new ResponseEntity<User>(user,HttpStatus.OK);
	}
	
	// 아이디, 패스워드 찾기
	@PostMapping("/search") // 임시 
	public ResponseEntity<?> findId(@RequestBody Map<String,String> map) throws Exception{ // type, value 가짐 
		int ret=0;
		
		ret=userService.find(map);
		if(ret==1) { // 성공
			return new ResponseEntity<String>(map.get("type")+" 찾기 메일 전송",HttpStatus.OK);
		}
		
		return new ResponseEntity<String>("일치하는 사용자가 없습니다.",HttpStatus.UNAUTHORIZED);

	}
	
	// 로그아웃
	@PostMapping("/logout") // 임시 
	public ResponseEntity<?> logout(HttpSession session){
		session.invalidate();
		return new ResponseEntity<String>("로그아웃 성공",HttpStatus.OK);
	}
	
	// 회원 탈퇴 
	@DeleteMapping("/{userId}")
	public ResponseEntity<?> delete(@PathVariable String userId){
		userService.delete(userId);
		return new ResponseEntity<String>("회원탈퇴 성공", HttpStatus.OK);
	}
	
}