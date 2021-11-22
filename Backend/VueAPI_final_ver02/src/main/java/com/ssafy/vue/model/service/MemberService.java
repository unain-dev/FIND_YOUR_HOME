package com.ssafy.vue.model.service;


import com.ssafy.vue.model.MemberDto;

public interface MemberService {

	public MemberDto login(MemberDto memberDto) throws Exception;
	public MemberDto userInfo(String userid) throws Exception;
	public boolean idcheck(String userid) throws Exception;
	public boolean register(MemberDto memberDto) throws Exception;
	public boolean updateInfo(MemberDto memberDto) throws Exception;
	public boolean deleteUser(String userid) throws Exception;
}
