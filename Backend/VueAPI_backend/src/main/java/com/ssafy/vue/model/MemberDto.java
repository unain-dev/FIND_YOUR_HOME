package com.ssafy.vue.model;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@ApiModel(value = "MemberDto : 회원정보", description = "회원의 상세 정보를 나타낸다.")
public class MemberDto {

	@ApiModelProperty(value = "회원 아이디")
	private String userid;
	@ApiModelProperty(value = "회원 이름")
	private String username;
	@ApiModelProperty(value = "회원 비밀번호")
	private String userpwd;
	@ApiModelProperty(value = "회원 이메일")
	private String email;
	@ApiModelProperty(value = "회원 가입일")
	private String joindate;
	@ApiModelProperty(value = "관리자")
	private boolean admin;
	@ApiModelProperty(value = "동코드 리스트")
	private List<String> dongcodes;

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUserpwd() {
		return userpwd;
	}

	public void setUserpwd(String userpwd) {
		this.userpwd = userpwd;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getJoindate() {
		return joindate;
	}

	public void setJoindate(String joindate) {
		this.joindate = joindate;
	}
	
	public boolean getAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public List<String> getDongcodes() {
		return dongcodes;
	}

	public void setDongcodes(List<String> dongcodes) {
		this.dongcodes = dongcodes;
	}
}
