package com.ssafy.trip.plan.model.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ssafy.trip.plan.model.AttractionDto;
import com.ssafy.trip.plan.model.AttractionInfoDto;
import com.ssafy.trip.plan.model.GugunDto;
import com.ssafy.trip.plan.model.PlanDto;
import com.ssafy.trip.plan.model.PlanListDetailDto;
import com.ssafy.trip.plan.model.PlanListDto;
import com.ssafy.trip.plan.model.PlanNthDetailRegistDto;
import com.ssafy.trip.plan.model.SidoGugunNameDto;
import com.ssafy.trip.plan.model.WishlistModifyDto;
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

	@Override
	public String sidoGugunName(int sidoCode, int gugunCode) {
		SidoGugunNameDto dto = planMapper.sidoGugunName(sidoCode, gugunCode);

		return dto.getSidoName() + " " + dto.getGugunName();
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
	public int registPlanNthDetail(PlanNthDetailRegistDto registDto) {
		return planMapper.registPlanNthDetail(registDto);
	}

	@Override
	public List<PlanListDetailDto> getPlan(Map<String, String> map) {
		return planMapper.getPlan(map);
	}

	@Override
	public List<PlanListDto> planList(String userId) {
		return planMapper.planList(userId);
	}

	@Override
	public int modifyPlan(Map<String, String> map) {
		
		if (map.get("type").equals("planName")) {
			return planMapper.modifyPlanName(map.get("planName"), map.get("planId"));
		} else { // 날짜 수정
			// 날짜 같은지
			LocalDate startDate = LocalDate.parse(map.get("startDate"));
			LocalDate endDate = LocalDate.parse(map.get("endDate"));
			
			int prePeriod =planMapper.getPlanPeriod(Integer.parseInt(map.get("planId"))); 
			int nextPeriod = (int) ChronoUnit.DAYS.between(startDate, endDate) + 1;
			System.out.println("변경 일자 : " + nextPeriod);
			
			int planId = Integer.parseInt(map.get("planId"));
			
			// 날짜 다르면
			System.out.println("날짜!" + prePeriod + " => " + nextPeriod);
			if(prePeriod != nextPeriod) {
				System.out.println("날짜 다름!" + prePeriod + " => " + nextPeriod);
				// 이전 날짜가 더 길면 -> plan_nth 삭제 및 이후 plan_nth_detail 앞으로 옮기기
				if(prePeriod > nextPeriod) {
					Map<String, Integer> temp = new HashMap<>();
					temp.put("planId", planId);
					temp.put("nextPeriod", nextPeriod);
					
					System.out.println("planId, nextPeriod " + planId + ", " + nextPeriod);
					// 넣어야할 userPlanNth와 시작 order 가져옴
					Map<String, Integer> insertPoint = planMapper.getInsertPoint(temp);// planId, userPlanNth
					
					System.out.println("insertPoint: " + insertPoint);
					
					// 넣을 계획 가져옴
					List<Integer> list = planMapper.getInsertPlanNthDetail(planId, nextPeriod);
					
					System.out.println("insert plan "+list);
					for(int i = 0; i < list.size(); i++ ) {
						PlanNthDetailRegistDto registDto = new PlanNthDetailRegistDto();
						registDto.setContentId(list.get(i));
						registDto.setOrder(Integer.parseInt(String.valueOf(insertPoint.get("order"))) + i);
						registDto.setUserPlanNth(insertPoint.get("userPlanNth"));
						registDto.setPlanId(planId);
						
						System.out.println(i + ": " + registDto);
						planMapper.registPlanNthDetail(registDto);
						System.out.println("registPlanNthDetail " + i);
					}
					
					// 지워야해 -> prePeriod+1부터 nextPeriod까지 삭제
					
					for(int i = nextPeriod+1; i <= prePeriod; i++) {
						System.out.println("delete date:" + i);
						planMapper.deletePlanNth(planId, i);
					}
					
				} 
				// 이전 날짜가 더 짦으면 -> plan_nth 더 만들기
				// 수정중....
				else {
					System.out.println("이전 날짜가 더 짧음!");
					System.out.println(nextPeriod+1 +" <- " + prePeriod);
					for(int i = prePeriod+1; i <= nextPeriod; i++) {
						System.out.println("날짜 추가: " + i);
						Map<String, Integer> temp = new HashMap<>();
						temp.put("period", i);
						temp.put("planId", planId);
//						registPlanNth(temp);
						System.out.println("map " + temp);
						planMapper.registPlanNth(temp);
					}
				}
			}
			
			return planMapper.modifyPlanDate(map);
		}
	}

	@Override
	public int detelePlan(int planId) {
		return planMapper.deletePlan(planId);
	}

	@Override
	public int deletePlanNthDetail(int planId) {
		return planMapper.deletePlanNthDetail(planId);
	}

	@Override
	public void makeWishlist(WishlistModifyDto wishlistModifyDto) {
		planMapper.deleteWishlist(wishlistModifyDto.getUserId());
		planMapper.registWishlist(wishlistModifyDto.getWishlist());
	}

	@Override
	public AttractionInfoDto attractionListById(int contentId) {
		return planMapper.attractionListById(contentId);
	}

}
