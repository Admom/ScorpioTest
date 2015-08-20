package com.example.guanoweather;
import com.example.guanoweather.adapter.GridCityMAdapter;
import com.example.guanoweather.bean.CityManagerBean;
import com.example.guanoweather.bean.SQLiteCityManager;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.AdapterView.OnItemClickListener;

@SuppressLint("NewApi")
public class MainTab03 extends Fragment{
	private GridView mgridview;
	private String cityname;
	private String imageurl;
	private String weather;
	private String temp;
	public CityManagerBean cmb;
	public GridCityMAdapter cmAdapter;
	private SQLiteCityManager sqlite;
	private SQLiteDatabase db;
	
	private View homep_content;	
	
	public Intent intent;
	private FragmentAndActivity mActivity;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		homep_content = inflater.inflate(R.layout.main_tab_03, container, false);
		initview();
		setpagedata();
		
		return homep_content;
	}
	
	
	
	
	

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		getdatabase();
		for (int i = 0; i < MainTab01.mcmb.size(); i++) {
			if (MainTab01.mcmb.get(i).getCity()
					.equals(Weather_Main.cmb2.getCity())) {
				MainTab01.mcmb.remove(Weather_Main.cmb2);
			}
		}
		
		// 标记，为每次打开城市管理页都会加载一个item问题的解决方案
		Weather_Main.cmb2.setCity("添加");
		MainTab01.mcmb.add(MainTab01.mcmb.size(),Weather_Main.cmb2);
		cmAdapter.setCitymanager(MainTab01.mcmb);
		cmAdapter.notifyDataSetChanged();
		
		
		super.onResume();
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mActivity = (FragmentAndActivity) activity;
	}




	private void getdatabase() {
		// TODO Auto-generated method stub
		// 创建SQLite对象并不会创建数据库
		sqlite = new SQLiteCityManager(getActivity(),
						"testdb", null, 1);
		// 读写数据库
		db = sqlite.getWritableDatabase();
		// 查询的条件
		Cursor cursor = db.query("guanoweather", null, null, null, null, null,
						null);
		MainTab01.mcmb.clear();
		// 循环的从数据库中取出
				while (cursor.moveToNext()) {
					int _id = cursor.getInt(cursor.getColumnIndex("_id"));
					cityname = cursor.getString(cursor.getColumnIndex("cityname"));
					imageurl = cursor.getString(cursor.getColumnIndex("imageurl"));
					weather = cursor.getString(cursor.getColumnIndex("weather"));
					temp = cursor.getString(cursor.getColumnIndex("temp"));
					setCityManagerBean();
				}
		
		
		
		
	}






	private void setCityManagerBean() {
		// TODO Auto-generated method stub
		cmb = new CityManagerBean();
		cmb.setCity(cityname);
		cmb.setWeather(weather);
		cmb.setTemp(temp);
		cmb.setWeatherimage(imageurl);
		for (int i = 0; i < MainTab01.mcmb.size(); i++) {
			
			if (MainTab01.mcmb.get(i).getCity().equals(cmb.getCity())) {
				MainTab01.mcmb.set(i, cmb);
				return;
			}
		}
		MainTab01.mcmb.add(cmb);
		
		
		
		
		
		
	}






	private void setpagedata() {
		// TODO Auto-generated method stub
		intent = new Intent(getActivity(), AddCityActivity.class);
		mgridview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (position == MainTab01.mcmb.size() - 1) {
					startActivity(intent);
				} 
				else {
					mActivity.showDialog();					
					
					Weather_Main ff = (Weather_Main) getActivity();
					ff.chagepage();
//					ff.chagepage(Weather_Main.homecontent,
//							MainTab01.TAG);
				
					// 得到城市，发起网络请求。
					System.out.println("获取到的城市："+MainTab01.mcmb.get(
							position).getCity());
					mActivity.sendcitytext(MainTab01.mcmb.get(
							position).getCity());
				}
			}
		});
		
		
		
		cmAdapter = new GridCityMAdapter(getActivity(),
				MainTab01.mcmb);
		mgridview.setAdapter(cmAdapter);
		
	}

	private void initview() {
		// TODO Auto-generated method stub
		mgridview = (GridView) homep_content.findViewById(R.id.gridview);
	}

}
