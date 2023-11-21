package com.ssafy.trip.plan.model;

import java.util.List;

public class PlanNthDetailMakeDto {
	private int planId;
	private int userPlanNth;
	private List<AttractionDto> list;

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

	public List<AttractionDto> getList() {
		return list;
	}

	public void setList(List<AttractionDto> list) {
		this.list = list;
	}

	@Override
	public String toString() {
		return "PlanNthDetailMakeDto [planId=" + planId + ", userPlanNth=" + userPlanNth + ", list=" + list + "]";
	}

}
