package com.ssafy.trip.plan.model;

public class Wishlist {

	private String userId;
	private String contentId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getContentId() {
		return contentId;
	}

	public void setContentId(String contentId) {
		this.contentId = contentId;
	}

	@Override
	public String toString() {
		return "Wishlist [userId=" + userId + ", contentId=" + contentId + "]";
	}

}
