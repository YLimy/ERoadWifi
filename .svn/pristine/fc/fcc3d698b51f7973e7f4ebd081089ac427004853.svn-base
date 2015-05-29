package com.e_road.ui;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.widget.ListView;

import com.e_road.R;
import com.e_road.adapter.DownLoadAdapter;
import com.e_road.service.DownLoadService;
import com.e_road.utils.IConstant;
import com.e_road.utils.LoggerUtil;
import com.e_road.vo.DownLoadInfo;

public class DownLoadListActivity extends Activity{
	private static final String TAG = "DownLoadListActivity";
	
	private ListView listView;
	private List<DownLoadInfo> downLoadInfos;
	private List<DownLoadInfo> infos;
	/** 下载服务 */
	private DownLoadService downLoadService;
	private DownloadListReceiver receiver;
	
	private DownLoadAdapter adapter;
	
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			LoggerUtil.d(TAG, "接收到msg");
			if(msg.what == 1){
				adapter.notifyDataSetChanged();
			}
		}
		
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.download_list_activity);
		listView = (ListView) findViewById(R.id.lv_downlaod_activity);
		
		//绑定服务
		Intent intent_sevice = new Intent(this,DownLoadService.class);
		bindService(intent_sevice, connection, BIND_AUTO_CREATE);
		registeReceiver();
		downLoadInfos = new ArrayList<DownLoadInfo>();
		adapter = new DownLoadAdapter(this, downLoadInfos);
		listView.setAdapter(adapter);
	}
	
	/**
	 * 注册接收器
	 */
	private void registeReceiver(){
		LoggerUtil.d(TAG, "注册receiver");
		IntentFilter filter = new IntentFilter();
		filter.addAction(IConstant.ACTION_DOWNLOAD_PROGRESS);
		receiver = new DownloadListReceiver();
		registerReceiver(receiver, filter);
	}

	@Override
	protected void onDestroy() {
		unregisterReceiver(receiver);
		if(null != connection){
			unbindService(connection);
		}
		connection = null;
		super.onDestroy();
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
	
	private class DownloadListReceiver extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			if(intent.getAction().equals(IConstant.ACTION_DOWNLOAD_PROGRESS)){
				Bundle bundle = intent.getExtras();
				if(null != bundle){
					LoggerUtil.d(TAG, "收到广播");
					infos = bundle.getParcelableArrayList("down_progress");
					for(int i=0; i<infos.size(); i++){
						if(downLoadInfos.size() <= i){
							downLoadInfos.add(infos.get(i));
						}else{
							downLoadInfos.get(i).setLength(infos.get(i).getLength());
						}
					}
					handler.sendEmptyMessage(1);
				}
			}
		}
		
	}
}
