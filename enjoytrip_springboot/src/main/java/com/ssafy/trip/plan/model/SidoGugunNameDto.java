package com.ssafy.trip.plan.model;

public class SidoGugunNameDto {
	private String sidoName;
	private String gugunName;

	public String getSidoName() {
		return sidoName;
	}

	public void setSidoName(String sidoName) {
		this.sidoName = sidoName;
	}

	public String getGugunName() {
		return gugunName;
	}

	public void setGugunName(String gugunName) {
		this.gugunName = gugunName;
	}

	@Override
	public String toString() {
		return "SidoGugunNameDto [sidoName=" + sidoName + ", gugunName=" + gugunName + "]";
	}

}
