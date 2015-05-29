package com.e_road.view.dialog;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.widget.Button;

import com.e_road.R;

/**
 * 自定义对话框<br>
 * 下方显示
 * 
 * @author CaiMeng
 * 
 */
public class ButtomDialog extends BaseButtomDialog {

	public ButtomDialog(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 添加功能按钮<br>
	 * 先添加的显示在上面
	 * 
	 * @param msg
	 * @param callback
	 */
	public void addOptionButton(String msg, final ButtonCallBack callback) {

		Button button = new Button(context);
		button.setText(msg);
		button.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				callback.callBack();
				dismiss();
			}
		});

		list_bt.add(button);
	}

	public void addOptionButton(String msg, int bgColor,
			final ButtonCallBack callback) {

		Button button = new Button(context);
		button.setText(msg);
		button.setTextSize(16f);
		button.setTextColor(context.getResources().getColor(R.color.white));
		button.setBackgroundResource(bgColor);
		button.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				callback.callBack();
				dismiss();
			}
		});

		list_bt.add(button);
	}

	public interface ButtonCallBack {
		public abstract void callBack();
	}
	
}
