package com.ssafy.trip.plan.model.service;

import java.util.List;
import java.util.Map;

import com.ssafy.trip.plan.model.AttractionDto;
import com.ssafy.trip.plan.model.AttractionInfoDto;
import com.ssafy.trip.plan.model.GugunDto;
import com.ssafy.trip.plan.model.PlanDto;
import com.ssafy.trip.plan.model.PlanListDto;
import com.ssafy.trip.plan.model.Wishlist;
import com.ssafy.trip.plan.model.WishlistModifyDto;

public interface PlanService {

	// attraction
	public List<AttractionDto> attractionList(Map<String, String> map);
	public List<GugunDto> gugun(int sidoCode);
	public AttractionDto getAttraction(int contentId);
	
	// plan
	void registPlan(PlanDto planDto);
	int getPlanPeriod(int planId);
	void registPlanNth(Map<String, Integer> map); // planId, 여행날짜 전달(1,2,3)
//	void registPlanNthDetail(List<PlanNthDetailDto> list); // ??
	
	List<PlanListDto> getPlan(Map<String, String> map);
	List<PlanListDto> planList(String userId); // PlanDto에 다 담아
	
	// wishlist
	List<AttractionInfoDto> wishlist(String userId);
	void makeWishlist(WishlistModifyDto wishlistModifyDto);
	
//	void deleteWishlist(String userId);
//	void registWishlist(List<Wishlist> list);
}
