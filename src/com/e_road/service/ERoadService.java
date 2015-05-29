package com.e_road.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import com.e_road.utils.LoggerUtil;

/**
 * e-road服务
 * 
 * @author CaiMeng
 * 
 */
public class ERoadService extends Service {
	private static String TAG = "ERoadService";

	private final IBinder binder = new ERoadBinder();

	@Override
	public IBinder onBind(Intent intent) {
		
		return binder;
	}

	/**
	 * 实现service的绑定
	 * @author CaiMeng
	 *
	 */
	public class ERoadBinder extends Binder {
		public ERoadService getService() {
			return ERoadService.this;
		}
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		LoggerUtil.d(TAG, "ERoadService被创建了");
	}

	/**
	 * 类比onStart()在启动时调用
	 */
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		LoggerUtil.d(TAG, "ERoadService启动了");
		
		/*
		 * Service.START_STICKY:service重新启动时传入的intent为null，适合于前台service如音乐播放等 <br>
		 * Service.START_NOT_STICKY:当停止service后会在下一个调度中尝试重新启动，适合于网络轮查<br>
		 * Service.START_REDELIVER_INTENT:时效性比较重要，当存在未处理的启动调用时会尝试重新启动，
		 * 并传入未完成处理的Intent
		 */
		return Service.START_STICKY;
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
//		LoggerUtil.d(TAG, "ERoadService销毁了");
	}
	
}
