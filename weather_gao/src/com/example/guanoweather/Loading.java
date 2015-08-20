package com.example.guanoweather;
import java.io.IOException;

import org.apache.http.ParseException;
import org.apache.http.util.EntityUtils;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.baidu.mapapi.SDKInitializer;
import com.example.guanoweather.bean.MHttpEntity;
import com.example.guanoweather.bean.ResponseWrapper;
import com.example.guanoweather.bean.SendDataBean;
import com.google.gson.GsonBuilder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

public class Loading extends Activity {
	
	public static ResponseWrapper response;// 数据结构的对象
	public static final int succeed = 1;
	public static final int fail = 2;
	public static final int nonet = 3;
	public String normalDistrict;
	public String normalCity = "上海市";
	public LocationClient mLocationClient = null;
	public BDLocationListener mListener;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.loading);
		
		mLocationClient = new LocationClient(this);
		mListener = new MyLocationListener();
		mLocationClient.registerLocationListener(mListener);// 注册监听函数
		LocationClientOption option = new LocationClientOption();
		
		option.setCoorType("bd09ll");
		option.setIsNeedAddress(true);
		option.setOpenGps(true);
		mLocationClient.setLocOption(option);
		mLocationClient.start();
		
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					Thread.sleep(2000);// 注：异步线程中不能设置UI

				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("当前城市0： "+normalCity);
				sendRequest();
			}
		}).start();
		
		
		
		
		
//		new Handler().postDelayed(new Runnable() {
//			@Override
//			public void run() {
//				
//					Intent intent = new Intent(Loading.this,
//							Weather_Main.class);
//					startActivity(intent);
//					Loading.this.finish();
//
//				Toast.makeText(getApplicationContext(), "登录成功",
//						Toast.LENGTH_SHORT).show();
//			}
//		}, 2000);
		
		
		
		
		
	}
	
	
	private void sendRequest() {
		Log.i("TAG", System.currentTimeMillis()
				+ "System.currentTimeMillis()222");
		String getData = null;
		MHttpEntity mhe = null;
		try {
			SendDataBean.setCity(normalDistrict);
			System.out.println("当前所在区： "+normalDistrict);
			
			mhe = MHttpEntity.sendhttpclient(SendDataBean.getData());
			if (mhe.getHentity() != null) {
				getData = EntityUtils.toString(mhe.getHentity());
				GsonBuilder gson = new GsonBuilder();//
				response = gson.create().fromJson(getData,
						ResponseWrapper.class);
				Log.i("TAG", response.getError() + "-->response.getError()");				
					if (response.getError() == -3) {
						SendDataBean.setCity(normalCity);						
						mhe = MHttpEntity
								.sendhttpclient(SendDataBean.getData());
						if (mhe.getHentity() != null) {
							getData = EntityUtils.toString(mhe.getHentity());
							Log.i("TAG", getData + "-->getData");
						
					}
				}
				mhe.getMessage().obj = getData;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		handler.sendMessage(mhe.getMessage());// 使用Handler对网络状态做处理
		
		
	}
	
	/**
	 * 对网络连接状态做处理
	 */
	Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {			
			if (msg != null)
				switch (msg.arg1) {
				case succeed:// 与服务器连接成功，则传递数据并跳转
					System.out.println((String) msg.obj);
					Intent intent = new Intent(Loading.this,
							Weather_Main.class);
					if (msg.obj != null)
						intent.putExtra("weather_data", (String) msg.obj);
						intent.putExtra("normal_city", normalCity);
						startActivity(intent);
						finish();
					break;
				case fail:// 与服务器连接失败，弹出错误提示Toast
					Toast.makeText(Loading.this,
							getString(R.string.net_fail), Toast.LENGTH_SHORT)
							.show();
					Message Mesg = Message.obtain();
					Mesg.arg1 = nonet;
					handler.sendMessageDelayed(Mesg, 1000);// 延迟发送
					break;
				case nonet:
					finish();// 1秒后关闭页面
					break;
				}
		}
	};
	
	
	
	/**
	 * 拦截返回键
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	public class MyLocationListener implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			if (location == null) {
				return;
			}
				normalDistrict = location.getDistrict();
				normalCity = location.getCity();
				System.out.println("当前城市1： "+normalCity+normalDistrict);
//				if(normalCity == null){
//					Toast.makeText(Loading.this, "定位失败，请检查网络", Toast.LENGTH_SHORT).show();
//				}else{
//					String[] str = normalCity.split("市");
//					normalCity = str[0];
//					System.out.println("当前城市： "+normalCity);
//				}
				
			
		}
	}
	
	
	
	
	
	
}
