package com.e_road.view.dialog;

import java.util.ArrayList;
import java.util.List;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import com.e_road.R;

public abstract class BaseButtomDialog extends Dialog {
	protected LayoutParams ll_params = new LayoutParams(
			LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
	protected Context context;
	private LinearLayout layout;
	/** 保存功能按钮 */
	protected List<Button> list_bt = new ArrayList<Button>();

	public BaseButtomDialog(Context context) {
		super(context,R.style.i_dialog);
		this.context = context;
	}
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_buttom);
		layout = (LinearLayout) findViewById(R.id.ll_dialog_buttom);
		layoutStyle();
		addButton();
	}
	
	/**
	 * 定义Dialog样式
	 */
	private void layoutStyle(){
		Window window = getWindow();
		WindowManager.LayoutParams params = window.getAttributes();
		params.width = WindowManager.LayoutParams.MATCH_PARENT;
		window.setGravity(Gravity.BOTTOM);
		window.setAttributes(params);
	}
	
	/**
	 * 动态添加固定样式的Button
	 * @param button
	 */
	protected void addButton() {
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);
		params.setMargins(0, 0, 0, 15);
		if(list_bt.size() > 0){
			for(Button button : list_bt){
				layout.addView(button, params);
			}
		}else{
			return;
		}
	}

}
