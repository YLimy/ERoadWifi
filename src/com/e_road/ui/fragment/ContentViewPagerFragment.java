package com.e_road.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.e_road.R;
import com.e_road.adapter.ContentPagerAdapter;
import com.e_road.ui.DownloadManagerActivity;
import com.e_road.utils.LoggerUtil;

/**
 * 主界面中间滑动布局
 * 
 * @author CaiMeng
 * 
 */
public class ContentViewPagerFragment extends Fragment implements
		OnClickListener, OnTouchListener {

	// TODO ViewPager中有三个WebView，出现网络不可用时调用三次Toast

	private ViewPager viewPager;
	private FragmentManager fm;
	private TextView tv_home;
	private TextView tv_service;
	// private TextView tv_wifi;
	private TextView tv_apps;
	private TextView tv_mine;
	private ImageView tab_line;
	private ImageView iv_scroll;
	private int mScreen1_4;
	private int screenHeight;
	private int screenWidth;
	/** 滑动起始位置 */
	private int startX;
	private int startY;
	/** 滑动结束位置 */
	private int endX;
	private int endY;
	/** 记录上一次位置 */
	private int lastX;
	private int lastY;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.content_viewpager_fragment,
				container, false);
		fm = getActivity().getSupportFragmentManager();
		findViews(view);
		init();
		setListener();
		// viewPager.setCurrentItem(0);
		return view;
	}

	private void findViews(View view) {
		viewPager = (ViewPager) view
				.findViewById(R.id.vp_content_viewpager_frag_main);
		// 设置缓存的ViewPager的子页面数量(左/右)
		viewPager.setOffscreenPageLimit(3);
		viewPager.setAdapter(new ContentPagerAdapter(fm));

		tv_home = (TextView) view
				.findViewById(R.id.tv_content_viewpager_frag_buttom_home);
		tv_service = (TextView) view
				.findViewById(R.id.tv_content_viewpager_frag_buttom_service);
		// tv_wifi = (TextView) view
		// .findViewById(R.id.tv_content_viewpager_frag_buttom_wifi);
		tv_apps = (TextView) view
				.findViewById(R.id.tv_content_viewpager_frag_buttom_apps);
		tv_mine = (TextView) view
				.findViewById(R.id.tv_content_viewpager_frag_buttom_mine);

		tab_line = (ImageView) view
				.findViewById(R.id.iv_content_viewpager_frag_scroll_line);
		iv_scroll = (ImageView) view.findViewById(R.id.iv_content_vp_scroll);
	}

	private void init() {
		initTabLine();
	}

	/**
	 * 初始化动作跟随条
	 */
	private void initTabLine() {
		Display display = getActivity().getWindow().getWindowManager()
				.getDefaultDisplay();
		DisplayMetrics outMetrics = new DisplayMetrics();
		display.getMetrics(outMetrics);
		screenHeight = outMetrics.heightPixels;
		screenWidth = outMetrics.widthPixels;
		mScreen1_4 = outMetrics.widthPixels / 4;
		android.view.ViewGroup.LayoutParams layoutParams = tab_line
				.getLayoutParams();
		layoutParams.width = mScreen1_4;
		tab_line.setLayoutParams(layoutParams);
	}

	private void setListener() {
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				configureButtomColor(position);
			}

			@Override
			public void onPageScrolled(int position, float positionOffset,
					int px) {
				LayoutParams layoutParams = (LayoutParams) tab_line
						.getLayoutParams();
				layoutParams.leftMargin = (int) (position * mScreen1_4 + positionOffset
						* mScreen1_4);
				tab_line.setLayoutParams(layoutParams);
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
 
			}
		});
		tv_home.setOnClickListener(this);
		tv_service.setOnClickListener(this);
		// tv_wifi.setOnClickListener(this);
		tv_apps.setOnClickListener(this);
		tv_mine.setOnClickListener(this);
		iv_scroll.setOnTouchListener(this);
	}

	/**
	 * 查找ViewPager中的fragment
	 * 
	 * @param position
	 * @return
	 */
	private ViewPagerBaseFragment findFragmentById(int position) {
		ContentPagerAdapter adapter = (ContentPagerAdapter) viewPager
				.getAdapter();
		return (ViewPagerBaseFragment) adapter.getItem(position);
	}

	/**
	 * 注销时刷新的fragment
	 */
	public void logoutFragments() {
		ContentPagerAdapter adapter = (ContentPagerAdapter) viewPager
				.getAdapter();
		MineFragment mineFragment = (MineFragment) adapter.getItem(3);
		mineFragment.showViews();
	}

	/**
	 * 刷新当前页面
	 */
	public void refresh() {
		findFragmentById(viewPager.getCurrentItem()).onRefresh();
	}

	/**
	 * 收藏/获取当前页
	 * 
	 * @return 1、2、3页返回的是URL<br>
	 *         4页(用户界面)返回你null
	 */
	public String saveUrl() {
		return findFragmentById(viewPager.getCurrentItem()).onSaveUrl();
	}

	/**
	 * 导航条回退
	 */
	public void onBack() {
		findFragmentById(viewPager.getCurrentItem()).onBack();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		// 首页
		case R.id.tv_content_viewpager_frag_buttom_home:
			viewPager.setCurrentItem(0);
			break;
		// 服务
		case R.id.tv_content_viewpager_frag_buttom_service:
			viewPager.setCurrentItem(1);
			break;
		// WiFi
		// case R.id.tv_content_viewpager_frag_buttom_wifi:
		// viewPager.setCurrentItem(2);
		// break;
		// 应用
		case R.id.tv_content_viewpager_frag_buttom_apps:
			viewPager.setCurrentItem(2);
			break;
		// 我的
		case R.id.tv_content_viewpager_frag_buttom_mine:
			viewPager.setCurrentItem(3);
			break;
		}
	}

	/**
	 * 改变底部按键的颜色
	 */
	private void configureButtomColor(int position) {
		tv_home.setTextColor(getResources().getColor(R.color.black));
		tv_service.setTextColor(getResources().getColor(R.color.black));
		// tv_wifi.setTextColor(getResources().getColor(R.color.black));
		tv_apps.setTextColor(getResources().getColor(R.color.black));
		tv_mine.setTextColor(getResources().getColor(R.color.black));
		switch (position) {
		case 0:
			tv_home.setTextColor(getResources().getColor(R.color.light_green));
			break;
		case 1:
			tv_service.setTextColor(getResources()
					.getColor(R.color.light_green));
			break;
		// case 2:
		// tv_wifi.setTextColor(getResources().getColor(R.color.light_green));
		// break;
		case 2:
			tv_apps.setTextColor(getResources().getColor(R.color.light_green));
			break;
		case 3:
			tv_mine.setTextColor(getResources().getColor(R.color.light_green));
			break;

		}
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch (v.getId()) {
		case R.id.iv_content_vp_scroll:
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				startX = (int) event.getRawX();
				startY = (int) event.getRawY();
				lastX = startX;
				lastY = startY;
				break;

			case MotionEvent.ACTION_MOVE:
				int dx = (int) event.getRawX() - lastX;
				int dy = (int) event.getRawY() - lastY;

				int top = v.getTop() + dy;

				int left = v.getLeft() + dx;

				if (top <= 0) {
					top = 0;
				}
				if (top >= screenHeight - iv_scroll.getHeight()) {
					top = screenHeight - iv_scroll.getHeight();
				}
				if (left >= screenWidth - iv_scroll.getWidth()) {
					left = screenWidth - iv_scroll.getWidth();
				}

				if (left <= 0) {
					left = 0;
				}
				v.layout(left, top, left + iv_scroll.getWidth(), top
						+ iv_scroll.getHeight());
				lastX = (int) event.getRawX();
				lastY = (int) event.getRawY();
				break;
			case MotionEvent.ACTION_UP:
				endX = (int) event.getRawX();
				endY = (int) event.getRawY();
				//两点间的距离
				double distance = Math.sqrt(Math.abs(startX - endX)
						* Math.abs(startX - endX) + Math.abs(startY - endY)
						* Math.abs(startY - endY));
				if(distance < 5){
					LoggerUtil.d("contentViewPagger", "distance=" + distance);
					//距离较小，处理为点击
					getActivity().startActivity(
							new Intent(getActivity(), DownloadManagerActivity.class));
				}else{
					//距离较大，处理为滑动
					return true;
				}
				break;
			}
		}
		return false;
	}

}
