package com.e_road.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.e_road.R;

public class HomeContentActionView extends LinearLayout {
	
	private ImageView iv;
	private TextView tv;

	public HomeContentActionView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public HomeContentActionView(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.view_home_content_action, this);
		iv = (ImageView)findViewById(R.id.iv_view_home_content_action);
		tv = (TextView)findViewById(R.id.tv_view_home_content_action);
	}
	
	public void setImageResources(int resId){
		//TODO 获取图片，统一样式取
		iv.setImageResource(resId);
	}

	public void setTextViewText(String text){
		tv.setText(text);
	}
}
