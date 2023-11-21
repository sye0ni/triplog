package com.ssafy.trip.plan.model;

public class PlanNthDetailRegistDto {
	private int order;
	private int contentId;
	private int planId;
	private int userPlanNth;

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public int getContentId() {
		return contentId;
	}

	public void setContentId(int contentId) {
		this.contentId = contentId;
	}

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

	@Override
	public String toString() {
		return "PlanNthDetailRegistDto [order=" + order + ", contentId=" + contentId + ", planId=" + planId
				+ ", userPlanNth=" + userPlanNth + "]";
	}

}
