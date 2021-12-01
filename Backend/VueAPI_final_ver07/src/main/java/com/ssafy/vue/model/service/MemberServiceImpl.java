package com.ssafy.vue.model.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.vue.model.MemberDto;
import com.ssafy.vue.model.SidoGugunCodeDto;
import com.ssafy.vue.model.mapper.MemberMapper;

@Service
public class MemberServiceImpl implements MemberService {

	@Autowired
	private SqlSession sqlSession;

	@Override
	public MemberDto login(MemberDto memberDto) throws Exception {
		if (memberDto.getUserid() == null || memberDto.getUserpwd() == null)
			return null;
		return sqlSession.getMapper(MemberMapper.class).login(memberDto);
	}

	@Override
	public MemberDto userInfo(String userid) throws Exception {
		return sqlSession.getMapper(MemberMapper.class).userInfo(userid);
	}

	@Override
	public boolean idcheck(String userid) throws Exception {
		return sqlSession.getMapper(MemberMapper.class).idcheck(userid) == 1;
	}

	@Override
	public boolean register(MemberDto memberDto) throws Exception {
		return sqlSession.getMapper(MemberMapper.class).register(memberDto) == 1;
	}

	@Override
	public boolean updateInfo(MemberDto memberDto) throws Exception {
		return sqlSession.getMapper(MemberMapper.class).updateInfo(memberDto) == 1;
	}

	@Override
	public boolean deleteUser(String userid) throws Exception {
		return sqlSession.getMapper(MemberMapper.class).deleteUser(userid) == 1;
	}

	@Override
	public boolean deleteArea(String userid) throws Exception {
		return sqlSession.getMapper(MemberMapper.class).deleteArea(userid) == 1;
	}

	@Override
	public boolean registerArea(MemberDto memberDto) throws Exception {
		return sqlSession.getMapper(MemberMapper.class).registerArea(memberDto) >= 1;
	}

	@Override
	public List<SidoGugunCodeDto> getUserArea(String userid) throws Exception {
		return sqlSession.getMapper(MemberMapper.class).getUserArea(userid);
	}
}
