package com.example.weixing;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.example.weixing.view.ArcMenu;
import com.example.weixing.view.ArcMenu.onMenuItemClickListener;

public class MainActivity extends Activity {
	ArcMenu arcMenu;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//arcMenu = new ArcMenu(this);
		arcMenu = (ArcMenu) findViewById(R.id.id_menu);
		arcMenu.setOnMenuItemClickListener(new onMenuItemClickListener() {

			@Override
			public void onclick(View view, int position) {
				// TODO Auto-generated method stub
				Toast.makeText(MainActivity.this,
						position + ":" + view.getTag(), Toast.LENGTH_SHORT)
						.show();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
