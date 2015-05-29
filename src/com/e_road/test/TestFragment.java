package com.e_road.test;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;

import com.e_road.R;
import com.e_road.view.HomeContentActionView;

public class TestFragment extends Fragment {
	
	private HomeContentActionView hcav_game;
	private HomeContentActionView hcav_apps;
	private HomeContentActionView hcav_feature;
	private HomeContentActionView hcav_service;
	
	private LinearLayout linearLayout;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.home_content_fragment, container, false);
		findViews(view);
		return view;
	}

	private void findViews(View view) {
		hcav_game = (HomeContentActionView) view.findViewById(R.id.hcav_game);
		hcav_apps = (HomeContentActionView) view.findViewById(R.id.hcav_apps);
		hcav_feature = (HomeContentActionView) view.findViewById(R.id.hcav_feature);
		hcav_service = (HomeContentActionView) view.findViewById(R.id.hcav_service);
		linearLayout = (LinearLayout) view.findViewById(R.id.ll_home_content_action);
		
		hcav_game.setImageResources(R.drawable.home_games);
		hcav_apps.setImageResources(R.drawable.home_jingpin);
		hcav_feature.setImageResources(R.drawable.home_tesezhuanqu);
		hcav_service.setImageResources(R.drawable.home_items4);
		
		hcav_game.setTextViewText("热门游戏");
		hcav_apps.setTextViewText("精选应用");
		hcav_feature.setTextViewText("特色专区");
		hcav_service.setTextViewText("本地服务");
		
		autoScreen();
	}

	//8个边距
	private int margin_action = 5;
	//四个标签项的宽
	private int width_action;
	
	private void autoScreen() {
		Display display = getActivity().getWindow().getWindowManager().getDefaultDisplay();
		DisplayMetrics metrics = new DisplayMetrics();
		display.getMetrics(metrics);
		width_action = (metrics.widthPixels - 8*margin_action)/4;
		LayoutParams layoutParams = linearLayout.getLayoutParams();
		layoutParams.height = width_action;
		linearLayout.setLayoutParams(layoutParams);
	}
}
