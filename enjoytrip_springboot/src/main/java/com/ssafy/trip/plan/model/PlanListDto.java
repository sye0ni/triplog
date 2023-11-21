package com.ssafy.trip.plan.model;

public class PlanListDto {
	private int planId;
	private int userPlanNth;
	private int sidoCode;
	private int gugunCode;
	private String startDate;
	private String endDate;
	private String planName;

	public int getPlanId() {
		return planId;
	}

	public void setPlanId(int planId) {
		this.planId = planId;
	}

	public int getUserPlanNth() {
		return userPlanNth;
	}

	public void setUserPlanNth(int userPlanNth) {
		this.userPlanNth = userPlanNth;
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
		return "PlanListDto [planId=" + planId + ", userPlanNth=" + userPlanNth + ", sidoCode=" + sidoCode
				+ ", gugunCode=" + gugunCode + ", startDate=" + startDate + ", endDate=" + endDate + ", planName="
				+ planName + "]";
	}

}
