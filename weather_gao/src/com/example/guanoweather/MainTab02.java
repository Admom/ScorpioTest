package com.example.guanoweather;

import java.util.List;

import com.example.guanoweather.adapter.GridTodayCAdapter;
import com.example.guanoweather.bean.SportIndexBean;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

@SuppressLint("NewApi")
public class MainTab02 extends Fragment{
	
	public TextView todaycan_dec;
	GridView todayinfo_grid ;
	
	public SportIndexBean sib, sib1, sib2, sib3, sib4, sib5, sib6, sib7;
	public List<SportIndexBean> listsib;
	private View homep_content;

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		homep_content =inflater.inflate(R.layout.main_tab_02, container, false);
		initview();
		setpagedata();
		
		return homep_content;

	}

	private void setpagedata() {
		// TODO Auto-generated method stub
		listsib = Weather_Main.response.getResults().get(0).getIndex();
		todaycan_dec.setText(listsib.get(0).getDes());
		todayinfo_grid.setAdapter(new GridTodayCAdapter(getActivity(),
				listsib));
		
		todayinfo_grid.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {				
					todaycan_dec.setText(listsib.get(position).getDes());				
			}
		});
		
		
	}

	private void initview() {
		// TODO Auto-generated method stub
		todayinfo_grid = (GridView) homep_content
				.findViewById(R.id.gridview);
		todaycan_dec = (TextView) homep_content.findViewById(R.id.todaycan_dec);
	}

}
