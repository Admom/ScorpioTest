package com.example.guanoweather.bean;

import java.io.IOException;
import java.net.SocketTimeoutException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;

import android.net.Uri;
import android.os.Message;
import android.util.Log;

public class MHttpEntity {
	
	private Message message;
	private HttpEntity hentity;
	public static final int succeed = 1;
	public static final int fail = 2;
	public static final int nonet = 3;
	
	public Message getMessage() {
		return message;
	}
	public void setMessage(Message message) {
		this.message = message;
	}
	public HttpEntity getHentity() {
		return hentity;
	}
	public void setHentity(HttpEntity hentity) {
		this.hentity = hentity;
	}

	public static MHttpEntity sendhttpclient(String str) {
		Log.i("TAG", System.currentTimeMillis()+"System.currentTimeMillis()333");
		MHttpEntity mhe = new MHttpEntity();
		Message Mesg = Message.obtain();
		HttpEntity he = null;
		HttpClient hClient = new DefaultHttpClient();// ʵ�����õ�һ���������Ӷ���
		HttpConnectionParams.setConnectionTimeout(hClient.getParams(), 5000);//���ӳ�ʱ����
		String mstr = Uri.decode(str);// ��String����תΪuri.����������д˾䡣
		HttpGet hget = new HttpGet(mstr);// ����Http����(get����)
		try {
			HttpResponse re = hClient.execute(hget);// ִ��һ������
			if (re.getStatusLine().getStatusCode() == 200) {
				he = re.getEntity();// �������ʵ��
				Mesg.arg1 = succeed;// ��������״̬
			} else {
				Mesg.arg1 = fail;// ��������״̬
			}
		} catch (SocketTimeoutException e) {
			Mesg.arg1 = fail;
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			Mesg.arg1 = fail;
			e.printStackTrace();
		} catch (IOException e) {
			Mesg.arg1 = fail;
			e.printStackTrace();
		}
		mhe.setMessage(Mesg);
		mhe.setHentity(he);
		Log.i("TAG", mhe.getHentity() + " mhe.getHentity()");
		return mhe;
	}
}
