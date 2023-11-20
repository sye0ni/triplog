package com.ssafy.trip.user.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.trip.user.model.UserDto;
import com.ssafy.trip.user.model.UserPhotoDto;
import com.ssafy.trip.user.model.service.UserService;
import com.ssafy.trip.util.JWTUtil;


@RestController
@RequestMapping("/users")
@CrossOrigin("*")
public class UserController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	private final UserService userService;
	private JWTUtil jwtUtil;

	@Autowired
	public UserController(UserService userService, JWTUtil jwtUtil) {
		this.userService = userService;
		this.jwtUtil = jwtUtil;
	}
	
	// 아이디, 이메일 중복 검사
	@PostMapping("/dupCheck") // 임시
	public ResponseEntity<?> dupCheck(@RequestBody Map<String,String> map) throws Exception{
		// map -> 1.type: id, email 2.value: 
		System.out.println("dupCheck start");
		int result=userService.dupCheck(map.get("type"),map.get("value"));
		System.out.println(map + " 중복검사: " + result);
		
		if(result==0) {
			return new ResponseEntity<String>("사용 가능한 "+map.get("type")+" 입니다.", HttpStatus.CREATED); // 201
		}
		else {
			return new ResponseEntity<String>("이미 존재하는 "+map.get("type")+" 입니다.",HttpStatus.CONFLICT); // 409 
		}
	}
	
	// 회원가입
	@PostMapping
	public ResponseEntity<?> join(@RequestBody UserDto user) throws Exception{
		logger.debug("join userDto : {}",user);
		
		int result;
		try {
			result = userService.join(user);
			if(result==1) {
				return new ResponseEntity<String>("1", HttpStatus.CREATED); // 201
//				return new ResponseEntity<String>("회원가입이 완료되었습니다.", HttpStatus.CREATED); // 201
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new ResponseEntity<String>("0",HttpStatus.CONFLICT); // 409 
//		return new ResponseEntity<String>("회원가입 오류! 중복 검사를 진행해주세요.",HttpStatus.CONFLICT); // 409 

	}
	
	// 로그인
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody Map<String,String> map, HttpSession session) throws Exception{
		logger.debug("login userId : {}", map.get("userId"));
		logger.debug("login userPw : {}", map.get("userPwd"));
		
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
		
		try {
			UserDto loginUser = userService.login(map);
			
			if(loginUser != null) {
				String accessToken = jwtUtil.createAccessToken(loginUser.getUserId());
				String refreshToken = jwtUtil.createRefreshToken(loginUser.getUserId());
				logger.debug("access token : {}", accessToken);
				logger.debug("refresh token : {}", refreshToken);
				
				// 발급받은 refresh token을 DB에 저장.
				userService.saveRefreshToken(loginUser.getUserId(), refreshToken);
				
				logger.debug("db save : id {} refreshToken {}", loginUser.getUserId(), refreshToken);
				
				// JSON으로 token 전달.
				resultMap.put("access-token", accessToken);
				resultMap.put("refresh-token", refreshToken);
				
				resultMap.put("message", "로그인 성공일까?");//??
				status = HttpStatus.CREATED;
				logger.debug("성공일까?");
			} else {
				resultMap.put("message", "아이디 또는 패스워드를 확인해주세요.");
				status = HttpStatus.UNAUTHORIZED;
				logger.debug("불일치!");
			} 
			
		} catch (Exception e) {
			logger.debug("로그인 에러 발생 : {}", e);
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	@GetMapping("/info/{userId}")
	public ResponseEntity<Map<String, Object>> getInfo(
			@PathVariable("userId") String userId, HttpServletRequest request) {
		logger.debug("userId : {} ", userId);
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
		if (jwtUtil.checkToken(request.getHeader("Authorization"))) {
			logger.info("사용 가능한 토큰!!!");
			try {
				// 로그인 사용자 정보.
				UserDto userDto = userService.userInfo(userId);
				resultMap.put("userInfo", userDto);
				status = HttpStatus.OK;
			} catch (Exception e) {
				logger.error("정보조회 실패 : {}", e);
				resultMap.put("message", e.getMessage());
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			logger.error("사용 불가능 토큰!!!");
			status = HttpStatus.UNAUTHORIZED;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	// 랜덤토큰으로 아이디 찾기
	@GetMapping("/randomToken")
	public ResponseEntity<?> findByRandomToken(@RequestParam String token) {
		System.out.println("controller randomToken start!!!!");
		logger.debug("randomToken: {}", token);
		
		String userId = userService.findByRandomToken(token);
		
		if(userId != null) {
			return new ResponseEntity<String>(userId, HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("유효하지 않은 token", HttpStatus.NOT_FOUND);
		}
		
	}
	
	// 내정보 수정 - 이름, 이메일 , 비밀번호 
	@PutMapping
	@Transactional
	public ResponseEntity<?> modify(@RequestBody Map<String,String> map) throws Exception{
		// map 에 담겨야 하는 값 
		// 1. userId 
		// 2. type - name or email or password
		// 3. value - 변경하고자 하는 값
		System.out.println("controlelr modify start!!"+ map);
		logger.debug("type : {}", map.get("type")); 
		logger.debug("value : {}", map.get("value"));
		
		int result=userService.modify(map);
			
		if(result==1) { // 수정 성공 
			return new ResponseEntity<String>("수정이 완료되었습니다.", HttpStatus.OK); // 200 
		} else {
			// ?? 중복된이메일??
			return new ResponseEntity<String>("중복된 이메일입니다.",HttpStatus.CONFLICT); // 409 -> 아이디는 not unique 이기 때문에  
		}
	}
	
	// 패스워드 변경 전, 일치하는지 확인 
	@PostMapping("/password") // 임시 
	public ResponseEntity<?> pwdCheck(@RequestBody Map<String,String> map) throws Exception {
		// map 에 담겨야 하는 값 
		// 1. userId 
		// 2. value - 확인하고자 하는 값
		System.out.println("pwdCheck!! " + map);
		
		int result = 0;
		result =userService.pwdCheck(map);
		
		if(result==1) { // 일치 
			return new ResponseEntity<String>("일치하는 패스워드입니다.", HttpStatus.OK); // 200 
		} else {
			return new ResponseEntity<String>("패스워드를 다시 확인하세요.", HttpStatus.UNAUTHORIZED); // 401
		}
		
//		return null;
	}
	
	// 내 정보 조회 
	@GetMapping("/{userId}")
	public ResponseEntity<?> userInfo(@PathVariable String userId) throws Exception{
		// User 반환 
		UserDto user=userService.getUser(userId);
		return new ResponseEntity<UserDto>(user,HttpStatus.OK);
	}
	
	// 아이디, 패스워드 찾기
	@PostMapping("/search") // 임시 
	public ResponseEntity<?> findId(@RequestBody Map<String,String> map) throws Exception{ 
		// map -> 1.type : id, password  2.value: 
		int ret = userService.find(map); // 0: 실패, 1: 성공
		
		if(ret==1) { // 성공
			if(map.get("type").equals("id")) {
				return new ResponseEntity<String>("아이디가 가입하신 이메일로 전송되었습니다.",HttpStatus.OK);
			} else {
				return new ResponseEntity<String>("비밀번호 변경 링크가 메일로 전송되었습니다.",HttpStatus.OK);
			}
		}
		
		return new ResponseEntity<String>("일치하는 사용자가 없습니다.", HttpStatus.UNAUTHORIZED);

	}
	
	// 로그아웃
//	@PostMapping("/logout") // 임시 
//	public ResponseEntity<?> logout(HttpSession session){
//		session.invalidate();
//		return new ResponseEntity<String>("로그아웃 성공",HttpStatus.OK);
//	}
	
	@GetMapping("/logout/{userId}")
	public ResponseEntity<?> removeToken(@PathVariable ("userId") String userId) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
		try {
			userService.deleRefreshToken(userId);
			status = HttpStatus.OK;
		} catch (Exception e) {
			logger.error("로그아웃 실패 : {}", e);
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);

	}
	
	@PostMapping("/refresh")
	public ResponseEntity<?> refreshToken(@RequestBody UserDto memberDto, HttpServletRequest request)
			throws Exception {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
		String token = request.getHeader("refreshToken");
		logger.debug("token : {}, memberDto : {}", token, memberDto);
		if (jwtUtil.checkToken(token)) {
			if (token.equals(userService.getRefreshToken(memberDto.getUserId()))) {
				String accessToken = jwtUtil.createAccessToken(memberDto.getUserId());
				logger.debug("token : {}", accessToken);
				logger.debug("정상적으로 액세스토큰 재발급!!!");
				resultMap.put("access-token", accessToken);
				status = HttpStatus.CREATED;
			}
		} else {
			logger.debug("리프레쉬토큰도 사용불가!!!!!!!");
			status = HttpStatus.UNAUTHORIZED;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	// 회원 탈퇴 
	@DeleteMapping("/{userId}")
	public ResponseEntity<?> delete(@PathVariable String userId){
		
		int tmp = userService.delete(userId);
		if(tmp == 0) {
			System.out.println("실패");
			return new ResponseEntity<String>("0", HttpStatus.BAD_REQUEST);
		} else {
			System.out.println("성공");
			return new ResponseEntity<String>("1", HttpStatus.OK);
		}
	}
	
	// 나의 게시글 조회 ; 
	@GetMapping("/{userId}/photo")
	public ResponseEntity<?> getPhotos(@PathVariable String userId) throws Exception{
		logger.error("getPhotos  : {}", userId);
		
		List<UserPhotoDto> list=userService.getPhotos(userId);
		if(list != null && !list.isEmpty()) {
			return ResponseEntity
					.status(HttpStatus.OK)
					.body(list);
		}
		
		return new ResponseEntity<List>(Collections.EMPTY_LIST, HttpStatus.OK);
	}
	
}