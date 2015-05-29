package com.e_road.ui.fragment;

import java.util.Map;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.e_road.R;
import com.e_road.test.TestData;

/**
 * 广告走马灯
 * @author CaiMeng
 *
 */
public class AdsMarqueeFragment extends Fragment implements OnClickListener{
	private TextView tv_ads;
	private Map<String, String> map;
	private String url;
	private CountDownTimer timer;
	private CountDownTimer timerShow;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.ads_marquee, container, false);
		findViews(view);
		init();
		setListener();
		timer.start();
		return view;
	}

	private void findViews(View view) {
		tv_ads = (TextView) view.findViewById(R.id.tv_ads_marquee_frag);
	}

	private void init() {
		timer = new CountDownTimer(4000000,5000) {
			
			@Override
			public void onTick(long millisUntilFinished) {
				map = TestData.personAction();
				String text = map.get("personAction");
				url = map.get("url");
				tv_ads.setText(text);
				tv_ads.setVisibility(View.VISIBLE);
				//开始走马灯
				tv_ads.setSelected(true);
//				showView();
//				timerShow.start();
			}
			
			private void showView() {
				timerShow = new CountDownTimer(2000,1000) {
					
					@Override
					public void onTick(long millisUntilFinished) {
						tv_ads.setVisibility(View.VISIBLE);
					}
					
					@Override
					public void onFinish() {
						tv_ads.setVisibility(View.GONE);
					}
				};
			}

			@Override
			public void onFinish() {
				tv_ads.setText("没有最新消息");
			}
		};
	}

	private void setListener() {
		tv_ads.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Toast.makeText(getActivity(), url, Toast.LENGTH_LONG).show();
	}
	
}
