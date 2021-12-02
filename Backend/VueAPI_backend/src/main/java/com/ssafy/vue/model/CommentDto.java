package com.ssafy.vue.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "CommentDto : Board에 작성하는 댓글에 대한 정보")
public class CommentDto {
	@ApiModelProperty(value = "댓글번호")
	private int memono;
	@ApiModelProperty(value = "작성자ID")
	private String userid;
	@ApiModelProperty(value = "댓글내용")
	private String comment;
	@ApiModelProperty(value = "댓글작성시각")
	private String memotime;
	@ApiModelProperty(value = "글 번호")
	private String articleno;
	
	public int getMemono() {
		return memono;
	}
	public void setMemono(int memono) {
		this.memono = memono;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getMemotime() {
		return memotime;
	}
	public void setMemotime(String memotime) {
		this.memotime = memotime;
	}
	public String getArticleno() {
		return articleno;
	}
	public void setArticleno(String articleno) {
		this.articleno = articleno;
	}
	@Override
	public String toString() {
		return "CommentDto [memono=" + memono + ", userid=" + userid + ", comment=" + comment + ", memotime=" + memotime
				+ ", articleno=" + articleno + "]";
	}
	
}
