package com.example.guanoweather;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.guanoweather.adapter.GridAddCityAdapter;
import com.example.guanoweather.bean.SQLiteCityManager;

public class AddCityActivity extends Activity {

	private GridView addcity_gridview;
	private static TextView citytextview;
	private boolean ishas;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addcity_activity);
		addcity_gridview = (GridView) findViewById(R.id.addcity_gridview);
		
		GridAddCityAdapter ad = new GridAddCityAdapter(this);
		
		addcity_gridview.setAdapter(ad);
		
		addcity_gridview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				citytextview = (TextView) view.findViewById(R.id.citytext);
				citytextview.setCompoundDrawablesWithIntrinsicBounds(0, 0,
						R.drawable.city_checkbox_selected, 0);
				querydata(citytextview.getText().toString());
				// 如果数据库中没有该城市，则添加到数据库。反之则提示。
				if(!ishas){
					insertdata();
					finish();
				}else{
					Toast.makeText(AddCityActivity.this, "不可重复添加",
							Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	// 创建SQLite对象并不会创建数据库
	private SQLiteCityManager sqlite = new SQLiteCityManager(
			AddCityActivity.this, "testdb", null, 1);

	// 插入数据
	private void insertdata() {
		// 读写数据库
		SQLiteDatabase db = sqlite.getReadableDatabase();
		// ContentValues键值对，类似HashMap
		ContentValues cv = new ContentValues();
		// key为字段名，value为所存数据
		cv.put("cityname", citytextview.getText().toString());
		cv.put("imageurl", "");
		cv.put("weather", "点击更新");
		cv.put("temp", "0℃");
		//
		db.insert("guanoweather", "cityname", cv);
	}

	public void querydata(String str) {
		// 读写数据库
		SQLiteDatabase db = sqlite.getReadableDatabase();
		Cursor cursor = db.query("guanoweather", null, null, null, null, null,
				null);
		while (cursor.moveToNext()) {
			String cityname = cursor.getString(cursor
					.getColumnIndex("cityname"));
			cityname = cityname.substring(0, 2);
			str = str.substring(0, 2);
			// 与当前按下的城市名做比较
			if (ishas = cityname.equals(str)) {
				return;
			}
		}
	}
}
