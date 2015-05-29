package com.e_road.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.e_road.R;
import com.e_road.utils.IConstant;

/**
 * WiFi网络列表的适配器
 * 
 * @author CaiMeng
 * 
 */
public class WifiAdapter extends BaseAdapter {
	private Context context;
	private List<ScanResult> list;
	private LayoutInflater inflater;

	public WifiAdapter(Context context, List<ScanResult> list) {
		this.context = context;
		inflater = LayoutInflater.from(context);
		this.list = prepareList(list);
	}

	/**
	 * 将指定SSID对象置顶
	 * 
	 * @param list
	 * @return
	 */
	private List<ScanResult> prepareList(List<ScanResult> list) {
		List<ScanResult> mList = new ArrayList<ScanResult>();
		if (null != list && !list.isEmpty()) {
			// 不重复的SSID标识
			boolean flag = true;
			for (ScanResult scanResult : list) {
				// 查找到指定SSID,添加
				if (IConstant.SSID.equals(scanResult.SSID)) {
					if(flag){
						mList.add(0, scanResult);
						// 重复的SSID将不添加
						flag = false;
					}
					continue;
				}
				mList.add(scanResult);
			}
		}
		return mList;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = null;
		Holder holder = null;

		if (null != convertView) {
			view = convertView;
			holder = (Holder) convertView.getTag();
		} else {
			view = inflater.inflate(R.layout.view_item_wifi, null);
			holder = new Holder();
			holder.tv_ssid = (TextView) view
					.findViewById(R.id.tv_item_wifi_ssid);
			holder.tv_capability = (TextView) view
					.findViewById(R.id.tv_item_wifi_capability);
			view.setTag(holder);
		}

		int local = position - list.size();
		if (local < 0) {

			ScanResult result = list.get(position);
			String ssid = result.SSID;
			String capability = result.capabilities;
			if (IConstant.SSID.equals(ssid)) {
				holder.tv_ssid.setText(ssid + "免费");
				holder.tv_ssid.setTextColor(context.getResources().getColor(
						R.color.green));
			} else {
				holder.tv_ssid.setText(ssid);
				holder.tv_ssid.setTextColor(context.getResources().getColor(
						R.color.black));
			}

			if (null != capability && capability.length() > 0) {
				// 如果加密，显示加密方式
				holder.tv_capability.setText("通过" + capability + "加密");
			} else {
				// 如果未加密，隐藏加密方式文本
				holder.tv_capability.setVisibility(View.GONE);
			}
		} else {

		}
		return view;
	}

	private class Holder {
		TextView tv_ssid;
		TextView tv_capability;
	}

}
