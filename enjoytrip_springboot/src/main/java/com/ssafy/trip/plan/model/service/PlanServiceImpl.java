package com.ssafy.trip.plan.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ssafy.trip.plan.model.AttractionDto;
import com.ssafy.trip.plan.model.AttractionInfoDto;
import com.ssafy.trip.plan.model.GugunDto;
import com.ssafy.trip.plan.model.PlanDto;
import com.ssafy.trip.plan.model.PlanListDto;
import com.ssafy.trip.plan.model.mapper.PlanMapper;

@Service
public class PlanServiceImpl implements PlanService {

	private PlanMapper planMapper;
	
	public PlanServiceImpl(PlanMapper planMapper) {
		super();
		this.planMapper = planMapper;
	}
	
	@Override
	public List<AttractionDto> attractionList(Map<String, String> map) {
		return planMapper.attractionList(map);
	}
	
	@Override
	public List<GugunDto> gugun(int sidoCode) {
		return planMapper.gugun(sidoCode);
	}
	
	@Override
	public AttractionDto getAttraction(int contentId) {
		return planMapper.getAttraction(contentId);
	}
	
	// plan
	@Override
	public void registPlan(PlanDto planDto) {
		planMapper.registPlan(planDto);
	}

	@Override
	public List<AttractionInfoDto> wishlist(String userId) {
		return planMapper.wishlist(userId);
	}

	@Override
	public int getPlanPeriod(int planId) {
		return planMapper.getPlanPeriod(planId);
	}

	@Override
	public void registPlanNth(Map<String, Integer> map) {
		planMapper.registPlanNth(map);
	}

	@Override
	public List<PlanListDto> getPlan(Map<String, String> map) {
		return planMapper.getPlan(map);
	}

	@Override
	public List<PlanListDto> planList(String userId) {
		return planMapper.planList(userId);
	}

	
	
	
	
	
}
