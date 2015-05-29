package com.e_road.ui.fragment;

import java.util.ArrayList;
import java.util.List;

import com.e_road.R;
import com.e_road.adapter.DownLoadAdapter;
import com.e_road.service.DownLoadService;
import com.e_road.utils.IConstant;
import com.e_road.utils.LoggerUtil;
import com.e_road.vo.DownLoadInfo;

import android.annotation.SuppressLint;
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
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 正在下载的文件列表
 * @author CaiMeng
 *
 */
public class DownloadingFragment extends Fragment {
	private static final String TAG = "DownloadingFragment";
	
	private ListView listView;
	private TextView tv_msg;
	/** 页面显示下载信息源 */
	private List<DownLoadInfo> downLoadInfos;
	/** 从广播中获取的下载信息 */
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
				onResume();
			}
		}
		
	};
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.download_list_fragment, container, false);
		findViews(view);
		//绑定服务
		Intent intent_sevice = new Intent(getActivity(),DownLoadService.class);
		getActivity().bindService(intent_sevice, connection, Context.BIND_AUTO_CREATE);
		registReceiver();
		init();
		setLinstener();
		return view;
	}

	private void findViews(View view) {
		listView = (ListView) view.findViewById(R.id.lv_downlaod_fragment);
		tv_msg = (TextView) view.findViewById(R.id.tv_download_list_msg);
	}
	
	private void init() {
		downLoadInfos = new ArrayList<DownLoadInfo>();
		adapter = new DownLoadAdapter(getActivity(), downLoadInfos);
		listView.setAdapter(adapter);
	}
	
	@Override
	public void onResume() {
		super.onResume();
		if(downLoadInfos.size() > 0){
			listView.setVisibility(View.VISIBLE);
			tv_msg.setVisibility(View.GONE);
		}else{
			listView.setVisibility(View.GONE);
			tv_msg.setVisibility(View.VISIBLE);
		}
	}

	private void registReceiver() {
		LoggerUtil.d(TAG, "注册receiver");
		IntentFilter filter = new IntentFilter();
		filter.addAction(IConstant.ACTION_DOWNLOAD_PROGRESS);
		receiver = new DownloadListReceiver();
		getActivity().registerReceiver(receiver, filter);
	}

	private void setLinstener() {
		// TODO Auto-generated method stub
		
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

	@Override
	public void onDestroy() {
		getActivity().unregisterReceiver(receiver);
		if(null != connection){
			getActivity().unbindService(connection);
		}
		connection = null;
		super.onDestroy();
	}
}
