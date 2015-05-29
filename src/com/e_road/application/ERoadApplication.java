package com.e_road.application;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Application;
import android.os.Environment;
import cn.jpush.android.api.JPushInterface;

import com.e_road.service.DownLoadService;
import com.e_road.utils.LoggerUtil;

/**
 * application类 <br>
 * 定义全局变量<br>
 * 定义全局方法<br>
 * @author CaiMeng
 *
 */
public class ERoadApplication extends Application {

	/**
	 * 缓存的根目录<br>
	 * 包括音乐，视频等
	 */
	private static String cachePath;
	
	/**
	 * 已经开启的activity的集合
	 */
	private List<Activity> list_activity = new ArrayList<Activity>();
	
	private static ERoadApplication application;

	@Override
	public void onCreate() {
		super.onCreate();
		//TODO 初始化缓存目录，activity集合，服务绑定
		initCachPath();
		JPushInterface.setDebugMode(true);
		JPushInterface.init(this);
		application = this;
	}
	
	public static ERoadApplication getInstance(){
		return application;
	}
	
	/**
	 * 初始化缓存目录
	 */
	public File initCachPath(){
		File f;
		//如果外部存储存在并且具有读写权限
		if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
			f = new File(Environment.getExternalStorageDirectory() + "/.e_road/");
			//判断路径是否存在
			if(!f.exists()){
				f.mkdirs();
				LoggerUtil.d("Application", "创建了文件夹：" + f.toString());
			}
			LoggerUtil.d("Application", "文件夹已存在");
		}else{
			//如果不存在外部存储设备，则使用系统缓存路径
			f = getApplicationContext().getCacheDir();
		}
		cachePath = f.getAbsolutePath();
		return f;
	}
	
	/**
	 * 获取应用的缓存路径
	 * @return 缓存的绝对路径
	 */
	public static String getCachPath(){
		return cachePath;
	}
	
	/**
	 * 开启一个activity时应调用此方法将其记录
	 * @param activity
	 */
	public void addActivity(Activity activity){
		list_activity.add(activity);
		
	}
	
	/**
	 * 移除一个activity的记录
	 * @param activity
	 */
	public void removeActivity(Activity activity){
		list_activity.remove(activity);
	}
	
	/**
	 * 将所有已记录的activity释放<br>
	 * 在应用结束时应调用此方法
	 */
	public void exit(){
		for(Activity activity : list_activity){
			activity.finish();
		}
		System.exit(0);
	}
	
	/**
	 * 当前已记录的activity数
	 * @return
	 */
	public int currentActivitySize(){
		return list_activity.size();
	}

}
