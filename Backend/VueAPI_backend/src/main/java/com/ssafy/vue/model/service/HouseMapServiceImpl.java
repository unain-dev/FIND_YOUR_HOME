package com.ssafy.vue.model.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.vue.config.KeyConfig;
import com.ssafy.vue.model.AptInfoDto;
import com.ssafy.vue.model.HouseInfoDto;
import com.ssafy.vue.model.SidoGugunCodeDto;
import com.ssafy.vue.model.api.AptApi;
import com.ssafy.vue.model.api.CrimeApi;
import com.ssafy.vue.model.mapper.HouseMapMapper;

@Service
public class HouseMapServiceImpl implements HouseMapService {
	
	@Autowired
	private SqlSession sqlSession;
	
	@Autowired
	private KeyConfig keyConfig;
	
//	private AptApi aptApi = AptApi.getAptApiInstance();
//	private CrimeApi crimeApi = CrimeApi.getCrimeApiInstance();

	
	@Autowired
	private AptApi aptApi;
	
	@Autowired
	private CrimeApi crimeApi;
	
//	private static HouseMapService haHouseMapService = new HouseMapServiceImpl();
//	public static HouseMapService getHouseMapServiceImplInstance() {
//		return haHouseMapService;
//	}

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
	public List<AptInfoDto> getAptListApi(String sidoName, String gugunName, String LAWD_CD, String DONG, String NUM_OF_ROWS, String PAGE_NO,
			String DEAL_YMD) throws Exception {
		return aptApi.getAptListApi(sidoName, gugunName, LAWD_CD, DONG, NUM_OF_ROWS, PAGE_NO, DEAL_YMD);
	}

	@Override
	public Map<String, String> getCrimeInGugun(String sidoName, String gugunName, String lat, String lng) throws Exception {
		return crimeApi.getCrimeInGugun(sidoName, gugunName, lat, lng);
	}
	
	@Override
	public List<SidoGugunCodeDto> getBaseAddressByCode(String dongCode) throws Exception {
		return sqlSession.getMapper(HouseMapMapper.class).getBaseAddressByCode(dongCode);
	}
	
	@Override
	public List<SidoGugunCodeDto> getNameAndlatLngByGugunCode(String gugunCode) throws Exception {
		return sqlSession.getMapper(HouseMapMapper.class).getNameAndlatLngByGugunCode(gugunCode.substring(0, 2), gugunCode.substring(0, 5));
	}

//	@Override
//	public List<SidoGugunCodeDto> getSidoBySidoCode(String sidoCode) throws Exception {
//		return sqlSession.getMapper(HouseMapMapper.class).getSidoBySidoCode(sidoCode.substring(0, 2));
//	}
//
//	@Override
//	public List<SidoGugunCodeDto> getGugunByGugunCode(String gugunCode) throws Exception {
//		return sqlSession.getMapper(HouseMapMapper.class).getGugunByGugunCode(gugunCode.substring(0, 5));
//	}
//
//	@Override
//	public List<SidoGugunCodeDto> getLatLngByName(String sidoName, String gugunName) throws Exception {
//		return sqlSession.getMapper(HouseMapMapper.class).getLatLngByName(sidoName, gugunName);
//	}

}
