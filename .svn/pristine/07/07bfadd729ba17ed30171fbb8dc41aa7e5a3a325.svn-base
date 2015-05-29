package com.e_road.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Parcelable;
import android.text.TextUtils;
import android.widget.Toast;

import com.e_road.application.ERoadApplication;
import com.e_road.download.DownLoadTask;
import com.e_road.download.DownLoadTask.DownlaodListener;
import com.e_road.thread.ThreadPoolManager;
import com.e_road.utils.FileUtils;
import com.e_road.utils.IConstant;
import com.e_road.utils.LoggerUtil;
import com.e_road.vo.DownLoadInfo;

public class DownLoadService extends Service implements DownlaodListener {
	private static final String TAG = "DownLoadService";
	private Map<String,DownLoadInfo> map;
	private DownLoadTask downLoadTask;
	private DownLoadReceiver receiver;
	private boolean ifBroad = true;
	private List<DownLoadInfo> downLoadInfos;

	private final IBinder serviceBinder = new DownLoadServiceBinder();

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return serviceBinder;
	}

	@Override
	public void onCreate() {
		map = new HashMap<String,DownLoadInfo>();
		downLoadInfos = new ArrayList<DownLoadInfo>();
		registeReceiver();
	}

	private void registeReceiver() {
		if(null == receiver){
			IntentFilter filter = new IntentFilter();
			filter.addAction(IConstant.ACTION_DOWNLOAD_URL);
			receiver = new DownLoadReceiver();
			registerReceiver(receiver, filter);
		}
	}

	/**
	 * 发送当前下载量的广播
	 */
	private void thread4SendBroadcast() {
		new Thread(new Runnable() {
			
			/* (non-Javadoc)
			 * @see java.lang.Runnable#run()
			 */
			@Override
			public void run() {
				while(ifBroad){
					Intent intent = new Intent();
					intent.setAction(IConstant.ACTION_DOWNLOAD_PROGRESS);
					intent.putParcelableArrayListExtra("down_progress", (ArrayList<? extends Parcelable>) broadData());
					LoggerUtil.d(TAG, "发送广播");
					sendBroadcast(intent);
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			}
		}).start();
	}
	
	/**
	 * 将map转成list
	 * @return
	 */
	private List<DownLoadInfo> broadData(){
		downLoadInfos.clear();
		ifBroad = false;
		Collection<DownLoadInfo> collection = map.values();
		Iterator<DownLoadInfo> iterator = collection.iterator();
		while(iterator.hasNext()){
			DownLoadInfo info = iterator.next();
			if(info.isDownload()){
				ifBroad = true;
			}
			downLoadInfos.add(info);
		}
		return downLoadInfos;
	}

	/**
	 * 添加一个下载进程
	 * @param url
	 */
	public void addDownLoad(String url){
		if(null == url){
			return;
		}
		LoggerUtil.d(TAG, "下载地址为：" + url);
		String fileName = url.substring(url.lastIndexOf("/") + 1, url.length());
		if(TextUtils.isEmpty(fileName)){
			return;
		}
		//对下载的文件进行重命名(添加缓存TEMP后缀)
		File file = new File(ERoadApplication.getCachPath(),
				fileName + ".temp");
		downLoadTask = new DownLoadTask(url, file.getAbsolutePath(), 5);
		downLoadTask.setListener(this);
		DownLoadInfo downLoadInfo = new DownLoadInfo();
		downLoadInfo.setFileName(fileName);
		downLoadInfo.setUrl(url);
		downLoadInfo.setLength(0);
		downLoadInfo.setDownload(true);
		map.put(url, downLoadInfo);
		ThreadPoolManager.getInstatnce().addTask(downLoadTask);
		ifBroad = true;
	}
	
	/**
	 * 获取下载队列
	 * @return
	 */
	public Map<String,DownLoadInfo> getDownLoadMap(){
		return this.map;
	}

	/**
	 * 绑定service
	 * 
	 * @author CaiMeng
	 * 
	 */
	public class DownLoadServiceBinder extends Binder {
		public DownLoadService getService() {
			return DownLoadService.this;
		}
	}

	@Override
	public void update(int total, int len, int threadid, String url) {
//		LoggerUtil.d(TAG, len + ":" + total + "" + threadid);
		DownLoadInfo downLoadInfo = map.get(url);//只从map中读取对象，不用考虑线程安全问题
		if(null != downLoadInfo){
			downLoadInfo.setLength(downLoadInfo.getLength()+len);
			downLoadInfo.setTotal(total);
		}

	}

	@Override
	public void downLoadFinish(int totalSucess,String url) {
		LoggerUtil.d(TAG, "downLoadFinish==>" + url);
		DownLoadInfo downLoadInfo = map.get(url);
		downLoadInfo.setDownload(false);
		
//		Toast.makeText(this, "下载完成", Toast.LENGTH_SHORT).show();
		
		Handler handler = new Handler(Looper.getMainLooper());
		handler.post(new Runnable() {
			
			@Override
			public void run() {
				Toast.makeText(getApplicationContext(), "下载完成", Toast.LENGTH_SHORT).show();
			}
		});
		
		onDownloadFinish(url);
	}

	/**
	 * 完成下载<br>
	 * 1.检测下载完整性
	 * 2.重命名下载文件(去掉TEMP后缀)
	 * @param url
	 */
	private void onDownloadFinish(String url) {
		//TODO 检验MD5值
		String fileName = url.substring(url.lastIndexOf("/") + 1, url.length());
		LoggerUtil.d(TAG, "onFinish file name=>" + fileName);
		if(TextUtils.isEmpty(fileName)){
			return;
		}
		File file = new File(ERoadApplication.getCachPath(),
				fileName + ".temp");
		File descFile = new File(ERoadApplication.getCachPath(),
				fileName);
		file.renameTo(descFile);
		
		saveDownloadFinished(descFile);
		
		//下载完成后自动安装
		Intent intent = FileUtils.getFileIntent(descFile);
		startActivity(intent);
	}

	@Override
	public void downLoadError(int type,String url) {
		// TODO Auto-generated method stub
		LoggerUtil.d(TAG, "downLoadError==>" + url);
	}
	
	/**
	 * 保存已经下载的文件信息
	 * @param file
	 */
	private void saveDownloadFinished(File file){
		SharedPreferences sp = getSharedPreferences("sp", MODE_PRIVATE);
		Editor editor = sp.edit();
		Set<String> values = sp.getStringSet(IConstant.SP_DOWNLOAD, new HashSet<String>());
		//将文件路径保存
		values.add(file.getAbsolutePath());
		editor.putStringSet(IConstant.SP_DOWNLOAD, values);
		editor.commit();
	}
	
	
	@Override
	public void onDestroy() {
		unregisterReceiver(receiver);
		super.onDestroy();
	}

	public class DownLoadReceiver extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			if(intent.getAction().equals(IConstant.ACTION_DOWNLOAD_URL)){
				Bundle bundle = intent.getExtras();
				if(null != bundle){
					String url = bundle.getString("url");
					addDownLoad(url);
					thread4SendBroadcast();
				}
				
			}
			
		}
		
	}
}
