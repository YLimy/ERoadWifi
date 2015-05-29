package com.e_road.ui;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import android.app.ActionBar;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewConfiguration;
import android.view.Window;
import android.widget.Toast;
import cn.jpush.android.api.JPushInterface;

import com.e_road.R;
import com.e_road.application.ERoadApplication;
import com.e_road.ui.fragment.ContentViewPagerFragment;
import com.e_road.utils.CommonUtil;
import com.e_road.utils.IConstant;
import com.e_road.utils.Interactive;
import com.e_road.utils.Interactive.DataCallBack;
import com.e_road.utils.parser.LogoutParser;
import com.e_road.view.actionbar.MyActionBarSettings;
import com.e_road.vo.RequestVo;

/**
 * 基类的activity<br>
 * 管理actionBar功能
 * 
 * @author CaiMeng
 * 
 */
public abstract class BaseFragmentActivity extends FragmentActivity {

	private static ERoadApplication application;
	private Interactive interactive;

	/** FragmentManager */
	private FragmentManager fm;
	/** 替换的布局ID */
	private int layoutContentID = R.id.ll_base_frag_content;
	/** 广告 */
	private int layoutAdsID = R.id.ll_base_frag_ads;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		application = (ERoadApplication) getApplication();
		application.addActivity(this);

		super.setContentView(R.layout.base_fragment);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		ActionBar actionBar = getActionBar();
		// 添加图标导航
		actionBar.setHomeButtonEnabled(true);
		// 禁止物理menu
		setOverflowShowingAlways();
		// 启用anctionBar导航
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayUseLogoEnabled(false);
		fm = getSupportFragmentManager();
		interactive = new Interactive(this);

		showAds(fm, layoutAdsID);

		showViews(fm, layoutContentID);
	}

	/**
	 * 展示走马灯广告
	 * 
	 * @param fragmentManager
	 * @param layoutID
	 */
	protected abstract void showAds(FragmentManager fragmentManager,
			int layoutID);

	/**
	 * 展示的界面
	 * 
	 * @param fragmentManager
	 */
	protected abstract void showViews(FragmentManager fragmentManager,
			int layoutID);

	/**
	 * 创建菜单界面
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.menu.main, menu);
		// MenuItem menuItem = menu.findItem(R.id.action_search);
		// SearchView searchView = (SearchView) menuItem.getActionView();
		// // TODO 操作searchView
		// MenuItem addMenu = menu.findItem(R.id.action_add);
		// AddActionProvider addProvider = (AddActionProvider)
		// addMenu.getActionProvider();

		// 在适当情况下显示刷新图标
		MenuItem item_refresh = menu.findItem(R.id.action_overflow_refresh);
		MenuItem item_login = menu.findItem(R.id.action_overflow_login);
		MenuItem item_logout = menu.findItem(R.id.action_overflow_logout);
		ContentViewPagerFragment viewPagerFragment = (ContentViewPagerFragment) fm
				.findFragmentByTag("viewPager");
		// 是否显示刷新按钮
		if (null != viewPagerFragment) {
			// viewPager界面显示刷新
			item_refresh.setVisible(true);
		} else {
			// 其他界面隐藏刷新按钮
			item_refresh.setVisible(false);
		}
		// 显示登陆/注销按钮
		if (CommonUtil.isLogin(this)) {
			item_login.setVisible(false);
			item_logout.setVisible(true);
		} else {
			item_login.setVisible(true);
			item_logout.setVisible(false);
		}

		return super.onCreateOptionsMenu(menu);
	}

	/**
	 * 菜单点击事项
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {

		case android.R.id.home:
			// actionBar的home键
			MyActionBarSettings.homeBackUp(this);
			return true;
			// case R.id.action_add:
			//
			// // 添加
			// return true;
		case R.id.action_overflow_refresh:
			MyActionBarSettings.refresh(fm);
			return true;
		case R.id.action_overflow_settings:

			// 设置
			Intent intent = new Intent(
					android.provider.Settings.ACTION_WIRELESS_SETTINGS);
			startActivity(intent);
			return true;

		case R.id.action_overflow_login:

			// 登录
			startActivity(new Intent(this, LoginFragmentActivity.class));

			return true;

		case R.id.action_overflow_logout:
			// 注销
			baseLogout();// 没有刷新ViewPager
			return true;
		case R.id.action_overflow_wifi:

			// WiFi列表
			MyActionBarSettings.showWifiList(this);

			return true;
//		case R.id.action_overflow_download:
//			//下载列表
////			startActivity(new Intent(this, DownLoadListActivity.class));
//			startActivity(new Intent(this, DownloadManagerActivity.class));
//			return true;
		case R.id.action_overflow_exit:

			// 退出
			exist();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/**
	 * 注销
	 * 
	 */
	protected void baseLogout() {

		// 未登录用户不处理
		if (!CommonUtil.isLogin(this))
			return;

		// TODO 已登录界面信息的刷新

		RequestVo requestVo = new RequestVo(R.string.url_logout, this, null,
				new LogoutParser());
		interactive.getDataFromServer(requestVo, new DataCallBack<String>() {

			@Override
			public void progressData(String param) {
				// 清空SharedPreferences中的登录用户信息
				CommonUtil.saveSharedPreferences(BaseFragmentActivity.this,
						"sp", IConstant.LOGIN_USER_NAME, "");

				Toast.makeText(BaseFragmentActivity.this, "注销成功",
						Toast.LENGTH_SHORT).show();
				logoutRefresh();
				invalidateOptionsMenu();// 更新menu按键
			}

		});

	}

	/** 注销登陆时刷新页面 */
	protected abstract void logoutRefresh();

	/**
	 * 为了overflow显示一致<br>
	 * 当存在物理menu时<br>
	 * 禁用其对本程序的操作。
	 */
	private void setOverflowShowingAlways() {
		try {
			ViewConfiguration config = ViewConfiguration.get(this);
			Field menuKeyField = ViewConfiguration.class
					.getDeclaredField("sHasPermanentMenuKey");
			menuKeyField.setAccessible(true);
			menuKeyField.setBoolean(config, false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 重写menuOpen方法<br>
	 * 使menu中显示图片<br>
	 * 默认：<br>
	 * actionBar中不显示文本，overflow中不显示图片
	 */
	@Override
	public boolean onMenuOpened(int featureId, Menu menu) {
		if (featureId == Window.FEATURE_ACTION_BAR && menu != null) {
			if (menu.getClass().getSimpleName().equals("MenuBuilder")) {
				try {
					Method m = menu.getClass().getDeclaredMethod(
							"s" + "etOptionalIconsVisible", Boolean.TYPE);
					m.setAccessible(true);
					m.invoke(menu, true);
				} catch (Exception e) {
				}
			}
		}
		return super.onMenuOpened(featureId, menu);
	}

	/**
	 * 退出程序
	 */
	protected void exist() {
		application.exit();
	}

	@Override
	protected void onPause() {
		JPushInterface.onPause(this);
		super.onPause();
	}

	@Override
	protected void onResume() {
		// 重载menu界面
		invalidateOptionsMenu();
		JPushInterface.onResume(this);
		super.onResume();
	}

	@Override
	public File getCacheDir() {
		return application.initCachPath();
	}

	@Override
	protected void onDestroy() {
		fm = null;
		interactive = null;
		super.onDestroy();
	}
}
