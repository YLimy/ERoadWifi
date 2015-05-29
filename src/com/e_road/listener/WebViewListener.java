package com.e_road.listener;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.view.Gravity;
import android.webkit.DownloadListener;
import android.widget.Toast;

import com.e_road.R;
import com.e_road.utils.FileTypeUtil;
import com.e_road.utils.FileTypeUtil.FileType;
import com.e_road.utils.IConstant;
import com.e_road.utils.LoggerUtil;
import com.e_road.view.dialog.ButtomDialog;
import com.e_road.view.dialog.ButtomDialog.ButtonCallBack;

public class WebViewListener implements DownloadListener {
	private static final String TAG = "WebViewListener";
	private Context context;
	private FileType fileType;

	public WebViewListener(Context context) {
		this.context = context;
	}

	@Override
	public void onDownloadStart(String url, String userAgent,
			String contentDisposition, String mimetype, long contentLength) {
		// TODO Auto-generated method stub
		LoggerUtil.d(TAG, "mimetype==>" + mimetype);
		fileType = FileTypeUtil.getMIMEType(url);
		showItem(url, fileType);
	}

	/**
	 * 根据不同的类型展示不同的对话框或不展示
	 * 
	 * @param fileType
	 *            文件类型
	 */
	public void showItem(String url, FileType fileType) {
		switch (fileType) {
		case apk:
			// downFile(url);
			ifApk(url);
			break;
		case video:

			break;
		case audio:

			break;

		case image:

			break;
		case pdf:

			break;

		case others:

			break;
		}
	};

	/**
	 * 处理APK链接
	 */
	private void ifApk(final String url) {
		ButtomDialog dialog = new ButtomDialog(context);

		String fileName = url.substring(url.lastIndexOf("/") + 1, url.length());
		
		dialog.addOptionButton("下载" + fileName, R.drawable.btn_add_accounts,
				new ButtonCallBack() {

					@Override
					public void callBack() {
						downFile(url);
					}
				});

		dialog.addOptionButton("取消", R.drawable.btn_quick_register,
				new ButtonCallBack() {

					@Override
					public void callBack() {
						// TODO Auto-generated method stub

					}
				});

		dialog.show();
	}

	/**
	 * 下载文件
	 * 
	 * @param url
	 */
	private void downFile(String url) {
		if (checkSDCard()) {
			// 将下载地址广播
			Intent intent = new Intent();
			intent.setAction(IConstant.ACTION_DOWNLOAD_URL);
			intent.putExtra("url", url);
			context.sendBroadcast(intent);
		}
	}

	/**
	 * 检查是否有SD卡
	 * 
	 * @return
	 */
	private boolean checkSDCard() {
		if (!Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			Toast t = Toast.makeText(context, "需要SD卡。", Toast.LENGTH_LONG);
			t.setGravity(Gravity.CENTER, 0, 0);
			t.show();
			return false;
		}
		return true;
	}

}
