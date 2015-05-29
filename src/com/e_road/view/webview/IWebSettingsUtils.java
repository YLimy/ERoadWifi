package com.e_road.view.webview;

import java.io.File;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.impl.client.DefaultHttpClient;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.webkit.HttpAuthHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.PluginState;
import android.webkit.WebSettings.RenderPriority;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.e_road.application.ERoadApplication;
import com.e_road.utils.LoggerUtil;

/**
 * WebKit的设置
 * 
 * @author CaiMeng
 * 
 */
public class IWebSettingsUtils {
	private static final String TAG = "IWebSettingsUtils";

	private Context context;
	/** webView控件 */
	private WebView webView;
	/** 进度条 */
	private ProgressBar progressBar;
	/** 网络是否可用 */
	private boolean isNetwork = true;
	/** 缓存网站链接 */
	private String urlCache = "";
	private String failedUrl = "";
	private String appCachePath;

	/** 位于assets目录下的静态网页 */
	public static final String URL404 = "file:///android_asset/web_error.html";
	/** 404错误的HTML消息头 */
	private CharSequence notfound_404 = "404 Not Found";
	private CharSequence notfound = "找不到网页";
	private IWebCallBack callback;

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case -1:// 网络不可用
				showToast();
				break;
			case 0:

				break;

