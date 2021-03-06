package com.e_road.utils;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.Map.Entry;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Environment;

public class CommonUtil {
	private static final String TAG = "CommonUtil";
	/**
	 * 显示对话框
	 * @param context
	 * @param message
	 */
	public static void showInfoDialog(Context context, String message) {
		showInfoDialog(context, message, "提示", "确定", null);
	}

	/**
	 * 邮箱验证
	 * @param paramString
	 * @return
	 */
	public static boolean isValidEmail(String paramString) {

		String regex = "[a-zA-Z0-9_\\.]{1,}@(([a-zA-z0-9]-*){1,}\\.){1,3}[a-zA-z\\-]{1,}";
		if (paramString.matches(regex)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 手机验证
	 * @param paramString
	 * @return
	 */
	public static boolean isValidMobiNumber(String paramString) {
		String regex = "^1\\d{10}$";
		if (paramString.matches(regex)) {
			return true;
		}
		return false;
	}

	/**
	 * 显示对话框
	 * @param context
	 * @param message
	 * @param titleStr
	 * @param positiveStr
	 * @param onClickListener
	 */
	public static void showInfoDialog(Context context, String message, String titleStr, String positiveStr,
			DialogInterface.OnClickListener onClickListener) {
		AlertDialog.Builder localBuilder = new AlertDialog.Builder(context);
		if(null != titleStr){
			localBuilder.setTitle(titleStr);
		}
		localBuilder.setMessage(message);
		if (onClickListener == null)
			onClickListener = new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {

				}
			};
		localBuilder.setPositiveButton(positiveStr, onClickListener);
		localBuilder.show();
	}
	
	/**
	 * 将long转换成日期格式
	 * @param param
	 */
	@SuppressLint("SimpleDateFormat")
	public static String dateFormat(long param){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss E");
		return sdf.format(new Date(param));
	}
	
	/**
	 * 保存应用数据
	 * @param context
	 * @param fileName
	 * @param key
	 * @param value
	 */
	public static void saveSharedPreferences(Context context, String fileName, String key,String value){
		SharedPreferences sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putString(key, value);
		editor.commit();
	}
	/**
	 * 保存应用数据
	 * @param context
	 * @param fileName
	 * @param map
	 */
	public static void saveSharedPreferences(Context context, String fileName, Map<String, String> map){
		SharedPreferences sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
		Editor editor = sp.edit();
		if(null != map && map.size() > 0){
			for(Entry<String, String> entry : map.entrySet()){
				editor.putString(entry.getKey(), entry.getValue());
				LoggerUtil.d(TAG, "已保存"+entry.getKey()+":"+entry.getValue());
			}
			editor.commit();
		}
	}
	
	/**
	 * 通过SharedPreferences判断用户是否已登陆
	 * @param context
	 */
	public static boolean isLogin(Context context){
		return !(context.getSharedPreferences("sp", Context.MODE_PRIVATE)
				.getString(IConstant.LOGIN_USER_NAME, "").equals(""));
	}
	
	/**
	 * 检车是否有SD卡
	 * @return
	 */
	public static boolean checkSDCard(){
		if (!Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			return false;
		}
		return true;
	}
}
