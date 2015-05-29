package com.e_road.adapter;

import java.util.List;

import com.e_road.R;
import com.e_road.vo.DownLoadFinishedFiles;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 已下载完成文件的适配器
 * @author CaiMeng
 *
 */
public class DownLoadFinishedAdapter extends BaseAdapter {
	private List<DownLoadFinishedFiles> data;
	private LayoutInflater inflater;
	
	public DownLoadFinishedAdapter(Context context, List<DownLoadFinishedFiles> list){
		this.data = list;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		if(position < getCount()){
			return data.get(position);
		}
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressWarnings("deprecation")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view;
		Holder holder;
		if(convertView != null){
			view = convertView;
			holder = (Holder) convertView.getTag();
		}else{
			view = inflater.inflate(R.layout.item_download_finished, null);
			holder = new Holder();
			holder.icon = (ImageView) view.findViewById(R.id.iv_item_download_finish_icon);
			holder.title = (TextView) view.findViewById(R.id.tv_item_download_finished_title);
			holder.msg = (TextView) view.findViewById(R.id.tv_item_download_finished_msg);
			view.setTag(holder);
		}
		
		DownLoadFinishedFiles downFile = data.get(position);
		if(downFile != null){
			holder.icon.setBackgroundDrawable(downFile.getIcon());
			holder.title.setText(downFile.getFileName());
			holder.msg.setText(downFile.getFileLength()+"M");
		}
		
		return view;
	}
	
	private class Holder{
		ImageView icon;
		TextView title;
		TextView msg;
	}

}
