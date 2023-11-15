package com.ssafy.trip.util;

import java.security.SecureRandom;

	public class RandomString{
	    private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()-_+=<>?";

	    public static String generateRandomString(int length) {
	        SecureRandom random = new SecureRandom();
	        StringBuilder sb = new StringBuilder(length);

	        for (int i = 0; i < length; i++) {
	            int randomIndex = random.nextInt(CHARACTERS.length());
	            char randomChar = CHARACTERS.charAt(randomIndex);
	            sb.append(randomChar);
	        }

	        return sb.toString();
	    }

}
