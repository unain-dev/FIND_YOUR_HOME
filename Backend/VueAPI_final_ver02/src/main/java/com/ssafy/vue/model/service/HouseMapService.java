package com.ssafy.vue.model.service;

import java.util.List;

import com.ssafy.vue.model.AptInfoDto;
import com.ssafy.vue.model.HouseInfoDto;
import com.ssafy.vue.model.SidoGugunCodeDto;

public interface HouseMapService {

	List<SidoGugunCodeDto> getSido() throws Exception;
	List<SidoGugunCodeDto> getGugunInSido(String sido) throws Exception;
	List<HouseInfoDto> getDongInGugun(String gugun) throws Exception;
	List<HouseInfoDto> getAptInDong(String dong) throws Exception;
	
	List<AptInfoDto> getAptListApi(String LAWD_CD, String DONG, String NUM_OF_ROWS, String PAGE_NO, String DEAL_YMD) throws Exception;
}
