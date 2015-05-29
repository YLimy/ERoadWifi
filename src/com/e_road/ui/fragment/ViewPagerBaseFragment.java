package com.e_road.ui.fragment;

import android.support.v4.app.Fragment;

/**
 * 主界面fragment基类
 * @author CaiMeng
 *
 */
public abstract class ViewPagerBaseFragment extends Fragment {
	/**
	 * 刷新页面
	 */
	public abstract void onRefresh();
	/**
	 * 点击导航回退时
	 */
	public abstract void onBack();
	/**
	 * 保存当前网址
	 * @return
	 */
	public abstract String onSaveUrl();
}
