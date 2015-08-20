package com.example.guanoweather.bean;

import java.util.List;

public class WeatherBean {

	private String currentCity;//��ǰ����
	private List<WeatherSubBean> weather_data;//����Ԥ����Ϣ
	private String pm25;//PM2.5ֵ
	private List<SportIndexBean> index;//����ָ��
	
	public String getCurrentCity() {
		return currentCity;
	}
	public void setCurrentCity(String currentCity) {
		this.currentCity = currentCity;
	}
	public List<WeatherSubBean> getWeather_data() {
		return weather_data;
	}
	public void setWeather_data(List<WeatherSubBean> weather_data) {
		this.weather_data = weather_data;
	}
	public String getPm25() {
		return pm25;
	}
	public void setPm25(String pm25) {
		this.pm25 = pm25;
	}
	public List<SportIndexBean> getIndex() {
		return index;
	}
	public void setIndex(List<SportIndexBean> index) {
		this.index = index;
	}
}
