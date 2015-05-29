package com.e_road.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.KeyEvent;
import cn.jpush.android.api.JPushInterface;

import com.e_road.R;
import com.e_road.application.ERoadApplication;
import com.e_road.ui.fragment.AdsFragment;
import com.e_road.utils.LoggerUtil;

public class ShowFragmentActivity extends FragmentActivity {
	private ERoadApplication application;
	private FragmentManager fm;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		application = (ERoadApplication) getApplication();
		application.addActivity(this);
		setContentView(R.layout.show_fragment_activity);
		fm = getSupportFragmentManager();
		AdsFragment adsFragment = new AdsFragment();
		Bundle bundle = getIntent().getExtras();
		adsFragment.setArguments(bundle);
		fm.beginTransaction().replace(R.id.fl_show_frag_activity, adsFragment)
				.commit();
//		test();
	}

	public void test() {
		Bundle bundle = getIntent().getExtras();
		String title = bundle
				.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
		String content = bundle.getString(JPushInterface.EXTRA_ALERT);
		String type = bundle.getString(JPushInterface.EXTRA_EXTRA);// JSON
		int notificationId = bundle
				.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
		String file = bundle.getString(JPushInterface.EXTRA_MSG_ID);// 用于统计
		// Toast.makeText(this, type, Toast.LENGTH_LONG).show();
		LoggerUtil.d("ShowFragmentActivity", "title===>" + title);
		LoggerUtil.d("ShowFragmentActivity", "content===>" + content);
		LoggerUtil.d("ShowFragmentActivity", "type===>" + type);
		LoggerUtil.d("ShowFragmentActivity", "notificationId===>"
				+ notificationId);
		LoggerUtil.d("ShowFragmentActivity", "file===>" + file);

	}

	@Override
	protected void onPause() {
		JPushInterface.onPause(this);
		super.onPause();
	}

	@Override
	protected void onResume() {
		JPushInterface.onResume(this);
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

}
