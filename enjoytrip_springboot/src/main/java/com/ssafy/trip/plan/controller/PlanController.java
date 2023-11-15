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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.trip.plan.model.AttractionDto;
import com.ssafy.trip.plan.model.AttractionInfoDto;
import com.ssafy.trip.plan.model.GugunDto;
import com.ssafy.trip.plan.model.PlanDto;
import com.ssafy.trip.plan.model.PlanListDto;
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
		if(list != null && !list.isEmpty()) {
			return ResponseEntity
					.status(HttpStatus.OK)
					.body(list);
		}
		
		return new ResponseEntity<List>(Collections.EMPTY_LIST, HttpStatus.OK);
	}
	
	@GetMapping(value = "/detail/attraction/gugun")
	private ResponseEntity<?> gugun(@RequestParam int sidoCode) {
		logger.debug("gugun sidoCode : {}", sidoCode);
		System.out.println("gugun sidoCode: " + sidoCode);
		
		List<GugunDto> list = planService.gugun(sidoCode);
		
		System.out.println("gugun sidoCode List: " + list);
		
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(list);
	}
	
	@GetMapping(value = "/detail/attraction/{contentId}")
	private ResponseEntity<?> getAttraction(@PathVariable int contentId) {
		AttractionDto attractionDto = planService.getAttraction(contentId);
		
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(attractionDto);
	}
	
	@PostMapping
	@Transactional
	private ResponseEntity<?> registPlan(@RequestBody PlanDto planDto) {
		planService.registPlan(planDto);
		System.out.println("registPlan planId : " + planDto.getPlanId());

		int period = planService.getPlanPeriod(planDto.getPlanId());
		
		for(int i = 1; i <= period; i++) {
			Map<String, Integer> map = new HashMap<>();
			map.put("period", i);
			map.put("planId", planDto.getPlanId());
			planService.registPlanNth(map);
			System.out.println("map " + map);
		}
		
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(planDto);
	}
	
	@GetMapping(value = "/{planid}")
	private ResponseEntity<?> getPlan(@PathVariable("planid") int planId, @RequestParam String userId) {
		System.out.println("plan id ::: " + planId + " userId ::: " + userId);
		
		Map<String, String> map = new HashMap<>();
		map.put("planId", planId+"");
		map.put("userId", userId);
		List<PlanListDto> planlist = planService.getPlan(map);
		
//		logger.debug("get Plan planDto: {}", planDto);
		
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(planlist);
	}
	@GetMapping
	private ResponseEntity<?> planList(@RequestParam String userId) {
		List<PlanListDto> planlist = planService.planList(userId);
		logger.debug("planList : {}", planlist);
		
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(planlist);
	}
	
	@GetMapping(value = "/wishlist/{userId}")
	private ResponseEntity<?> wishlist(@PathVariable String userId){
		List<AttractionInfoDto> attractionInfoDto = planService.wishlist(userId);
		System.out.println("userid " + userId);
		System.out.println("wishlist " + attractionInfoDto);
		
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(attractionInfoDto);
	}
	
}
