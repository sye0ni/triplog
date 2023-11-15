package com.ssafy.trip.plan.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.trip.plan.model.AttractionDto;
import com.ssafy.trip.plan.model.AttractionInfoDto;
import com.ssafy.trip.plan.model.GugunDto;
import com.ssafy.trip.plan.model.PlanDto;
import com.ssafy.trip.plan.model.PlanListDto;
import com.ssafy.trip.plan.model.PlanNthDetailDto;

@Mapper
public interface PlanMapper {
	
	// attraction
	List<AttractionDto> attractionList(Map<String, String> map);
	AttractionDto getAttraction(int contentId);
	List<GugunDto> gugun(int sidoCode);
	
	// plan
	void registPlan(PlanDto planDto);
	int getPlanPeriod(int planId);
	void registPlanNth(Map<String, Integer> map); // planId, 여행날짜 전달(1,2,3)
	void registPlanNthDetail(List<PlanNthDetailDto> list); // ??
	
	List<PlanListDto> getPlan(Map<String, String> map); // int planId, String userId
	List<PlanListDto> planList(String userId); // .. PlanDto에 다 담아
	
	List<AttractionInfoDto> wishlist(String userId);
	
}
