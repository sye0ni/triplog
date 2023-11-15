package com.ssafy.trip.plan.model;

public class PlanNthDto {

	private int planNthId;
	private int userPlanNth;
	private int planId;

	public int getPlanNthId() {
		return planNthId;
	}

	public void setPlanNthId(int planNthId) {
		this.planNthId = planNthId;
	}

	public int getUserPlanNth() {
		return userPlanNth;
	}

	public void setUserPlanNth(int userPlanNth) {
		this.userPlanNth = userPlanNth;
	}

	public int getPlanId() {
		return planId;
	}

	public void setPlanId(int planId) {
		this.planId = planId;
	}

	@Override
	public String toString() {
		return "PlanNthDto [planNthId=" + planNthId + ", userPlanNth=" + userPlanNth + ", planId=" + planId + "]";
	}

}
