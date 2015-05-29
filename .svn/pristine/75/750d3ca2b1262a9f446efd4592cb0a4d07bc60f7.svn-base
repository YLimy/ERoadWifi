package com.e_road.utils;

import java.util.ArrayList;
import java.util.List;

import com.e_road.vo.AppInfo;

import android.R.drawable;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * 应用相关
 * @author CaiMeng
 *
 */
public class AppUtil {

	/**
	 * 查看是否安装了应用
	 * @param context 上下文
	 * @param PackageName 应用的包名
	 * @return
	 */
	public static  boolean isInstalled(Context context,String packageName){
		if(null == packageName || packageName.equals("")){
			return false;
		}
		PackageManager pm = context.getPackageManager();
		List<PackageInfo> list = pm.getInstalledPackages(0);
		for(PackageInfo info : list){
			if(packageName.equals(info.packageName)){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 收集已安装的应用信息列表
	 * @param context 上下文
	 * @return 已安装的非系统信息列表
	 */
	public static List<AppInfo> showInstalledApp(Context context){
		List<AppInfo> list_app = new ArrayList<AppInfo>();
		
		PackageManager pm = context.getPackageManager();
		List<PackageInfo> list_info = pm.getInstalledPackages(0);
		AppInfo appInfo = null;
		for(PackageInfo info : list_info){
			
			if((info.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0){
				//non-system app
				appInfo = new AppInfo();
				appInfo.setAppName(info.applicationInfo.loadLabel(pm).toString());
				appInfo.setVersionCode(info.versionCode);
				appInfo.setPackageName(info.packageName);
				appInfo.setAppIcon(info.applicationInfo.loadIcon(pm));
				
				list_app.add(appInfo);
			}
			
		}
		return list_app;
	}
}
