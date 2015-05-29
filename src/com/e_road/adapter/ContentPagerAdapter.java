package com.e_road.adapter;

import com.e_road.ui.fragment.AppsFragment;
import com.e_road.ui.fragment.HomeFragment;
import com.e_road.ui.fragment.LocalServiceFragment;
import com.e_road.ui.fragment.MineFragment;
import com.e_road.ui.fragment.WifiFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

/**
 * 主界面ViewPage的适配器
 * @author CaiMeng
 *
 */
public class ContentPagerAdapter extends FragmentPagerAdapter {
	
	/** fragment容器 */
	private Fragment[] fragments;

	public ContentPagerAdapter(FragmentManager fm) {
		super(fm);
		fragments = new Fragment[4];
		//TODO 将fragment放入fragment容器
		fragments[0] = new HomeFragment();
		fragments[1] = new LocalServiceFragment();
//		fragments[2] = new WifiFragment();
		fragments[2] = new AppsFragment();
		fragments[3] = new MineFragment();
	}

	@Override
	public Fragment getItem(int position) {
		return fragments[position];
	}

	@Override
	public int getCount() {
		return fragments.length;
	}

	@Override
	public void setPrimaryItem(ViewGroup container, int position, Object object) {
		// TODO Auto-generated method stub
		super.setPrimaryItem(container, position, object);
	}
	
}