			case 1:// WebView receive error
				// webView.stopLoading();
//				webView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
				 webView.loadUrl(URL404);
				break;
			}
		}

	};

	/**
	 * WebKit配置的构造器
	 * 
	 * @param context
	 * @param webView
	 * @param progressBar
	 */
	public IWebSettingsUtils(Context context, WebView webView, ProgressBar progressBar,IWebCallBack callback) {
		this.context = context;
		this.webView = webView;
		this.progressBar = progressBar;
		this.callback = callback;
		appCachePath = ERoadApplication.getCachPath();
	}

	/**
	 * 配置WebView参数
	 */
	@SuppressLint("SetJavaScriptEnabled")
	@SuppressWarnings("deprecation")
	public void setWebViewConfigure() {
		// webView.clearCache(false);
		WebSettings settings = webView.getSettings();

		// init();

		// 支持JavaScript
		settings.setJavaScriptEnabled(true);
		// webView的内容全屏或者充满父框架
		settings.setLoadWithOverviewMode(true);
		// 设置任意比例缩放available
		// settings.setUseWideViewPort(true);
		// 支持缩放
		// settings.setBuiltInZoomControls(true);
		// 支持flash插件，18版本后不再支持，因为adobe不发布移动新版本了
		settings.setPluginState(PluginState.ON);

		// 支持双击缩放
		// settings.supportZoom();
		settings.setAllowFileAccess(true);
		// settings.setBuiltInZoomControls(false);

		// 支持通过js打开新的窗口
		// settings.setJavaScriptCanOpenWindowsAutomatically(true);
		// settings.setLoadsImagesAutomatically(true);
		settings.setSavePassword(true);
		// settings.setLightTouchEnabled(true);//TODO 导致百度导航条混乱
		// settings.setUserAgentString("Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10_6_4; zh-tw) "
		// +
		// "AppleWebKit/533.16 (KHTML, like Gecko) Version/5.0 Safari/533.16");
		settings.setPluginsEnabled(true);
		settings.setPluginState(WebSettings.PluginState.ON);

		// 提高渲染的优先级
		settings.setRenderPriority(RenderPriority.HIGH);
		settings.setEnableSmoothTransition(true);

		settings.setCacheMode(WebSettings.LOAD_DEFAULT);
		settings.setDatabaseEnabled(true);
		settings.setDatabasePath(appCachePath);
		settings.setAppCachePath(appCachePath);
		settings.setAppCacheEnabled(true);
		settings.setAppCacheMaxSize(1024 * 1024 * 5);

		// 取消滚动条
		webView.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
		// 触摸焦点起作用
		webView.requestFocus();

		// 处理解析和网络渲染
		webView.setWebChromeClient(new WebChromeClient() {

			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				if (newProgress == 100) {
					progressBar.setVisibility(View.GONE);
				} else {
					if (progressBar.getVisibility() == View.GONE)
						progressBar.setVisibility(View.VISIBLE);
					progressBar.setProgress(newProgress);
				}
				super.onProgressChanged(view, newProgress);
			}

			@Override
			public void onReceivedTitle(WebView view, String title) {
				LoggerUtil.d(TAG, "title===>" + title);
				if (title.contains(notfound_404) || title.contains(notfound)) {
					// 404未找到
					view.stopLoading();
					Message msg = handler.obtainMessage();
					msg.what = 1;
					handler.sendMessage(msg);
				}
			}

		});

		webView.setWebViewClient(new WebViewClient() {

			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				// // 超链接在当前WebView中跳转，而不是调用浏览器
				LoggerUtil.d(TAG, "正在加载：" + url);
				// webView.loadUrl(url);
//				if (url.startsWith("http://") && getRespStatus(url) == 404) {
//					view.stopLoading();
//					// 载入本地assets文件夹下面的错误提示页面404.html
//					view.loadUrl("file:///android_asset/404.html");
//				} else {
//					view.loadUrl(url);
//				}
				// return true;
				return false;
			}

			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				// 页面加载开始时调用
				urlCache = url;
				LoggerUtil.d("HomeFragment", "=========>" + urlCache);
				if (!isNetwork) {
					// TODO 此处调用了两次 1.目标页面；2.404页面；
					Message msg = handler.obtainMessage();
					msg.what = -1;
					handler.sendMessage(msg);
					return;
				}
				if (url.endsWith(".apk")) {
					// TODO 下载apk文件
				}
				super.onPageStarted(view, url, favicon);
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				// TODO 页面加载结束时调用
				LoggerUtil.d(TAG, "加载完成：" + url);
				// init();
//				view.loadUrl("javascript:LinkClick()");
				view.loadUrl("javascript:auth()");
				view.loadUrl("javascript:cli()");
				LoggerUtil.d(TAG, "js调用：" + url);
				super.onPageFinished(view, url);
			}

			@Override
			public void onReceivedError(WebView view, int errorCode,
					String description, String failingUrl) {
				// TODO Auto-generated method stub
				LoggerUtil.d(TAG, "加载失败：" + errorCode + ">" +failingUrl);
				failedUrl = failingUrl;
				try {
					// webView.stopLoading();
					Message msg = handler.obtainMessage();
					msg.what = 1;
					handler.sendMessage(msg);
				} catch (Exception e) {
					// TODO: handle exception
				}
				try {
					webView.clearView();
				} catch (Exception e) {
					// TODO: handle exception
				}
//				if (webView.canGoBack()) {
//					webView.goBack();
//				}
				// super.onReceivedError(view, errorCode, description,
				// failingUrl);
			}

			@Override
			public void onReceivedHttpAuthRequest(WebView view,
					HttpAuthHandler handler, String host, String realm) {
				// TODO Auto-generated method stub
				super.onReceivedHttpAuthRequest(view, handler, host, realm);
			}

		});

		IntentFilter mFilter = new IntentFilter();
		mFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
		context.registerReceiver(mReceiver, mFilter);
	}
	
	/**
	 * 获取当前加载的页面
	 * @return
	 */
	public String getUrlCache(){
		return this.urlCache;
	}

	public String getFailedUrl(){
		return this.failedUrl;
	}
	/** 接收网络状态变更 */
	private BroadcastReceiver mReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			ConnectivityManager connectManager = (ConnectivityManager) (context
					.getSystemService(Context.CONNECTIVITY_SERVICE));
			NetworkInfo networkInfo = connectManager.getActiveNetworkInfo();
			if (null != networkInfo && networkInfo.isAvailable()) {
				isNetwork = true;
			} else {
				isNetwork = false;
			}

		}
	};

	/**
	 * 取消注册接收器
	 */
	public void unregisterReceiver() {
		if (null != mReceiver) {
			context.unregisterReceiver(mReceiver);
		}
	}

	/**
	 * 在页面加载完成前部加载图片<br>
	 * 页面加载结束后加载图片
	 */
	public void init() {
		if (Build.VERSION.SDK_INT >= 19) {
			webView.getSettings().setLoadsImagesAutomatically(true);
		} else {
			webView.getSettings().setLoadsImagesAutomatically(false);
		}
	}

	public void clearWebViewCache() {

		try {
			context.deleteDatabase("webview.db");
			context.deleteDatabase("webviewCache.db");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// WebView 缓存文件
		File appCacheDir = new File(appCachePath);
		LoggerUtil.e("IWebSettingsUtils",
				"appCacheDir path=" + appCacheDir.getAbsolutePath());

		File webviewCacheDir = new File(appCachePath);
		LoggerUtil.e("IWebSettingsUtils", "webviewCacheDir path="
				+ webviewCacheDir.getAbsolutePath());

		// 删除webview 缓存目录
		if (webviewCacheDir.exists()) {
			deleteFile(webviewCacheDir);
		}
		// 删除webview 缓存 缓存目录
		if (appCacheDir.exists()) {
			deleteFile(appCacheDir);
		}
	}

	/**
	 * 递归删除 文件/文件夹
	 * 
	 * @param file
	 */
	public void deleteFile(File file) {

		LoggerUtil.i(TAG,
				"delete file path=" + file.getAbsolutePath());

		if (file.exists()) {
			if (file.isFile()) {
				file.delete();
			} else if (file.isDirectory()) {
				File files[] = file.listFiles();
				for (int i = 0; i < files.length; i++) {
					deleteFile(files[i]);
				}
			}
			file.delete();
		} else {
			LoggerUtil.e("IWebSettingsUtils",
					"delete file no exists " + file.getAbsolutePath());
		}
	}

	/** 记录下toast显示的时间 */
	private long show_time = 0;

	/**
	 * 显示toast的方法<br>
	 * 每2秒才能调用一次
	 */
	private void showToast() {
		if (System.currentTimeMillis() - show_time > 2000) {
			Toast.makeText(context, "网络不可用", Toast.LENGTH_SHORT).show();
			show_time = System.currentTimeMillis();
		}
	}
	
//	private int getRespStatus(String url) {  
//        int status = -1;  
//        try {  
//                HttpHead head = new HttpHead(url);  
//                HttpClient client = new DefaultHttpClient();  
//                HttpResponse resp = client.execute(head);  
//                status = resp.getStatusLine().getStatusCode();  
//        } catch (IOException e) {}  
//        return status;  
//}  
	private boolean validStatusCode(String url) {  
        try {  
        	HttpHead head = new HttpHead(url);  
          HttpClient client = new DefaultHttpClient();  
          HttpResponse response = client.execute(head);  
            int statusCode = response.getStatusLine().getStatusCode();  
            String str = String.valueOf(statusCode);  
            if (str.startsWith("4") || str.startsWith("5")) {  
                return false;  
            }  
            return true;  
        } catch (Exception e) {  
            LoggerUtil.e(TAG, e.getMessage(),e);  
        }   
        return false;  
    }
	
	/**
	 * IWebSettings回调
	 * @author CaiMeng
	 *
	 */
	public interface IWebCallBack{

	}
}
