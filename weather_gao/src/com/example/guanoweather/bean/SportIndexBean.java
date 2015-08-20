package com.example.guanoweather.bean;


public class SportIndexBean {

	private String title;//标题(穿衣，洗车，旅游，感冒 ，运动，紫外线强度)
	private String zs;//是否适宜
	private String tipt;//各项指数名称
	private String des;//简介
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getZs() {
		return zs;
	}
	public void setZs(String zs) {
		this.zs = zs;
	}
	public String getTipt() {
		return tipt;
	}
	public void setTipt(String tipt) {
		this.tipt = tipt;
	}
	public String getDes() {
		return des;
	}
	public void setDes(String des) {
		this.des = des;
	}
	
}
