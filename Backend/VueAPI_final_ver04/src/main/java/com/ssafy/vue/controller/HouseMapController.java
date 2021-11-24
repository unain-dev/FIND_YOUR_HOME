package com.ssafy.vue.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.vue.model.AptInfoDto;
import com.ssafy.vue.model.HouseInfoDto;
import com.ssafy.vue.model.SidoGugunCodeDto;
import com.ssafy.vue.model.service.HouseMapService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/map")
@Api("Map 컨트롤러  API V1")
public class HouseMapController {
	
	private final Logger logger = LoggerFactory.getLogger(HouseMapController.class);

	@Autowired
	private HouseMapService haHouseMapService;
	
	@ApiOperation(value = "시도 정보", notes = "전국의 시도를 반환한다.", response = List.class)
	@GetMapping("/sido")
	public ResponseEntity<List<SidoGugunCodeDto>> sido() throws Exception {
		logger.info("sido - 호출");
		return new ResponseEntity<List<SidoGugunCodeDto>>(haHouseMapService.getSido(), HttpStatus.OK);
	}
	
	@ApiOperation(value = "구군 정보", notes = "전국의 구군을 반환한다.", response = List.class)
	@GetMapping("/gugun")
	public ResponseEntity<List<SidoGugunCodeDto>> gugun(@RequestParam("sido") @ApiParam(value = "시도코드.", required = true) String sido) throws Exception {
		logger.info("gugun - 호출");
		return new ResponseEntity<List<SidoGugunCodeDto>>(haHouseMapService.getGugunInSido(sido), HttpStatus.OK);
	}
	
	@ApiOperation(value = "동 정보", notes = "서울시 내의 동을 반환한다.", response = List.class)
	@GetMapping("/dong")
	public ResponseEntity<List<HouseInfoDto>> dong(@RequestParam("gugun") String gugun) throws Exception {
		return new ResponseEntity<List<HouseInfoDto>>(haHouseMapService.getDongInGugun(gugun), HttpStatus.OK);
	}
	
//	@ApiOperation(value = "아파트 정보", notes = "동 안에 위치한 아파트를 반환한다.", response = List.class)
//	@GetMapping("/apt")
//	public ResponseEntity<List<HouseInfoDto>> apt(@RequestParam("dong") String dong) throws Exception {
//		return new ResponseEntity<List<HouseInfoDto>>(haHouseMapService.getAptInDong(dong), HttpStatus.OK);
//	}
	
//	@ApiOperation(value = "아파트 정보(API)", notes = "동 안에 위치한 아파트를 반환한다.", response = List.class)
//	@GetMapping("/apt")
//	public ResponseEntity<List<AptInfoDto>> apt(@RequestParam("dong") String dong) throws Exception {
//		String LAWD_CD = dong.substring(0, 5); // 지역 코드 5자리 - 구군 단위
//		String DONG = dong.substring(5); // 동 코드 10자리 - 뒤 5자리만 끊어서 법정동읍면동코드와 비교 필요
//		String PAGE_NO = "1";			// 페이지번호
//		String NUM_OF_ROWS = "30";		// 한 페이지 결과 수
//		String DEAL_YMD = "202110";		// 계약월
//		return new ResponseEntity<List<AptInfoDto>>(haHouseMapService.getAptListApi(LAWD_CD, DONG, NUM_OF_ROWS, PAGE_NO, DEAL_YMD), HttpStatus.OK);
//	}
	
	@ApiOperation(value = "선택된 구군의 범죄 발생 정보", notes = "선택된 구군의 범죄발생 정도를 반환한다.", response = Map.class)
	@GetMapping("/crime")
	public ResponseEntity<Map<String, String>> crime(String sidoName, String gugunName) throws Exception {
		return new ResponseEntity<Map<String, String>>(haHouseMapService.getCrimeInGugun(sidoName, gugunName), HttpStatus.OK);
	}
	
	@ApiOperation(value = "아파트 정보(API)", notes = "동 안에 위치한 아파트를 반환한다.", response = List.class)
	@GetMapping("/apt")
	public ResponseEntity<List<AptInfoDto>> testsido(String dong, String sidoName,String gugunName) throws Exception {
		String LAWD_CD = dong.substring(0, 5); // 지역 코드 5자리 - 구군 단위
		String DONG = dong.substring(5); // 동 코드 10자리 - 뒤 5자리만 끊어서 법정동읍면동코드와 비교 필요
		String PAGE_NO = "1";			// 페이지번호
		String NUM_OF_ROWS = "30";		// 한 페이지 결과 수
		String DEAL_YMD = "202110";		// 계약월
//		sidoName = "대구광역시";
//		gugunName = "서구";
		return new ResponseEntity<List<AptInfoDto>>(haHouseMapService.getAptListApi(sidoName, gugunName, LAWD_CD, DONG, NUM_OF_ROWS, PAGE_NO, DEAL_YMD), HttpStatus.OK);
	}
}
