package com.e_road.test;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

import com.e_road.R;
import com.e_road.view.dialog.ButtomDialog;
import com.e_road.view.dialog.ButtomDialog.ButtonCallBack;

public class TestLayout extends FragmentActivity implements OnClickListener{
	private Button bt_dialog;
	private LinearLayout ll_text;
	private FragmentManager fm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_activity);
		findViews();
		setLinstener();
		fm = getSupportFragmentManager();
		init();
	}

	private void init() {
		fm.beginTransaction().replace(R.id.ll_test, new TestFragment()).commit();
		
		
	}

	private void findViews() {
		bt_dialog = (Button) findViewById(R.id.bt_test_01);
		ll_text = (LinearLayout) findViewById(R.id.ll_test);
	}

	private void setLinstener() {
		bt_dialog.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_test_01:
			ButtomDialog dialog = new ButtomDialog(this);
			
			dialog.addOptionButton("取消",new ButtonCallBack() {
				
				@Override
				public void callBack() {
					// TODO Auto-generated method stub
					
				}
			});
			dialog.show();
			break;
		}
	}

	
}
