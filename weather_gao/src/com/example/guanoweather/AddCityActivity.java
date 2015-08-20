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
				// ������ݿ���û�иó��У�����ӵ����ݿ⡣��֮����ʾ��
				if(!ishas){
					insertdata();
					finish();
				}else{
					Toast.makeText(AddCityActivity.this, "�����ظ����",
							Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	// ����SQLite���󲢲��ᴴ�����ݿ�
	private SQLiteCityManager sqlite = new SQLiteCityManager(
			AddCityActivity.this, "testdb", null, 1);

	// ��������
	private void insertdata() {
		// ��д���ݿ�
		SQLiteDatabase db = sqlite.getReadableDatabase();
		// ContentValues��ֵ�ԣ�����HashMap
		ContentValues cv = new ContentValues();
		// keyΪ�ֶ�����valueΪ��������
		cv.put("cityname", citytextview.getText().toString());
		cv.put("imageurl", "");
		cv.put("weather", "�������");
		cv.put("temp", "0��");
		//
		db.insert("guanoweather", "cityname", cv);
	}

	public void querydata(String str) {
		// ��д���ݿ�
		SQLiteDatabase db = sqlite.getReadableDatabase();
		Cursor cursor = db.query("guanoweather", null, null, null, null, null,
				null);
		while (cursor.moveToNext()) {
			String cityname = cursor.getString(cursor
					.getColumnIndex("cityname"));
			cityname = cityname.substring(0, 2);
			str = str.substring(0, 2);
			// �뵱ǰ���µĳ��������Ƚ�
			if (ishas = cityname.equals(str)) {
				return;
			}
		}
	}
}
