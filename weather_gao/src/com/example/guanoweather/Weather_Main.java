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
	
	private long nowtime;// ���浱ǰʱ��
	public static ResponseWrapper response = new ResponseWrapper();// ���ݽṹ�Ķ���
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
	 * �ײ�3����ť
	 */
	private LinearLayout mTabBtnWeixin;
	private LinearLayout mTabBtnFrd;
	private LinearLayout mTabBtnSettings;
	
	
	private DrawerLayout drawerlayout_main;// drawerlayout_main
	private View left_drawer;
	
	/**
	 * ���ڶ�Fragment���й���
	 */
	private FragmentManager fragmentManager;
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_main);
		Intent intent = getIntent();
		String wetherdata = intent.getStringExtra("weather_data");// �õ�����ҳ���ݹ���������
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
		drawerlayout_main.setScrimColor(0x00000000);// ���õײ�ҳ�汳��͸����
		
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
			showToast("������...");
			drawerlayout_main.closeDrawer(left_drawer);
			break;
		case R.id.button2:
			//TODO..
			showToast("������...");
			drawerlayout_main.closeDrawer(left_drawer);
			break;
		case R.id.button3:
			//TODO..
			showToast("������...");
			drawerlayout_main.closeDrawer(left_drawer);
			break;
		
		case R.id.button5:
			//TODO..
			showToast("������...");
			drawerlayout_main.closeDrawer(left_drawer);
			break;
		case R.id.button6:
			//TODO..
			showToast("������...");
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
	 * ����menu��
	 */
	private void openleftlayout() {
		if (drawerlayout_main.isDrawerOpen(left_drawer)) {
			drawerlayout_main.closeDrawer(left_drawer);
		} else {
			drawerlayout_main.openDrawer(left_drawer);
		}
	}
	
	
	/**
	 * ���ݴ����index����������ѡ�е�tabҳ��
	 * 
	 */
	@SuppressLint("NewApi")
	private void setTabSelection(int index)
	{
		// ���ð�ť
		resetBtn();
		// ����һ��Fragment����
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		// �����ص����е�Fragment���Է�ֹ�ж��Fragment��ʾ�ڽ����ϵ����
		hideFragments(transaction);
		switch (index)
		{
		case 0:
			// ���������Ϣtabʱ���ı�ؼ���ͼƬ��������ɫ
			((ImageButton) mTabBtnWeixin.findViewById(R.id.btn_tab_bottom_weixin))
					.setImageResource(R.drawable.tab_weixin_pressed);
			if (mTab01 == null)
			{
				// ���MessageFragmentΪ�գ��򴴽�һ������ӵ�������
				mTab01 = new MainTab01();
				transaction.add(R.id.id_content, mTab01);
			} else
			{
				// ���MessageFragment��Ϊ�գ���ֱ�ӽ�����ʾ����
				transaction.show(mTab01);
			}
			break;
		case 1:
			// ���������Ϣtabʱ���ı�ؼ���ͼƬ��������ɫ
			((ImageButton) mTabBtnFrd.findViewById(R.id.btn_tab_bottom_friend))
					.setImageResource(R.drawable.tab_find_frd_pressed);
			if (mTab02 == null)
			{
				// ���MessageFragmentΪ�գ��򴴽�һ������ӵ�������
				mTab02 = new MainTab02();
				transaction.add(R.id.id_content, mTab02);
			} else
			{
				// ���MessageFragment��Ϊ�գ���ֱ�ӽ�����ʾ����
				transaction.show(mTab02);
			}
			break;
		
		case 2:
			// �����������tabʱ���ı�ؼ���ͼƬ��������ɫ
			((ImageButton) mTabBtnSettings.findViewById(R.id.btn_tab_bottom_setting))
					.setImageResource(R.drawable.tab_settings_pressed);
			if (mTab03 == null)
			{
				// ���SettingFragmentΪ�գ��򴴽�һ������ӵ�������
				mTab03 = new MainTab03();
				transaction.add(R.id.id_content, mTab03);
			} else
			{
				// ���SettingFragment��Ϊ�գ���ֱ�ӽ�����ʾ����
				transaction.show(mTab03);
			}
			break;
		}
		transaction.commit();
	}
	
	/**
	 * ��������е�ѡ��״̬��
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
	 * �����е�Fragment����Ϊ����״̬��
	 * 
	 * @param transaction
	 *            ���ڶ�Fragmentִ�в���������
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
	 * ���������η������˳�����
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
	 * �������������������
	 */
	public void sendRequest(String cityname) {
		String getData = null;
		MHttpEntity mhe = null;
		try {
			SendDataBean.setCity(cityname);// ��ȡ�û�����ĳ�����
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
		handler.sendMessage(mhe.getMessage());// ʹ��Handler������״̬������
	}
	
	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if (pDialog != null)
				pDialog.dismiss();
			if (msg != null)
				switch (msg.arg1) {
				case succeed:// ����������ӳɹ�
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
				case fail:// �����������ʧ��
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
			SendDataBean.setCity(inputcitytext);// ��ȡ�û��������
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
		pDialog.setCancelable(true);// �������ȡ��Dialog��չ��
		pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pDialog.setMessage("���ڸ���...");
		pDialog.show();
	}
	
	/**
	 * ������bt��Toastֻ��ʾһ�εĽ������
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
