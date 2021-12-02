package com.ssafy.vue.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "HouseInfoDto : 아파트정보", description = "아파트의 상세 정보를 나타낸다.")
public class AptInfoDto {
	@ApiModelProperty(value = "일련번호")
	private String aptCode;
	@ApiModelProperty(value = "아파트이름")
	private String aptName;
	@ApiModelProperty(value = "법정동읍면동코드")
	private String dongCode;
	@ApiModelProperty(value = "법정동")
	private String dongName;
	@ApiModelProperty(value = "법정동시군구코드")
	private String sidogugunCode;
	@ApiModelProperty(value = "도로명")
	private String roadName;
	@ApiModelProperty(value = "건축년도")
	private int buildYear;
	@ApiModelProperty(value = "거래금액")
	private String recentPrice;
	@ApiModelProperty(value = "층")
	private int floor;
	@ApiModelProperty(value = "지번")
	private String jibun;
	@ApiModelProperty(value = "위도")
	private String lat;
	@ApiModelProperty(value = "경도")
	private String lng;
	@ApiModelProperty(value = "아파트이미지")
	private String img;
	
	public String getAptCode() {
		return aptCode;
	}
	public void setAptCode(String aptCode) {
		this.aptCode = aptCode;
	}
	public String getAptName() {
		return aptName;
	}
	public void setAptName(String aptName) {
		this.aptName = aptName;
	}
	public String getDongCode() {
		return dongCode;
	}
	public void setDongCode(String dongCode) {
		this.dongCode = dongCode;
	}
	public String getDongName() {
		return dongName;
	}
	public void setDongName(String dongName) {
		this.dongName = dongName;
	}
	public String getSidogugunCode() {
		return sidogugunCode;
	}
	public void setSidogugunCode(String sidogugunCode) {
		this.sidogugunCode = sidogugunCode;
	}
	public String getRoadName() {
		return roadName;
	}
	public void setRoadName(String roadName) {
		this.roadName = roadName;
	}
	public int getBuildYear() {
		return buildYear;
	}
	public void setBuildYear(int buildYear) {
		this.buildYear = buildYear;
	}
	public String getRecentPrice() {
		return recentPrice;
	}
	public void setRecentPrice(String recentPrice) {
		this.recentPrice = recentPrice;
	}
	public int getFloor() {
		return floor;
	}
	public void setFloor(int floor) {
		this.floor = floor;
	}
	public String getJibun() {
		return jibun;
	}
	public void setJibun(String jibun) {
		this.jibun = jibun;
	}
	public String getLat() {
		return lat;
	}
	public void setLat(String lat) {
		this.lat = lat;
	}
	public String getLng() {
		return lng;
	}
	public void setLng(String lng) {
		this.lng = lng;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	@Override
	public String toString() {
		return "AptInfoDto [aptCode=" + aptCode + ", aptName=" + aptName + ", dongCode=" + dongCode + ", dongName="
				+ dongName + ", sidogugunCode=" + sidogugunCode + ", roadName=" + roadName + ", buildYear=" + buildYear
				+ ", recentPrice=" + recentPrice + ", floor=" + floor + ", jibun=" + jibun + ", lat=" + lat + ", lng="
				+ lng + ", img=" + img + "]";
	}
	
}
