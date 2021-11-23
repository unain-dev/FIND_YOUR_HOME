package com.ssafy.vue.model.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.vue.model.AptInfoDto;
import com.ssafy.vue.model.HouseInfoDto;
import com.ssafy.vue.model.SidoGugunCodeDto;
import com.ssafy.vue.model.mapper.AptApi;
import com.ssafy.vue.model.mapper.CrimeApi;
import com.ssafy.vue.model.mapper.HouseMapMapper;

@Service
public class HouseMapServiceImpl implements HouseMapService {
	
	@Autowired
	private SqlSession sqlSession;
	
	private AptApi aptApi = AptApi.getAptApiInstance();
	private CrimeApi crimeApi = CrimeApi.getCrimeApiInstance();

	@Override
	public List<SidoGugunCodeDto> getSido() throws Exception {
		return sqlSession.getMapper(HouseMapMapper.class).getSido();
	}

	@Override
	public List<SidoGugunCodeDto> getGugunInSido(String sido) throws Exception {
		return sqlSession.getMapper(HouseMapMapper.class).getGugunInSido(sido);
	}

	@Override
	public List<HouseInfoDto> getDongInGugun(String gugun) throws Exception {
		return sqlSession.getMapper(HouseMapMapper.class).getDongInGugun(gugun);
	}

	@Override
	public List<HouseInfoDto> getAptInDong(String dong) throws Exception {
		return sqlSession.getMapper(HouseMapMapper.class).getAptInDong(dong);
	}

	@Override
	public List<AptInfoDto> getAptListApi(String LAWD_CD, String DONG, String NUM_OF_ROWS, String PAGE_NO,
			String DEAL_YMD) throws Exception {
		return aptApi.getAptListApi(LAWD_CD, DONG, NUM_OF_ROWS, PAGE_NO, DEAL_YMD);
	}

	@Override
	public Map<String, String> getCrimeInGugun(String sidoName, String gugunName) throws Exception {
		return crimeApi.getCrimeInGugun(sidoName, gugunName);
	}

}
