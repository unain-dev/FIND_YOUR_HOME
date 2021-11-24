package com.ssafy.vue.model.service;

import java.util.List;
import java.util.Map;

import com.ssafy.vue.model.AptInfoDto;
import com.ssafy.vue.model.HouseInfoDto;
import com.ssafy.vue.model.SidoGugunCodeDto;

public interface HouseMapService {

	List<SidoGugunCodeDto> getSido() throws Exception;
	List<SidoGugunCodeDto> getGugunInSido(String sido) throws Exception;
	List<HouseInfoDto> getDongInGugun(String gugun) throws Exception;
	List<HouseInfoDto> getAptInDong(String dong) throws Exception;
	
	List<AptInfoDto> getAptListApi(String LAWD_CD, String DONG, String NUM_OF_ROWS, String PAGE_NO, String DEAL_YMD) throws Exception;
	List<AptInfoDto> getAptListApi(String sidoName, String gugunName, String LAWD_CD, String DONG, String NUM_OF_ROWS, String PAGE_NO, String DEAL_YMD) throws Exception;
	Map<String, String> getCrimeInGugun(String sidoName, String gugunName) throws Exception;
	
	List<SidoGugunCodeDto> getSidoByCode(String sido) throws Exception;
	List<SidoGugunCodeDto> getGugunByCode(String gugun) throws Exception;
}
