package com.e_road.adapter;

import java.util.List;

import com.e_road.R;
import com.e_road.vo.DownLoadInfo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * 下载页的适配器
 * @author CaiMeng
 *
 */
public class DownLoadAdapter extends BaseAdapter {
	private LayoutInflater inflater;
	private List<DownLoadInfo> downLoadInfos;
	
	public DownLoadAdapter(Context context, List<DownLoadInfo> downLoadInfos){
		inflater = LayoutInflater.from(context);
		this.downLoadInfos = downLoadInfos;
	}

	@Override
	public int getCount() {
		return downLoadInfos.size();
	}

	@Override
	public Object getItem(int position) {
		if(position < getCount()){
			return downLoadInfos.get(position);
		}
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view;
		Holder holder;
		if(null != convertView){
			view = convertView;
			holder = (Holder) view.getTag();
		}else{
			view = inflater.inflate(R.layout.item_download_info, null);
			holder = new Holder();
			holder.icon = (ImageView) view.findViewById(R.id.iv_item_down_icon);
			holder.fileName = (TextView) view.findViewById(R.id.tv_item_down_name);
			holder.progressBar = (ProgressBar) view.findViewById(R.id.pb__item_down);
			view.setTag(holder);
		}
		DownLoadInfo downLoadInfo = downLoadInfos.get(position);
		if(null != downLoadInfo){
			holder.fileName.setText(downLoadInfo.getFileName());
			holder.progressBar.setMax(downLoadInfo.getTotal());
			holder.progressBar.setProgress(downLoadInfo.getLength());
		}
		return view;
	}
	
	class Holder{
		ImageView icon;
		TextView fileName;
		ProgressBar progressBar;
	}

}
