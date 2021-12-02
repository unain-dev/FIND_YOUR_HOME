package com.ssafy.vue.model.mapper;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.vue.model.HouseInfoDto;
import com.ssafy.vue.model.SidoGugunCodeDto;

@Mapper
public interface HouseMapMapper {

	List<SidoGugunCodeDto> getSido() throws SQLException;
	List<SidoGugunCodeDto> getGugunInSido(String sido) throws SQLException;
	List<HouseInfoDto> getDongInGugun(String gugun) throws SQLException;
	List<HouseInfoDto> getAptInDong(String dong) throws SQLException;
	
//	List<SidoGugunCodeDto> getSidoBySidoCode(String sidoCode) throws SQLException;
//	List<SidoGugunCodeDto> getGugunByGugunCode(String gugunCode) throws SQLException;
//	List<SidoGugunCodeDto> getLatLngByName(String sidoName, String gugunName) throws SQLException;
	
	List<SidoGugunCodeDto> getBaseAddressByCode(String dongCode) throws SQLException;
	List<SidoGugunCodeDto> getNameAndlatLngByGugunCode(String sidoCode, String gugunCode) throws SQLException;
	
}