package com.ssafy.trip.plan.model;

import java.util.List;

public class WishlistModifyDto {
	private String userId;
	private List<Wishlist> wishlist;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public List<Wishlist> getWishlist() {
		return wishlist;
	}

	public void setWishlist(List<Wishlist> wishlist) {
		this.wishlist = wishlist;
	}

	@Override
	public String toString() {
		return "WishlistModifyDto [userId=" + userId + ", wishlist=" + wishlist + "]";
	}

}
