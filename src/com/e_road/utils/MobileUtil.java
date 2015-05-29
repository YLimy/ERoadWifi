package com.e_road.utils;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;

/**
 * 获取手机信息
 * @author CaiMeng
 *
 */
public class MobileUtil {
	
	/**
	 * 获取手机当前操作系统版本
	 * @return
	 */
	public static String getPhoneVersion(){
		return android.os.Build.VERSION.SDK_INT + "";
	}
	
	/**
	 * 获取手机型号
	 */
	public static String getPhoneModel() {
		return android.os.Build.MODEL;
	}
	/**
	 * 获取手机号码
	 */
	public static String getPhoneNumber(Context context) {
		TelephonyManager telephoneManager = 
				(TelephonyManager) (context.getSystemService(Context.TELEPHONY_SERVICE));
		return telephoneManager.getLine1Number();
	}

	/**
	 * 获取手机IMEI
	 */
	public static String getPhoneIMEI(Context context) {
		/*
		 * IMEI(International Mobile EquipmentIdentity) 是国际移动设备身份码的缩写，国际移动装备辨识码，
		 * 是由15位数字组成的 "电子串号"，它与每台手机一一对应，而且该码是全世界唯一的。
		 * 每一只手机在组装完成后都将被赋予一个全球唯一的一组号码， 这个号码从生产到交付使用都将被制造生产的厂商所记录。
		 */
		TelephonyManager telephoneManager = 
				(TelephonyManager) (context.getSystemService(Context.TELEPHONY_SERVICE));
		return telephoneManager.getDeviceId();
	}
	
	/**
	 * 获取mac地址
	 * 
	 * @return [mac地址]：wifi连接正常时<br>
	 *         [nill]：没有wifi设备时(比如模拟器)
	 */
	public static String getPhoneMAC(Context context) {
		WifiManager wifiManager = (WifiManager) (context.getSystemService(Context.WIFI_SERVICE));
		WifiInfo wifiInfo = null;
		if (null != wifiManager)
			wifiInfo = wifiManager.getConnectionInfo();
		if (null != wifiInfo)
			return wifiInfo.getMacAddress();
		else
			return null;
	}
}
