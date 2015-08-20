package com.example.guanoweather.adapter;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.guanoweather.R;
import com.example.guanoweather.bean.SQLiteCityManager;

public class GridAddCityAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	private static final String[] cityname = 
			{ "����", "�Ϻ�", "����", "�Ͼ�", "�ɶ�", "�人", "����", "����", "����", "����", "��ݸ",
					"����", "���", "������", "��ɳ", "���ͺ���", "ʯ��ׯ", "����", "����", "��ͷ",
					"����", "����", "����", "����", "��³ľ��", "����", "����", "̫ԭ", "֣��",
					"�Ϸ�", "�ϲ�", "����", "����", "����", "����", "����", "̨��", "���", "����" };
	SparseBooleanArray sba = new SparseBooleanArray();
	private String nowcityname;

	public GridAddCityAdapter(Context context) {
		this.mInflater = LayoutInflater.from(context);

		SQLiteCityManager sqlite = new SQLiteCityManager(context, "testdb", null, 1);
		SQLiteDatabase db = sqlite.getReadableDatabase();
		Cursor cursor = db.query("guanoweather", null, null, null, null, null, null);
		while(cursor.moveToNext()){
			nowcityname = cursor.getString(cursor.getColumnIndex("cityname"));
			Log.i("TAG", nowcityname+"-->nowcityname");
			for(int i=0;i<cityname.length;i++){
				if(nowcityname.equals(cityname[i])){
					sba.put(i, true);
				}
			}
		}
	}

	@Override
	public int getCount() {
		return cityname == null ? 0 : cityname.length;
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.item_gridview_addcity,
					parent, false);// �˴���Ҫ���ϵڶ�������parent������item�е�������Ч����item�߶����á�
		}
		TextView citytext = (TextView) convertView.findViewById(R.id.citytext);
		citytext.setText(cityname[position]);

		// ��ѯ���ݿ⣬���ݿ����иó��������ù�ѡ
		if (sba.get(position)) {
			citytext.setCompoundDrawablesWithIntrinsicBounds(0, 0,
					R.drawable.city_checkbox_selected, 0);
		}
		return convertView;
	}
}
