package com.example.guanoweather.bean;

import java.util.List;

public class ResponseWrapper {

	private int error;//�������
	private String status;//���ؽ��״̬��Ϣ
	private String date;//��ǰʱ��
	private List<WeatherBean> results;//����Ԥ����Ϣ
	
	public int getError() {
		return error;
	}
	public void setError(int error) {
		this.error = error;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public List<WeatherBean> getResults() {
		return results;
	}
	public void setResults(List<WeatherBean> results) {
		this.results = results;
	}
	
}
