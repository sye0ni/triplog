package com.ssafy.trip.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class PwHash {

	public static final int SALT_BYTES = 24;
	public static final int HASH_BYTES = 24;
	public static final int STRETCHING = 1000;
	
	static PwHash instance = new PwHash();
	private PwHash() { }
	public static PwHash getInstance() {
		return instance;
	}

	public static String makeSalt() {
		// 랜덤 salt 생성
		SecureRandom random = new SecureRandom();
		byte[] salt = new byte[SALT_BYTES];
		random.nextBytes(salt);

		System.out.println(byte2string(salt));

		return byte2string(salt);
	}

	public static String pwHashing(String userPw, String salt) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		byte[] newPw = userPw.getBytes();
		for(int i = 0; i < STRETCHING; i++) {
			String temp = userPw + salt;
			md.update(temp.getBytes());	
			newPw = md.digest();
		}
		
		return byte2string(newPw);
	}
	
	private static String byte2string(byte[] temp) {
		StringBuilder sb = new StringBuilder();
		for(byte a : temp) {
			sb.append(String.format("%02x", a));
		}
		return sb.toString();
	}
	
//	public static void main(String[] args) throws NoSuchAlgorithmException {
//		System.out.println("Charset.defaultCharset(): " + Charset.defaultCharset());
//        System.out.println("file.encoding: " + System.getProperty("file.encoding"));
//        
//		makeSalt();
//		pwHashing("1234", "1babc826ba477686cba8a5ca20ff11a147409d6804e4cdd0");
//	}

}
