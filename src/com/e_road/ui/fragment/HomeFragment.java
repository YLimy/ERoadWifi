package com.e_road.ui.fragment;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.e_road.R;
import com.e_road.listener.WebViewListener;
import com.e_road.utils.LoggerUtil;
import com.e_road.view.webview.IWebSettingsUtils;
import com.e_road.view.webview.IWebSettingsUtils.IWebCallBack;

/**
 * 标签home显示的内容
 * 
 * @author CaiMeng
 * 
 */
public class HomeFragment extends ViewPagerBaseFragment implements IWebCallBack{
	//TODO 在页面加载完时会导致程序占用大量内存甚至引起假死机情况。

	private LinearLayout layout;
	private WebView webView_home;
	/** 进度条 */
	private ProgressBar progressBar;
	/** 网络是否可用 */
	// private boolean isNetwork = true;
	/** 缓存网站链接 */
	private String urlCache = null;
	/** WebKit配置 */
	private IWebSettingsUtils settings;

//	@Override
//	public void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
		// if(null != savedInstanceState){
		// urlCache = savedInstanceState.getString("saved_url");
		// }
//	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.home_fragment, container, false);
		findView(view);
		init();
		setListener();
		loadUrl();
		return view;
	}

	/**
	 * 加载页面
	 */
	private void loadUrl(){
//		if (urlCache != null) {
//			webView_home.loadUrl(urlCache);
//		} else {
			webView_home.loadUrl(getResources().getString(R.string.url_home));
//		}
	}
	
	public void refresh(){
//		webView_home.clearCache(false);
		if(IWebSettingsUtils.URL404.equals(settings.getUrlCache()) ){
			//网络错误
			if(webView_home.canGoBack()){
				//能回退则回退
				webView_home.goBack();
			}else{
				//不能回退则重新加载失败页面
				webView_home.loadUrl(settings.getFailedUrl());
			}
		}else{
			//网络正常则刷新
			webView_home.reload();
		}
	}

	/**
	 * 查找view中的控件
	 * 
	 * @param view
	 */
	private void findView(View view) {
		layout = (LinearLayout) view.findViewById(R.id.ll_home_frag);
//		webView_home = (WebView) view.findViewById(R.id.wv_home);
		progressBar = (ProgressBar) view.findViewById(R.id.pb_home);
	}

	/**
	 * 给控件绑定事件
	 */
	private void init() {
		/*
		 * 传入application的context解决了activity被消除后依然保持引用的问题
		 * 但在加载flash时会把WebView作为父控件
		 * 在绘制时去找activity的context结果因为发现的是application的context而发生不可预期的结果
		 */
		 webView_home = new WebView(getActivity().getApplicationContext());
		 layout.addView(webView_home);
		settings = new IWebSettingsUtils(getActivity(), webView_home,
				progressBar,this);
		settings.setWebViewConfigure();
	}

	/**
	 * 设置监听器
	 */
	private void setListener() {
		webView_home.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				// 监听按键：按下
				if (event.getAction() == KeyEvent.ACTION_DOWN) {
					// 是否回退键
					if ((keyCode == KeyEvent.KEYCODE_BACK)
							&& webView_home.canGoBack()) {
						webView_home.goBack();
						return true;
					}
				}
				return false;
			}
		});

		webView_home.setDownloadListener(new WebViewListener(
				getActivity()));
	}
	
	@Override
	public void onPause() {
		super.onPause();
		webView_home.pauseTimers();
//		if(getActivity().isFinishing()){
//			webView_home.loadUrl("about:blank");
//		}
	}
	
	@Override
	public void onResume() {
		LoggerUtil.d("HomeFragment", "onResume");
		super.onResume();
		webView_home.resumeTimers();
	}
	
	@Override
	public void onStop() {
		webView_home.freeMemory();
		super.onStop();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (null != settings) {
			settings.clearWebViewCache();
			settings.unregisterReceiver();
		}
		webView_home = null;
//		webView_home.removeAllViews();
//		webView_home.destroy();
		progressBar = null;
		layout = null;
	}

	@Override
	public void onRefresh() {
		refresh();
	}

	@Override
	public void onBack() {
		loadUrl();
	}

	@Override
	public String onSaveUrl() {
		return settings.getUrlCache();
	}

}
