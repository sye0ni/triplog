package com.ssafy.trip.plan.model;

public class PlanListDto {
	private int planId;
	private int planNthId;
	private int order;
	private int contentId;
	private String contentTypeName;
	private String title;
	private String addr1;
	private String addr2;
	private String firstImage;

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

	public String getCotentTypeName() {
		return contentTypeName;
	}

	public void setCotentTypeName(String cotentTypeName) {
		this.contentTypeName = cotentTypeName;
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

	@Override
	public String toString() {
		return "PlanListDto [planId=" + planId + ", planNthId=" + planNthId + ", order=" + order + ", contentId="
				+ contentId + ", contentTypeName=" + contentTypeName + ", title=" + title + ", addr1=" + addr1
				+ ", addr2=" + addr2 + ", firstImage=" + firstImage + "]";
	}

}
