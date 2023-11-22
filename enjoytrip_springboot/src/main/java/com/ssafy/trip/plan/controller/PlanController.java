package com.ssafy.trip.plan.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.trip.plan.model.AttractionDto;
import com.ssafy.trip.plan.model.AttractionInfoDto;
import com.ssafy.trip.plan.model.GugunDto;
import com.ssafy.trip.plan.model.PlanDto;
import com.ssafy.trip.plan.model.PlanListDetailDto;
import com.ssafy.trip.plan.model.PlanListDto;
import com.ssafy.trip.plan.model.PlanNthDetailMakeDto;
import com.ssafy.trip.plan.model.PlanNthDetailRegistDto;
import com.ssafy.trip.plan.model.WishlistModifyDto;
import com.ssafy.trip.plan.model.service.PlanService;

@RequestMapping("/plan")
@CrossOrigin("*")
@RestController
public class PlanController {

	private static final Logger logger = LoggerFactory.getLogger(PlanController.class);

	private PlanService planService;

	@Autowired
	public PlanController(PlanService planService) {
		super();
		this.planService = planService;
	}

	// query string으로 sidoCode, gugunCode, contentType 받음
	@GetMapping(value = "/detail/attraction")
	private ResponseEntity<?> attractionList(@RequestParam Map<String, String> map) {
		logger.debug("attraction list call");

		List<AttractionDto> list = planService.attractionList(map);
		if (list != null && !list.isEmpty()) {
			return ResponseEntity.status(HttpStatus.OK).body(list);
		}

		return new ResponseEntity<List>(Collections.EMPTY_LIST, HttpStatus.OK);
	}

	@GetMapping(value = "/detail/attraction/gugun")
	private ResponseEntity<?> gugun(@RequestParam int sidoCode) {
		logger.debug("gugun sidoCode : {}", sidoCode);
		System.out.println("gugun sidoCode: " + sidoCode);

		List<GugunDto> list = planService.gugun(sidoCode);

		System.out.println("gugun sidoCode List: " + list);

		return ResponseEntity.status(HttpStatus.OK).body(list);
	}

	@GetMapping(value = "/detail/attraction/{contentId}")
	private ResponseEntity<?> getAttraction(@PathVariable int contentId) {
		AttractionDto attractionDto = planService.getAttraction(contentId);

		return ResponseEntity.status(HttpStatus.OK).body(attractionDto);
	}

	@GetMapping("/location/name")
	private ResponseEntity<?> getSidoGugunName(@RequestParam int sidoCode, @RequestParam int gugunCode) {
		String name = planService.sidoGugunName(sidoCode, gugunCode);
		logger.debug("sido gugun name : {}", name);

		return ResponseEntity.status(HttpStatus.OK).body(name);
	}

	@PostMapping
	@Transactional
	private ResponseEntity<?> registPlan(@RequestBody PlanDto planDto) {
		planService.registPlan(planDto);
		System.out.println("registPlan planId : " + planDto.getPlanId());

		int period = planService.getPlanPeriod(planDto.getPlanId());

		for (int i = 1; i <= period; i++) {
			Map<String, Integer> map = new HashMap<>();
			map.put("period", i);
			map.put("planId", planDto.getPlanId());
			planService.registPlanNth(map);
			System.out.println("map " + map);
		}

		Map<String, Object> map = new HashMap<>();
		map.put("period", period);
		map.put("planDto", planDto);

		return ResponseEntity.status(HttpStatus.CREATED).body(map);
	}

	@PostMapping(value = "/detail")
	@Transactional
	private ResponseEntity<?> registPlanDetail(@RequestBody PlanNthDetailMakeDto makeDto) {
		System.out.println("planNthDetailDto" + makeDto);

		logger.debug("삭제 시작");
		// 세부계획 삭제하고 다시 넣음..
		planService.deletePlanNthDetail(makeDto.getPlanId());
		logger.debug("삭제 끝");

		//
		logger.debug("등록 시작");
		int tmp = -1;
		List<AttractionDto> list = makeDto.getList();
		for (int i = 0; i < list.size(); i++) {
			PlanNthDetailRegistDto registDto = new PlanNthDetailRegistDto();
			registDto.setContentId(list.get(i).getContentId());
			registDto.setOrder(i + 1);
			registDto.setPlanId(makeDto.getPlanId());
			registDto.setUserPlanNth(makeDto.getUserPlanNth());

			System.out.println(registDto);
			tmp = planService.registPlanNthDetail(registDto);
		}
		logger.debug("등록 끝");

		System.out.println("result" + tmp);

		return ResponseEntity.status(HttpStatus.CREATED).body(null);
	}

	@GetMapping(value = "/{planid}")
	private ResponseEntity<?> getPlan(@PathVariable("planid") int planId, @RequestParam String userId) {
		System.out.println("plan id ::: " + planId + " userId ::: " + userId);

		Map<String, String> map = new HashMap<>();
		map.put("planId", planId + "");
		map.put("userId", userId);
		List<PlanListDetailDto> planlist = planService.getPlan(map);

//		logger.debug("get Plan planDto: {}", planDto);

		int period = planService.getPlanPeriod(planId);
		planlist.get(0).setPeriod(period);

		return ResponseEntity.status(HttpStatus.OK).body(planlist);
	}

	@GetMapping
	private ResponseEntity<?> planList(@RequestParam String userId) {
		List<PlanListDto> planlist = planService.planList(userId);
		logger.debug("planList : {}", planlist);

		return ResponseEntity.status(HttpStatus.OK).body(planlist);
	}

	@PutMapping("/{planId}")
//	private ResponseEntity<?> modifyPlan(@PathVariable String planId, @RequestBody String map){
	private ResponseEntity<?> modifyPlan(@PathVariable String planId, @RequestBody Map<String, String> map) {
		logger.debug("modifyPlan : {}", map);
		System.out.println(map.get("type"));
		// 여행 날짜 or 계획 이름 변경 planId, planName
		// type: planName -> planName:값
		// type: planDate -> startDate, endDate

		map.put("planId", planId);
		
		planService.modifyPlan(map);

		return ResponseEntity.status(HttpStatus.OK).body("1");
	}

	@DeleteMapping("/{planId}")
	private ResponseEntity<?> deletePlan(@PathVariable String planId) {
		planService.detelePlan(Integer.parseInt(planId));

		return ResponseEntity.status(HttpStatus.OK).body(null);
	}

	@GetMapping(value = "/wishlist/{userId}")
	private ResponseEntity<?> wishlist(@PathVariable String userId) {
		List<AttractionInfoDto> attractionInfoDto = planService.wishlist(userId);
		System.out.println("userid " + userId);
		System.out.println("wishlist " + attractionInfoDto);

		return ResponseEntity.status(HttpStatus.OK).body(attractionInfoDto);
	}

	@PutMapping(value = "/wishlist")
	@Transactional
	private ResponseEntity<?> modifyWishlist(@RequestBody WishlistModifyDto wishlistModifyDto) {
		System.out.println("wishlist modify!! " + wishlistModifyDto);

		for (int i = 0; i < wishlistModifyDto.getWishlist().size(); i++) {
			wishlistModifyDto.getWishlist().get(i).setUserId(wishlistModifyDto.getUserId());
		}

		planService.makeWishlist(wishlistModifyDto);

		// 에러처리??
		return ResponseEntity.status(HttpStatus.OK).body("찜목록 추가 완료");
	}
}
