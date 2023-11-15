package com.ssafy.trip.plan.model;

public class PlanNthDetailDto {

	private int order;
	private int contentId;
	private int planNthId;

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

	public int getPlanNthId() {
		return planNthId;
	}

	public void setPlanNthId(int planNthId) {
		this.planNthId = planNthId;
	}

	@Override
	public String toString() {
		return "PlanNthDetail [order=" + order + ", contentId=" + contentId + ", planNthId=" + planNthId + "]";
	}

}
