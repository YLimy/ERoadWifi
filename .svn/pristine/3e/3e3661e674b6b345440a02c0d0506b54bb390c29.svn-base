package com.e_road.service;

import com.e_road.utils.LoggerUtil;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * 开机启动的广播接收者
 * @author CaiMeng
 *
 */
public class BootBroadCastReceiver extends BroadcastReceiver {
	//TODO 无法接收到BOOT_COMPLETED or 第一次安装接收不到 or 没有启动应用接收不到
	private static String TAG = "BootBroadCastReceiver";

	@Override
	public void onReceive(Context context, Intent intent) {
		//开机启动服务
		if(intent.getAction().equals("android.intent.action.BOOT_COMPLETED")){
			LoggerUtil.d(TAG, "接收到广播了");
			Intent intent_boot_service = new Intent(Intent.ACTION_RUN);
			intent_boot_service.setClass(context, ERoadService.class);
			context.startService(intent_boot_service);
		}
	}

}
