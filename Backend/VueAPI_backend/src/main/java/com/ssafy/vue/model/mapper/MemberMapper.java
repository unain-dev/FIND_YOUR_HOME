package com.ssafy.vue.model.mapper;

import java.util.List;
import java.sql.SQLException;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.vue.model.MemberDto;
import com.ssafy.vue.model.SidoGugunCodeDto;

@Mapper
public interface MemberMapper {

	public MemberDto login(MemberDto memberDto) throws SQLException;

	public MemberDto userInfo(String userid) throws SQLException;

	public int idcheck(String userid) throws SQLException;

	public int register(MemberDto memberDto) throws SQLException;

	public int updateInfo(MemberDto memberDto) throws SQLException;

	public int deleteUser(String userid) throws SQLException;

	public int registerArea(MemberDto MemberDto) throws SQLException;

	public int deleteArea(String userid) throws SQLException;

	public List<SidoGugunCodeDto> getUserArea(String userid) throws SQLException;
}