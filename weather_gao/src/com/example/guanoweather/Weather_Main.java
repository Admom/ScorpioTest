package com.example.guanoweather;

import java.io.IOException;

import org.apache.http.ParseException;
import org.apache.http.util.EntityUtils;

import com.example.guanoweather.bean.CityManagerBean;
import com.example.guanoweather.bean.MHttpEntity;
import com.example.guanoweather.bean.ResponseWrapper;
import com.example.guanoweather.bean.SendDataBean;
import com.google.gson.GsonBuilder;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Weather_Main extends FragmentActivity implements
	OnClickListener, FragmentAndActivity{
	
	private long nowtime;// 保存当前时间
	public static ResponseWrapper response = new ResponseWrapper();// 数据结构的对象
	public static ResponseWrapper response2;
	
	public static final int succeed = 1;
	public static final int fail = 2;
	public static final int nonet = 3;
	private ProgressDialog pDialog;
	
	private EditText inputcity;
	
	public static CityManagerBean cmb2 = new CityManagerBean();
	public static MainTab01 homecontent = new MainTab01();
	
	private MainTab01 mTab01;
	private MainTab02 mTab02;
	private MainTab03 mTab03;
	
	/**
	 * 底部3个按钮
	 */
	private LinearLayout mTabBtnWeixin;
	private LinearLayout mTabBtnFrd;
	private LinearLayout mTabBtnSettings;
	
	
	private DrawerLayout drawerlayout_main;// drawerlayout_main
	private View left_drawer;
	
	/**
	 * 用于对Fragment进行管理
	 */
	private FragmentManager fragmentManager;
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_main);
		Intent intent = getIntent();
		String wetherdata = intent.getStringExtra("weather_data");// 得到启动页传递过来的数据
		GsonBuilder gson = new GsonBuilder();//
		response2 = gson.create().fromJson(wetherdata, ResponseWrapper.class);
		if(response2.getError() == 0){
			response = response2;
		}
		initViews();
		fragmentManager = getFragmentManager();
		setTabSelection(0);
		
	
	}

	private void initViews()
	{

		mTabBtnWeixin = (LinearLayout) findViewById(R.id.id_tab_bottom_weixin);
		mTabBtnFrd = (LinearLayout) findViewById(R.id.id_tab_bottom_friend);		
		mTabBtnSettings = (LinearLayout) findViewById(R.id.id_tab_bottom_setting);
		
		drawerlayout_main = (DrawerLayout) findViewById(R.id.drawerlayout_main);
		left_drawer = findViewById(R.id.left_drawer);
		drawerlayout_main.setScrimColor(0x00000000);// 设置底部页面背景透明度
		
		mTabBtnWeixin.setOnClickListener(this);
		mTabBtnFrd.setOnClickListener(this);		
		mTabBtnSettings.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
		case R.id.id_tab_bottom_weixin:
			setTabSelection(0);
			break;
		case R.id.id_tab_bottom_friend:
			setTabSelection(1);
			break;
		
		case R.id.id_tab_bottom_setting:
			setTabSelection(2);
			break;
		case R.id.homep_menu:
			openleftlayout();
			break;
		case R.id.homep_refresh:
			showDialog();
			chagepage();
			new Thread(new Runnable() {

				@Override
				public void run() {
					sendRequest(MainTab01.currentcity.getText()
							.toString());
				}
			}).start();
			
			
			
			
			break;
		case R.id.button1:
			//TODO..
			showToast("开发中...");
			drawerlayout_main.closeDrawer(left_drawer);
			break;
		case R.id.button2:
			//TODO..
			showToast("开发中...");
			drawerlayout_main.closeDrawer(left_drawer);
			break;
		case R.id.button3:
			//TODO..
			showToast("开发中...");
			drawerlayout_main.closeDrawer(left_drawer);
			break;
		
		case R.id.button5:
			//TODO..
			showToast("开发中...");
			drawerlayout_main.closeDrawer(left_drawer);
			break;
		case R.id.button6:
			//TODO..
			showToast("开发中...");
			drawerlayout_main.closeDrawer(left_drawer);
			break;
		case R.id.exitapp:	
			drawerlayout_main.closeDrawer(left_drawer);
			final Dialog dialog = new Dialog(this, 
					R.style.Dialog);
			View exitappview = getLayoutInflater().inflate
					(R.layout.exit_dialog, null);
			
			Button leftbutton = (Button) exitappview.findViewById(R.id.exitBtn0);
			Button rightbutton = (Button) exitappview.findViewById(R.id.exitBtn1);
			leftbutton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					finish();
				}
			});
			rightbutton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					dialog.dismiss();
				}
			});
			dialog.setContentView(exitappview);
			dialog.show();
			break;
		default:
			break;
		}
	}
	
	/**
	 * 关联menu键
	 */
	private void openleftlayout() {
		if (drawerlayout_main.isDrawerOpen(left_drawer)) {
			drawerlayout_main.closeDrawer(left_drawer);
		} else {
			drawerlayout_main.openDrawer(left_drawer);
		}
	}
	
	
	/**
	 * 根据传入的index参数来设置选中的tab页。
	 * 
	 */
	@SuppressLint("NewApi")
	private void setTabSelection(int index)
	{
		// 重置按钮
		resetBtn();
		// 开启一个Fragment事务
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		// 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
		hideFragments(transaction);
		switch (index)
		{
		case 0:
			// 当点击了消息tab时，改变控件的图片和文字颜色
			((ImageButton) mTabBtnWeixin.findViewById(R.id.btn_tab_bottom_weixin))
					.setImageResource(R.drawable.tab_weixin_pressed);
			if (mTab01 == null)
			{
				// 如果MessageFragment为空，则创建一个并添加到界面上
				mTab01 = new MainTab01();
				transaction.add(R.id.id_content, mTab01);
			} else
			{
				// 如果MessageFragment不为空，则直接将它显示出来
				transaction.show(mTab01);
			}
			break;
		case 1:
			// 当点击了消息tab时，改变控件的图片和文字颜色
			((ImageButton) mTabBtnFrd.findViewById(R.id.btn_tab_bottom_friend))
					.setImageResource(R.drawable.tab_find_frd_pressed);
			if (mTab02 == null)
			{
				// 如果MessageFragment为空，则创建一个并添加到界面上
				mTab02 = new MainTab02();
				transaction.add(R.id.id_content, mTab02);
			} else
			{
				// 如果MessageFragment不为空，则直接将它显示出来
				transaction.show(mTab02);
			}
			break;
		
		case 2:
			// 当点击了设置tab时，改变控件的图片和文字颜色
			((ImageButton) mTabBtnSettings.findViewById(R.id.btn_tab_bottom_setting))
					.setImageResource(R.drawable.tab_settings_pressed);
			if (mTab03 == null)
			{
				// 如果SettingFragment为空，则创建一个并添加到界面上
				mTab03 = new MainTab03();
				transaction.add(R.id.id_content, mTab03);
			} else
			{
				// 如果SettingFragment不为空，则直接将它显示出来
				transaction.show(mTab03);
			}
			break;
		}
		transaction.commit();
	}
	
	/**
	 * 清除掉所有的选中状态。
	 */
	private void resetBtn()
	{
		((ImageButton) mTabBtnWeixin.findViewById(R.id.btn_tab_bottom_weixin))
				.setImageResource(R.drawable.tab_weixin_normal);
		((ImageButton) mTabBtnFrd.findViewById(R.id.btn_tab_bottom_friend))
				.setImageResource(R.drawable.tab_find_frd_normal);
		((ImageButton) mTabBtnSettings.findViewById(R.id.btn_tab_bottom_setting))
				.setImageResource(R.drawable.tab_settings_normal);
	}
	
	/**
	 * 将所有的Fragment都置为隐藏状态。
	 * 
	 * @param transaction
	 *            用于对Fragment执行操作的事务
	 */
	@SuppressLint("NewApi")
	private void hideFragments(FragmentTransaction transaction)
	{
		if (mTab01 != null)
		{
			transaction.hide(mTab01);
		}
		if (mTab02 != null)
		{
			transaction.hide(mTab02);
		}
		if (mTab03 != null)
		{
			transaction.hide(mTab03);
		}
		
		
	}
	
	private void fromJson(String wetherdata) {
		GsonBuilder gson = new GsonBuilder();//
		response2 = gson.create().fromJson(wetherdata, ResponseWrapper.class);
		if (response2.getError() == 0) {
			response = response2;
			homecontent.setpagedata();
			
		} else if (response2.getError() == -3 || response2.getError() == -2) {
			showToast(getString(R.string.input_truename));
		} else {
			showToast(getString(R.string.getdata_fail));
		}
		
	}
	
	
	
	
	
	/**
	 * 连续按两次返回则退出程序
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (System.currentTimeMillis() - nowtime > 2000) {
				Toast.makeText(this, R.string.click_exit, Toast.LENGTH_SHORT)
						.show();
				nowtime = System.currentTimeMillis();
				return true;
			} else {
				finish();
			}
		}
		return super.onKeyDown(keyCode, event);
	}
	
	/**
	 * 向服务器发送数据请求
	 */
	public void sendRequest(String cityname) {
		String getData = null;
		MHttpEntity mhe = null;
		try {
			SendDataBean.setCity(cityname);// 获取用户输入的城市名
			mhe = MHttpEntity.sendhttpclient(SendDataBean.getData());
			if (mhe.getHentity() != null) {
				getData = EntityUtils.toString(mhe.getHentity());
				mhe.getMessage().obj = getData;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		handler.sendMessage(mhe.getMessage());// 使用Handler对网络状态做处理
	}
	
	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if (pDialog != null)
				pDialog.dismiss();
			if (msg != null)
				switch (msg.arg1) {
				case succeed:// 与服务器连接成功
					if (msg.obj != null) {
						//fromJson(msg.obj.toString());
						Intent intent = new Intent(Weather_Main.this,
								Weather_Main.class);
						intent.putExtra("weather_data", (String) msg.obj);
						//intent.putExtra("normal_city", normalCity);
						startActivity(intent);
						finish();
					}
					break;
				case fail:// 与服务器连接失败
					showToast(getString(R.string.net_fail));
					break;
				}
		}
	};
	

	@Override
	public void senddata(EditText inputcity) {
		// TODO Auto-generated method stub
		this.inputcity = inputcity;
	}

	@Override
	public void sendcitytext(final String inputcitytext) {
		// TODO Auto-generated method stub
		if ("".equals(inputcitytext)) {
			showToast(getString(R.string.edittext_hint));
			
		} else {
			SendDataBean.setCity(inputcitytext);// 获取用户输入城市
			new Thread(new Runnable() {

				@Override
				public void run() {
					sendRequest(inputcitytext);
				}
			}).start();
		}
	}

	@Override
	public void showDialog() {
		// TODO Auto-generated method stub
		pDialog = new ProgressDialog(Weather_Main.this);
		pDialog.setCancelable(true);// 点击可以取消Dialog的展现
		pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pDialog.setMessage("正在更新...");
		pDialog.show();
	}
	
	/**
	 * 点击多次bt，Toast只显示一次的解决方案
	 */
	public Toast toast = null;

	public void showToast(String text) {
		if (toast == null) {
			toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
		} else {
			toast.setText(text);
		}
		toast.show();
	}
	
	public void chagepage() {
		setTabSelection(0);		
	}
	
	
	
}
