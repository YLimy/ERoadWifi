package com.e_road.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.e_road.R;
import com.e_road.ui.fragment.DownloadFinishedFragment;
import com.e_road.ui.fragment.DownloadingFragment;

/**
 * 下载管理
 * 
 * @author CaiMeng
 * 
 */
public class DownloadManagerActivity extends FragmentActivity implements
		OnClickListener {
	private ImageView iv_back;
	private ImageView iv_line;
	private TextView tv_loading;
	private TextView tv_finished;
	/** TAB动作条长度 */
	private int mScreen1_2;

	private FragmentManager fm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// getActionBar().hide();
		setContentView(R.layout.download_manager_activity);
		fm = getSupportFragmentManager();
		findViews();
		initTabLine();
		setLinstener();
		progress();
	}

	private void findViews() {
		iv_back = (ImageView) findViewById(R.id.iv_download_manager_back);
		iv_line = (ImageView) findViewById(R.id.iv_download_manager_line);
		tv_loading = (TextView) findViewById(R.id.tv_download_manager_loading);
		tv_finished = (TextView) findViewById(R.id.tv_download_manager_finished);
	}

	private void initTabLine() {
		Display display = getWindow().getWindowManager().getDefaultDisplay();
		DisplayMetrics metrics = new DisplayMetrics();
		display.getMetrics(metrics);
		mScreen1_2 = metrics.widthPixels / 2;
		LayoutParams layoutParams = iv_line.getLayoutParams();
		layoutParams.width = mScreen1_2;
		iv_line.setLayoutParams(layoutParams);
		selectedLine(0);
	}

	private void selectedLine(int position) {
		// 设置TAB动作条长度
		
		android.widget.RelativeLayout.LayoutParams params = (android.widget.RelativeLayout.LayoutParams)iv_line.getLayoutParams();
		params.leftMargin = mScreen1_2 * position;
		iv_line.setLayoutParams(params);
	}

	private void setLinstener() {
		iv_back.setOnClickListener(this);
		tv_loading.setOnClickListener(this);
		tv_finished.setOnClickListener(this);
	}

	private void progress() {
		showFragment(new DownloadingFragment(), "downLoading");
	}

	/**
	 * 显示主体信息
	 * 
	 * @param fragment
	 *            对象
	 * @param fragmentTag
	 *            标识
	 */
	private void showFragment(Fragment fragment, String fragmentTag) {
		fm.beginTransaction()
				.replace(R.id.ll_download_manager_content, fragment,
						fragmentTag).commit();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_download_manager_back:
			finish();
			break;
		case R.id.tv_download_manager_loading:
			showFragment(new DownloadingFragment(), "downLoading");
			selectedLine(0);
			break;
		case R.id.tv_download_manager_finished:
			showFragment(new DownloadFinishedFragment(), "downLoadFinished");
			selectedLine(1);
			break;
		}

	}

	@Override
	protected void onDestroy() {
		iv_back = null;
		iv_line = null;
		tv_loading = null;
		tv_finished = null;
		super.onDestroy();
	}
}
