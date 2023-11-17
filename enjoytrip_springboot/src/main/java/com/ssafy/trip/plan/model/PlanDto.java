package com.ssafy.trip.plan.model;

import java.util.List;

public class PlanDto {

	private int planId;
	private String userId;
	private int userPlanNth;
	private String location;
	private int sidoCode;
	private int gugunCode;
	private String startDate;
	private String endDate;
	private String planName;

//	private List<PlanNthDto> planNthList;
//	private List<PlanNthDetailDto> planNthDetailList;
	public int getPlanId() {
		return planId;
	}

	public void setPlanId(int planId) {
		this.planId = planId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getUserPlanNth() {
		return userPlanNth;
	}

	public void setUserPlanNth(int userPlanNth) {
		this.userPlanNth = userPlanNth;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public int getSidoCode() {
		return sidoCode;
	}

	public void setSidoCode(int sidoCode) {
		this.sidoCode = sidoCode;
	}

	public int getGugunCode() {
		return gugunCode;
	}

	public void setGugunCode(int gugunCode) {
		this.gugunCode = gugunCode;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getPlanName() {
		return planName;
	}

	public void setPlanName(String planName) {
		this.planName = planName;
	}

	@Override
	public String toString() {
		return "PlanDto [planId=" + planId + ", userId=" + userId + ", userPlanNth=" + userPlanNth + ", location="
				+ location + ", sidoCode=" + sidoCode + ", gugunCode=" + gugunCode + ", startDate=" + startDate
				+ ", endDate=" + endDate + ", planName=" + planName + "]";
	}

}
