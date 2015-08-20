package com.example.guanoweather.adapter;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.guanoweather.R;
import com.example.guanoweather.bean.SportIndexBean;

public class GridTodayCAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	private List<SportIndexBean> sportIndex;
	private int[] resours = {R.drawable.ic_todaycan_dress,
			R.drawable.ic_todaycan_carwash, R.drawable.ic_todaycan_tour,
			R.drawable.ic_todaycan_coldl, R.drawable.ic_todaycan_sport,
			R.drawable.ic_todaycan_ultravioletrays };

	public GridTodayCAdapter(Context context, List<SportIndexBean> sportIndex) {
		this.mInflater = LayoutInflater.from(context);
		this.sportIndex = sportIndex;
	}

	@Override
	public int getCount() {
		return sportIndex == null ? 0 : sportIndex.size();
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
			// �˴���Ҫ���ϵڶ�������parent������item�е�������Ч����item�߶����á�
			convertView = mInflater.inflate(R.layout.item_gridview_todaycan,
					parent, false);
		}
		TextView dothing = (TextView) convertView.findViewById(R.id.dothing);
		TextView index = (TextView) convertView.findViewById(R.id.index);
		ImageView image_index = (ImageView) convertView
				.findViewById(R.id.image_index);
		ImageView image_click = (ImageView) convertView
				.findViewById(R.id.image_click);

		// ��������
		if (position == 0){
			dothing.setText("����ָ��");
		}else if (position == 1) {
			dothing.setText("ϴ��ָ��");
		}else if (position == 2) {
			dothing.setText("����ָ��");
		}else if (position == 3) {
			dothing.setText("��ðָ��");
		}else if (position == 4) {
			dothing.setText("�˶�ָ��");
		}else if (position == 5) {
			dothing.setText("������ָ��");
		}else {
			dothing.setText(sportIndex.get(position).getTipt());
		}

		try{
			if(sportIndex.get(position).getZs() != null){
				
					index.setText(sportIndex.get(position).getZs());
				
			}else{
				index.setText("����");
			}
		}catch(NullPointerException e){
			index.setText("����");
		}
		Log.i("TAG", sportIndex.size()+"sportIndex.size()");
		image_index.setBackgroundResource(resours[position]);
		image_click.setBackgroundResource(R.drawable.ic_todaycan_clickbt);
		return convertView;
	}
}
