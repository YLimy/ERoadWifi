package com.e_road.ui.fragment;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.e_road.R;
import com.e_road.adapter.DownLoadFinishedAdapter;
import com.e_road.utils.DownLoadApkFileInfo;
import com.e_road.utils.DownLoadFileInfo;
import com.e_road.utils.IConstant;
import com.e_road.vo.DownLoadFinishedFiles;

/**
 * 已完成的下载文件列表
 * 
 * @author CaiMeng
 * 
 */
public class DownloadFinishedFragment extends Fragment {
	private ListView listView;
	private TextView tv_msg;
	private List<DownLoadFinishedFiles> list_files;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.download_finished_fragment,
				container, false);
		findviews(view);
		list_files = new ArrayList<DownLoadFinishedFiles>();
		setLinstener();
		init();
		DownLoadFinishedAdapter adapter = new DownLoadFinishedAdapter(getActivity(), list_files);
		listView.setAdapter(adapter);
		return view;
	}

	private void setLinstener() {
		// TODO Auto-generated method stub
		
	}

	private void findviews(View view) {
		listView = (ListView) view.findViewById(R.id.lv_downlaod_finished_fragment);
		tv_msg = (TextView) view.findViewById(R.id.tv_download_finish_msg);
	}
	
	@Override
	public void onResume() {
		super.onResume();
		//如果数据列表为空，则显示文本信息
		if(list_files.size() > 0){
			listView.setVisibility(View.VISIBLE);
			tv_msg.setVisibility(View.GONE);
		}else{
			listView.setVisibility(View.GONE);
			tv_msg.setVisibility(View.VISIBLE);
		}
	}
	
	/**
	 *  加载数据
	 */
	private void init(){
		SharedPreferences sp = getActivity().getSharedPreferences("sp", Context.MODE_PRIVATE);
		Set<String> set = sp.getStringSet(IConstant.SP_DOWNLOAD, new HashSet<String>());
		DownLoadFinishedFiles loadFinishedFiles;
		for(String str : set){
			//获取到文件路径
			//下载的APK文件
			loadFinishedFiles = DownLoadApkFileInfo.getFileInfo(getActivity(), str);
			if(loadFinishedFiles != null){
				list_files.add(loadFinishedFiles);
			}
		}
	}

}
