package com.e_road.ui;

import com.e_road.ui.fragment.LoginFragment;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;

/**
 * 登录的activity
 * @author CaiMeng
 *
 */
public class LoginFragmentActivity extends BaseFragmentActivity {

	@Override
	protected void showViews(FragmentManager fragmentManager, int layoutID) {
		LoginFragment fragment = new LoginFragment();
		Bundle bundle = getIntent().getExtras();
		if(null != bundle)
			fragment.setArguments(bundle);
		fragmentManager.beginTransaction()
				.replace(layoutID, fragment).commit();
	}

	@Override
	protected void showAds(FragmentManager fragmentManager, int layoutID) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void logoutRefresh() {
		// TODO Auto-generated method stub
		
	}

}
