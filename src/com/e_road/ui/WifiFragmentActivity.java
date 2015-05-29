package com.e_road.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.FragmentManager;

import com.e_road.ui.fragment.WifiClosedFragment;
import com.e_road.ui.fragment.WifiClosedFragment.WifiStateCallBack;
import com.e_road.ui.fragment.WifiListFragment;
import com.e_road.utils.IConstant;

public class WifiFragmentActivity extends BaseFragmentActivity implements
		WifiStateCallBack {

	private FragmentManager fm;
	private int layoutID;
	/** 页面跳转的倒计时（单位：毫秒） */
	private int millisInFuture = 5000;
	/** 页面跳转提示框 */
	private AlertDialog alertDialog;

	@Override
	protected void showAds(FragmentManager fragmentManager, int layoutID) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void showViews(FragmentManager fragmentManager, int layoutID) {
		this.fm = fragmentManager;
		this.layoutID = layoutID;
		if(isWifiOpen()){
			callBack();
		}else{
			WifiClosedFragment fragment = new WifiClosedFragment();
			Bundle bundle = getIntent().getExtras();
			if (null != bundle)
				fragment.setArguments(bundle);
			fm.beginTransaction().replace(layoutID, fragment).commit();
		}
	}

	/**
	 * 检查WiFi是否开启
	 * @return
	 */
	private boolean isWifiOpen() {
		WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
		return wifiManager.isWifiEnabled();
	}

	@Override
	public void callBack() {
		WifiListFragment listFragment = new WifiListFragment();
		Bundle bundle = getIntent().getExtras();
		if (null != bundle)
			listFragment.setArguments(bundle);
		fm.beginTransaction().replace(layoutID, listFragment).commit();
	}

	/**
	 * 页面跳转
	 */
	public void jump(boolean args){
		if(isJump()){
			if(args){
				prepareJump();
			}else{
				gotoNextPage();
			}
		}
	}
	
	/**
	 * 根据intent中的isJump确定是否进行页面跳转
	 * @return
	 */
	private boolean isJump(){
		Bundle bundle = getIntent().getExtras();
		if (null != bundle) {
			return bundle.getBoolean("isJump");
		}
		return false;
	}
	
	/**
	 * 跳转前的对话框及倒计时的初始化
	 */
	private void prepareJump(){
		createAlertDialog();
		alertDialog.show();
		new CountDownTimer(millisInFuture,1000) {
			
			@Override
			public void onTick(long millisUntilFinished) {
				int countDown = (int) ((millisUntilFinished + 500)/1000);
				alertDialog.setMessage("即将在" + countDown + "秒后跳转到首页");
			}
			
			@Override
			public void onFinish() {
				gotoNextPage();
			}
		}.start();
	}
	/**
	 * 转到下一个界面
	 */
	private void gotoNextPage(){
		dismissDialog();
		Intent intent = new Intent(WifiFragmentActivity.this,MainFragmentActivity.class);
		Bundle bundle = new Bundle();
		bundle.putString(IConstant.CONTENT_SHOW, "home");
		intent.putExtras(bundle);
		startActivity(intent);
		finish();
	}
	
	/**
	 * 创建跳转对话框
	 */
	private void createAlertDialog(){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("准备跳转");
		alertDialog = builder.create();
	}
	
	/**
	 * 取消对话框
	 */
	private void dismissDialog(){
		if(null != alertDialog && alertDialog.isShowing()){
			alertDialog.dismiss();
		}
	}
	
	@Override
	protected void onDestroy() {
		dismissDialog();
		super.onDestroy();
	}

	@Override
	protected void logoutRefresh() {
		// TODO Auto-generated method stub
		
	}
}
