package com.e_road.view.actionbar;

import com.e_road.ui.WifiFragmentActivity;
import com.e_road.ui.fragment.ContentViewPagerFragment;
import com.e_road.utils.IConstant;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;

/**
 * 自定义ActionBar设置
 * 
 * @author CaiMeng
 * 
 */
public class MyActionBarSettings {

	/**
	 * 将一个activity置顶<br>
	 * 销毁其上的所有的activity
	 * 
	 * @param context
	 * @param class1
	 */
	public static void home2Activity(Context context, Class<?> class1) {
		// 在action bar点击APP icon; 回到 指定页面

		Intent intent = new Intent(context, class1);

		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

		context.startActivity(intent);
	}

	/**
	 * 回退到父activity<br>
	 * 需要建立一个根节点
	 * 
	 * @param activity
	 */
	public static void home2Patent(Activity activity) {
		// 获取父activity
		Intent upIntent = NavUtils.getParentActivityIntent(activity);
		// 判断是否能移动到栈顶
		if (NavUtils.shouldUpRecreateTask(activity, upIntent)) {
			TaskStackBuilder.create(activity)
					.addNextIntentWithParentStack(upIntent).startActivities();
		} else if (null != upIntent) {
			upIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			NavUtils.navigateUpTo(activity, upIntent);
		} else {
			// TODO
		}
	}

	/**
	 * 回退到上一个activity
	 * 
	 * @param activity
	 */
	public static void home2FrontActivity(Activity activity) {
		activity.finish();
	}

	/**
	 * 回退到上一次操作
	 * 
	 * @param activity
	 */
	public static void homeBackUp(Activity activity) {
		//实际上就是activity在finish前的动作，执行此方法后activity finish
		activity.onBackPressed();
	}
	
	
	/**
	 * 展示WiFi列表
	 */
	public static void showWifiList(Context context) {
		Intent intent = new Intent(context, WifiFragmentActivity.class);
		intent.putExtra(IConstant.CONTENT_SHOW, "wifi");
		intent.putExtra("isJump", false);
		context.startActivity(intent);
	}
	
	/**
	 * 刷新界面
	 * @param fm
	 */
	public static void refresh(FragmentManager fm){
		ContentViewPagerFragment viewPagerFragment = (ContentViewPagerFragment) fm
				.findFragmentByTag("viewPager");
		if(null != viewPagerFragment){
			viewPagerFragment.refresh();
		}
	}
}
