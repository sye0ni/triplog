package com.ssafy.trip.board.photo.model;

public class SigunDto {
	int sidoCode;
	String sigunName;
	int gugunCode;
	
	public int getSidoCode() {
		return sidoCode;
	}
	public void setSidoCode(int sidoCode) {
		this.sidoCode = sidoCode;
	}
	public String getSigunName() {
		return sigunName;
	}
	public void setSigunName(String sigunName) {
		this.sigunName = sigunName;
	}
	public int getGugunCode() {
		return gugunCode;
	}
	public void setGugunCode(int gugunCode) {
		this.gugunCode = gugunCode;
	}
	
	@Override
	public String toString() {
		return "SigunDto [sidoCode=" + sidoCode + ", sigunName=" + sigunName + ", gugunCode=" + gugunCode + "]";
	}
	
	
	
}
