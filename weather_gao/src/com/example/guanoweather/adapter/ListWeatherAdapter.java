package com.example.guanoweather.adapter;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.base.CHImageView;
import com.example.guanoweather.R;
import com.example.guanoweather.bean.WeatherSubBean;

public class ListWeatherAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	private List<WeatherSubBean> tweather;

	public ListWeatherAdapter(Context context, List<WeatherSubBean> tweather) {
		this.mInflater = LayoutInflater.from(context);
		this.tweather = tweather;
	}

	@Override
	public int getCount() {
		return tweather == null ? 0 : tweather.size();
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.item_listview_tomorrow,
					null);
		}
		// ����
		TextView list_date_nextday = (TextView) convertView
				.findViewById(R.id.list_date_nextday);
		if (position > 0) {
			list_date_nextday.setText(tweather.get(position).getDate());
		} else if (position == 0) {
			list_date_nextday.setText("����");
		} 

		// ��������ͼƬ
		CHImageView list_day_picture = (CHImageView) convertView
				.findViewById(R.id.list_day_picture);
		String dayp = tweather.get(position).getDayPictureUrl();
		list_day_picture.loadImage(dayp);
		
		// ҹ������ͼƬ
		CHImageView list_night_picture = (CHImageView) convertView
				.findViewById(R.id.list_night_picture);
		String nightp = tweather.get(position).getNightPictureUrl();
		list_night_picture.loadImage(nightp);

		// �������
		TextView list_weather = (TextView) convertView
				.findViewById(R.id.list_weather);
		list_weather.setText(tweather.get(position).getWeather());
		// �¶�
		TextView list_temperature = (TextView) convertView
				.findViewById(R.id.list_temperature);
		String str = tweather.get(position).getTemperature();
		if(str.length() > 4){
			//Ĭ�Ͻ�����¶���ʾ��ǰ����㷨
			String str1 = str.substring(0, str.length()-1);
			String str2 = str.substring(str.length()-1, str.length());
			Log.i("TAG", str2);
			String [] mstr = str1.split("~", 2);
			list_temperature.setText(mstr[1]+" ~ "+mstr[0]+str2);
		}else{
			list_temperature.setText(str);
		}
		
		// ����
		TextView list_wind = (TextView) convertView
				.findViewById(R.id.list_wind);
		list_wind.setText(tweather.get(position).getWind());

		return convertView;
	}

//	/**
//	 * ��InputStreamת��ΪBitmap
//	 * 
//	 * @param mhe
//	 */
//	private Bitmap inputstreamTbitmap(MHttpEntity mhe) {
//		Bitmap bt = null;
//		try {
//			if (mhe.getHentity() != null) {
//				InputStream is = mhe.getHentity().getContent();
//				bt = BitmapFactory.decodeStream(is);
//			}
//		} catch (IllegalStateException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return bt;
//	}

	/**
	 * ����item�ĵ���¼�
	 */
	@Override
	public boolean isEnabled(int position) {
		return false;
	}

}
