package com.e_road.service;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

import com.e_road.utils.LoggerUtil;

/**
 * e-road主service(ERoadService)的守护进程
 * 
 * @author CaiMeng
 * 
 */
public class ProtectedService extends Service {
	private static String TAG = "ProtectedService";

	@Override
	public void onCreate() {
		LoggerUtil.d(TAG, "ProtectedService创建了");		/*
		 * 监听系统广播 ACTION_TIME_TICK这个广播每分钟发送一次
		 * 如果发现ERoadService被结束，就重新启动ERoadService
		 * 
		 * 做了此项检查，ERoadService可以设置为不开机自启动
		 */
		IntentFilter filter = new IntentFilter(Intent.ACTION_TIME_TICK);

		// 此项广播接收只能动态注册，静态注册(AndroidManifest)容易被发现
		ProtectedBroadcastReceiver receiver = new ProtectedBroadcastReceiver();

		registerReceiver(receiver, filter);

	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		
		LoggerUtil.d(TAG, "ProtectedService启动了");

		/**
		 * Service.START_STICKY:service重新启动时传入的intent为null，适合于前台service如音乐播放等 <br>
		 * Service.START_NOT_STICKY:当停止service后会在下一个调度中尝试重新启动，适合于网络轮查<br>
		 * Service.START_REDELIVER_INTENT:时效性比较重要，当存在未处理的启动调用时会尝试重新启动，
		 * 并传入未完成处理的Intent
		 */
		return Service.START_STICKY;
	}

	/**
	 * 守护进程的广播接收者<br>
	 * maybe：动态注册
	 * 
	 * @author CaiMeng
	 * 
	 */
	private class ProtectedBroadcastReceiver extends BroadcastReceiver {
		private boolean isServiceRunning = false;

		@Override
		public void onReceive(Context context, Intent intent) {
			//启动service后可以接收到ACTION_TIME_TICK广播
			if (intent.getAction().equals(Intent.ACTION_TIME_TICK)) {
				LoggerUtil.d(TAG, "接收到广播ACTION_TIME_TICK");
				// 检查service状态
				ActivityManager manager = (ActivityManager) context
						.getSystemService(Context.ACTIVITY_SERVICE);

				// 查询所有正在运行的服务
				for (RunningServiceInfo serviceInfo : manager
						.getRunningServices(Integer.MAX_VALUE)) {
					// 检测到指定的服务类名，isServiceRunning = true
					if ("com.e_road.service.ERoadService"
							.equals(serviceInfo.service.getClassName())) {
						isServiceRunning = true;
					}
				}

				// 没有检测到正在运行的service：ERoadService
				if (!isServiceRunning) {
					LoggerUtil.d(TAG, "ERoadService没有启动，重启中......");
					Intent intent_restart = new Intent(context,
							ERoadService.class);
					startService(intent_restart);
				}
				LoggerUtil.d(TAG, "ERoadService已启动，无需操作");
			}
		}

	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		LoggerUtil.d(TAG, "ProtectedService被销毁了");
		super.onDestroy();
	}
	
}
