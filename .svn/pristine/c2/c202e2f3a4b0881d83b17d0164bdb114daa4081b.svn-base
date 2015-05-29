package com.e_road.provider;

import com.e_road.R;

import android.content.Context;
import android.view.ActionProvider;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.SubMenu;
import android.view.View;

/**
 * actionBar "add"标签的provider
 * 
 * @author CaiMeng
 * 
 */
public class AddActionProvider extends ActionProvider {

	public AddActionProvider(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View onCreateActionView() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 当父menu创建时
	 */
	@Override
	public void onPrepareSubMenu(SubMenu subMenu) {
		subMenu.clear();
		//收藏
		subMenu.add(R.string.menu_add_favorite)
		.setIcon(R.drawable.ofm_feedback_icon)
		.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				// TODO Auto-generated method stub
				return false;
			}
		});
		//书签
		subMenu.add(R.string.menu_add_bookmarks)
		.setIcon(R.drawable.ofm_card_icon)
		.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				// TODO Auto-generated method stub
				return false;
			}
		});
		//WiFi热点
		subMenu.add(R.string.menu_add_wifihot)
		.setIcon(R.drawable.actionbar_search_icon)
		.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				// TODO Auto-generated method stub
				return false;
			}
		});
	}

	@Override
	public boolean hasSubMenu() {
		// 是否有父menu，onPrepareSubMenu()调用前会执行此项检查
		return true;
	}

}
