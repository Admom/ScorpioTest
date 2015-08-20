package com.example.guanoweather;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.example.base.Lunar;
import com.example.guanoweather.adapter.ListWeatherAdapter;
import com.example.guanoweather.bean.CityManagerBean;
import com.example.guanoweather.bean.SQLiteCityManager;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

@SuppressLint("NewApi")
public class MainTab01 extends Fragment{
	public static final String TAG = "HomeContent";
	public static List<CityManagerBean> mcmb = new ArrayList<CityManagerBean>();
	public static TextView currentcity;// ��ǰ����
	private TextView pm25;// PMֵ
	private TextView temp;// �¶�
	private TextView pollution;// ��Ⱦ�̶�
	private TextView todaydate;// ũ��
	private ListView tweatherlist;//
	public static String lunarStr = "";
	private View homep_content;
	public FragmentAndActivity mactivity;
	public Weather_Main homepa = (Weather_Main) getActivity();
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		homep_content =inflater.inflate(R.layout.main_tab_01, container, false);
		
		initview();
		setpagedata();
		
		return homep_content;

	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mactivity = (FragmentAndActivity) activity;
		 
	}

	public void setpagedata() {
		// TODO Auto-generated method stub
		System.out.println(Weather_Main.response.getDate()+" bbbbbb");
		System.out.println(getActivity()+"123");
		tweatherlist.setAdapter(new ListWeatherAdapter(getActivity(),
				Weather_Main.response.getResults().get(0)
						.getWeather_data()));
		
		
		
		currentcity.setText(Weather_Main.response.getResults().get(0)
				.getCurrentCity());
		if ("".equals(Weather_Main.response.getResults().get(0).getPm25())) {
			pm25.setText("PM2.5��");
			pollution.setText(R.string.no_data);
			pollution.setBackgroundColor(Color.TRANSPARENT);
		} else {
			pm25.setText("PM2.5��"
					+ Weather_Main.response.getResults().get(0).getPm25());
			int pm = Integer.parseInt(Weather_Main.response.getResults()
					.get(0).getPm25());
			Log.i("TAG", pm + " <-- pm");
			if (pm < 75) {
				pollution.setText(R.string.pollution_no);
				pollution.setBackgroundResource(R.drawable.ic_dl_b);
			} else if (pm > 75 && pm < 100) {
				pollution.setText(R.string.pollution_little);
				pollution.setBackgroundResource(R.drawable.ic_dl_c);
			} else if (pm > 100 && pm < 150) {
				pollution.setText(R.string.pollution_mild);
				pollution.setBackgroundResource(R.drawable.ic_dl_d);
			} else if (pm > 150 && pm < 200) {
				pollution.setText(R.string.polltion_moderate);
				pollution.setBackgroundResource(R.drawable.ic_dl_e);
			} else if (pm > 200) {
				pollution.setText(R.string.polltion_severe);
				pollution.setBackgroundResource(R.drawable.ic_dl_f);
			}
		}
		Calendar cal = Calendar.getInstance();
		// ��ȡϵͳ������
		 int date = cal.get(Calendar.YEAR);
		 String date2 = cal.get(Calendar.MONTH)+1+"";
		 String date3 = cal.get(Calendar.DAY_OF_MONTH)+"";
		Lunar lunar = new Lunar(cal);
		lunarStr = lunar.animalsYear() + "��(";
		lunarStr += lunar.cyclical() + ")";
		lunarStr += lunar.toString();
		if(date2.length()==1){
			date2="0"+date2;
		}
		if(date3.length()==1){
			date3="0"+date3;
		}
		todaydate.setText("ũ����" + lunarStr+"("+date+"-"+date2+"-"+date3+")");
		
		String todaydata = Weather_Main.response.getResults().get(0)
				.getWeather_data().get(0).getDate();
		String temperature = Weather_Main.response.getResults().get(0)
				.getWeather_data().get(0).getTemperature();
		String subs = null;
		if (todaydata.length() > 14) {
			subs = todaydata.substring(14, todaydata.length() - 1);
			temp.setText(subs);
		} else if (temperature.length() > 5) {
			String[] str = temperature.split("~ ", 2);
			subs = str[1];
			temp.setText(subs);
		} else {
			temp.setText(temperature);
		}
		
		// ����SQLite���󲢲��ᴴ�����ݿ�
		SQLiteCityManager sqlite = new SQLiteCityManager(getActivity(),
						"testdb", null, 1);
		// ��д���ݿ�
		SQLiteDatabase db = sqlite.getWritableDatabase();
		// ContentValues��ֵ�ԣ�����HashMap
		ContentValues cv = new ContentValues();
		// keyΪ�ֶ�����valueΪ��������
		cv.put("cityname", Weather_Main.response.getResults().get(0)
						.getCurrentCity());
		cv.put("imageurl", Weather_Main.response.getResults().get(0)
						.getWeather_data().get(0).getDayPictureUrl());
		cv.put("weather", Weather_Main.response.getResults().get(0)
						.getWeather_data().get(0).getWeather());
		cv.put("temp", subs);
		// ���룬�ڶ�������:����Ϊnull���ֶ�
		db.insert("guanoweather", "cityname", cv);
		db.close();
		
		
	}
	
	

	private void initview() {
		// TODO Auto-generated method stub
		currentcity = (TextView) homep_content.findViewById(R.id.currentcity);
		pm25 = (TextView) homep_content.findViewById(R.id.pm25);
		temp = (TextView) homep_content.findViewById(R.id.temp);
		pollution = (TextView) homep_content.findViewById(R.id.pollution_level);
		tweatherlist = (ListView) homep_content
				.findViewById(R.id.tomorrow_weather);
		todaydate = (TextView) homep_content.findViewById(R.id.today_date_dec);
		
		
		
	}

}
