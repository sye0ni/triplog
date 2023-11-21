package com.ssafy.trip.plan.model;

public class PlanListDetailDto {

	private int planId;
	private int planNthId;
	private int userPlanNth;
	private int order;
	private int contentId;
	private String contentTypeName;
	private String title;
	private String addr1;
	private String addr2;
	private String firstImage;
	private int period;

	public int getPlanId() {
		return planId;
	}

	public void setPlanId(int planId) {
		this.planId = planId;
	}

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

	public String getContentTypeName() {
		return contentTypeName;
	}

	public void setContentTypeName(String contentTypeName) {
		this.contentTypeName = contentTypeName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAddr1() {
		return addr1;
	}

	public void setAddr1(String addr1) {
		this.addr1 = addr1;
	}

	public String getAddr2() {
		return addr2;
	}

	public void setAddr2(String addr2) {
		this.addr2 = addr2;
	}

	public String getFirstImage() {
		return firstImage;
	}

	public void setFirstImage(String firstImage) {
		this.firstImage = firstImage;
	}

	public int getPeriod() {
		return period;
	}

	public void setPeriod(int period) {
		this.period = period;
	}

	@Override
	public String toString() {
		return "PlanListDetailDto [planId=" + planId + ", planNthId=" + planNthId + ", userPlanNth=" + userPlanNth
				+ ", order=" + order + ", contentId=" + contentId + ", contentTypeName=" + contentTypeName + ", title="
				+ title + ", addr1=" + addr1 + ", addr2=" + addr2 + ", firstImage=" + firstImage + ", period=" + period
				+ "]";
	}

}
