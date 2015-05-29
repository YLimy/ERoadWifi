package com.e_road.ui;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.KeyEvent;
import android.widget.Toast;

import com.e_road.download.DownLoadTask.DownlaodListener;
import com.e_road.service.DownLoadService;
import com.e_road.ui.fragment.AdsFragment;
import com.e_road.ui.fragment.ContentViewPagerFragment;
import com.e_road.ui.fragment.FragmentCallBack;
import com.e_road.ui.fragment.WifiFragment;
import com.e_road.utils.IConstant;
import com.e_road.utils.LoggerUtil;

/**
 * 主界面
 * 
 * @author CaiMeng
 * 
 */
public class MainFragmentActivity extends BaseFragmentActivity implements
		FragmentCallBack, DownlaodListener {
	private static final String TAG = "MainFragmentActivity";
	/** FragmentManager */
	private FragmentManager fm;
	private int layoutID;
	private static final int DOWN_ERROR = 0;
	private static final int SHOW_DIALOG = 1;
	/** 下载服务 */
	private DownLoadService downLoadService;

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case DOWN_ERROR:
				Toast.makeText(MainFragmentActivity.this, "下载失败",
						Toast.LENGTH_SHORT).show();
				break;
			case SHOW_DIALOG:
				
				break;
			}
		}

	};

	@Override
	protected void showViews(FragmentManager fragmentManager, int layoutID) {
		LoggerUtil.d(TAG, "showViews");
		startService();
		this.fm = fragmentManager;
		this.layoutID = layoutID;
		Bundle bundle = getIntent().getExtras();
		if (null != bundle) {
			String showWhat = bundle.getString(IConstant.CONTENT_SHOW);
			if ("home".equals(showWhat)) {
				LoggerUtil.d(TAG, "跳转到home");
				gotoHome(bundle);
			} else if ("ads".equals(showWhat)) {
				LoggerUtil.d(TAG, "跳转到ads");
				gotoAds(bundle);
			} else if ("wifi".equals(showWhat)) {
				LoggerUtil.d(TAG, "跳转到wifi");
				gotoWifi(bundle);
			} else {
				LoggerUtil.d(TAG, "跳转到home(else,NO MATCH)");
				gotoHome(bundle);
			}
		} else {
			LoggerUtil.d(TAG, "跳转到wifi(else,bundle NONE)");
			gotoHome(bundle);
		}
	}

	/**
	 * 显示WiFi页
	 */
	private void gotoWifi(Bundle bundle) {
		WifiFragment wifiFragment = new WifiFragment();
		wifiFragment.setArguments(bundle);
		fm.beginTransaction().replace(layoutID, wifiFragment).commit();
		// wifiToHome();
	}

	/**
	 * 显示主页
	 */
	public void gotoHome(Bundle bundle) {
		ContentViewPagerFragment viewPagerFragment = new ContentViewPagerFragment();
		viewPagerFragment.setArguments(bundle);
		fm.beginTransaction().replace(layoutID, viewPagerFragment, "viewPager")
				.commit();
	}

	/**
	 * 显示广告
	 */
	private void gotoAds(Bundle bundle) {
		AdsFragment adsFragment = new AdsFragment();
		adsFragment.setArguments(bundle);
		// fm.beginTransaction().show(adsFragment).commit();
		fm.beginTransaction().replace(layoutID, adsFragment).commit();
	}

	/**
	 * WiFi跳转到Home
	 */
	@Override
	public void wifiToHome(Bundle bundle) {
		gotoHome(bundle);
	}

	@Override
	protected void showAds(FragmentManager fragmentManager, int layoutID) {
		// fragmentManager.beginTransaction()
		// .replace(layoutID, new AdsMarqueeFragment()).commit();
	}

	@Override
	protected void onResume() {
		super.onResume();
		LoggerUtil.d("MainFragmentActivity", "onResume");
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			// LoggerUtil.d(TAG, "backStackCount=" +
			// fm.getBackStackEntryCount());
			Fragment fragment = fm.findFragmentByTag("viewPager");
			if (fragment instanceof ContentViewPagerFragment) {
				click2Exist(); // 调用双击退出函数
				return true;
			}
		}
		return super.onKeyDown(keyCode, event);
	}
	
	@Override
	public void onBackPressed() {
		//在系统调用onKeyDown->onKeyUp完成一次按键之后调用
		Fragment fragment = fm.findFragmentByTag("viewPager");
		if (fragment instanceof ContentViewPagerFragment) {
			((ContentViewPagerFragment) fragment).onBack();
		}
	}



	/** 记录按下回退键的时间 */
	private long exitTime = 0;

	/**
	 * 双击退出函数
	 */
	private void click2Exist() {

		if ((System.currentTimeMillis() - exitTime) > 2000) {
			Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
			exitTime = System.currentTimeMillis();
		} else {
			super.exist();
		}

	}

	@Override
	protected void logoutRefresh() {
		ContentViewPagerFragment contentViewPagerFragment = (ContentViewPagerFragment) fm
				.findFragmentByTag("viewPager");
		contentViewPagerFragment.logoutFragments();
	}

	@Override
	protected void onDestroy() {
		fm = null;
		finish();
		unbindService(connection);
		super.onDestroy();
	}

	@Override
	public void update(int total, int len, int threadid,String url) {
		// TODO Auto-generated method stub

	}

	@Override
	public void downLoadFinish(int totalSucess,String url) {
		if (totalSucess == 5) {
			//TODO
		} else {
			Message.obtain(handler, DOWN_ERROR).sendToTarget();
		}
	}

	@Override
	public void downLoadError(int type,String url) {
		handler.sendEmptyMessage(DOWN_ERROR);
	}
	
	/**
	 * 启动服务
	 */
	private void startService() {
		LoggerUtil.d(TAG, "启动服务i");
		Intent intent = new Intent(this, DownLoadService.class);
		startService(intent);
		bindService(intent, connection, Context.BIND_AUTO_CREATE);
		
	}
	
	/**
	 * 下载服务连接
	 */
	private ServiceConnection connection = new ServiceConnection() {
		
		@Override
		public void onServiceDisconnected(ComponentName name) {
			downLoadService = null;
			LoggerUtil.d(TAG, "下载服务绑定失败");
		}
		
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			downLoadService = ((DownLoadService.DownLoadServiceBinder)service).getService();
			if(null != downLoadService){
				LoggerUtil.d(TAG, "服务绑定成功");
			}else{
				LoggerUtil.d(TAG, "服务绑定失败");
			}
		}
	};
}
