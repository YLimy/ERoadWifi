package com.e_road.utils;

public class IConstant {
	
	/** 默认自动连接的WiFi */
	public static final String SSID = "9797168.com";
	
	/** 主界面下方按钮默认选定值 */
	public static int BUTTOM_DEFAULT = 0;
	/** 主界面下方按钮选定值1 */
	public static final int BUTTOM_1 = 1;
	/** 主界面下方按钮选定值2 */
	public static final int BUTTOM_2 = 2;
	/** 主界面下方按钮选定值3 */
	public static final int BUTTOM_3 = 3;
	/** 主界面下方按钮选定值4 */
	public static final int BUTTOM_4 = 4;
	/** 主界面下方按钮选定值5 */
	public static final int BUTTOM_5 = 5;
	
	public static final String USERNAME = "phone";
	public static final String PASSWORD = "pswd";
	public static final String NICKNAME = "nickname";
	/** 相应标识符 */
	public static final String RESPONSE = "response";
	/** 已登陆状态 */
	public static final String LOGIN = "login";
	/** 登陆错误 */
	public static final String LOGIN_ERRO = "login_erro";
	/** 需要验证登陆时处于未登陆状态 */
	public static final String LOGIN_OUT = "login_out";
	/** 无需登录 */
	public static final String LOGIN_NOT = "login_not";
	/** 用户已存在 */
	public static final String REGISTER_EXIST = "phone_already_exists";
	/** 服务器数据异常 */
	public static final String RESPONSE_ERRO = "response_erro";
	/** 注册成功 */
	public static final String RESPONSE_SUCCESS = "register";
	
	/** 未连接网络 */
	public static final int NETWORK_FAILED = 0;
	/** 网络正常 */
	public static final int NETWORK_OK = 1;
	
	/** 请求码login_not=403 */
	public static final int REQUESTCODE_BASE_LOGIN_NOT = 403;
	/** key:login_not */
	public static final String IK_BASE_LOGIN_NOT = "login_not";
	/** 返回码login_not=4031 */
	public static final int RESULTCODE_LOGIN_LOGIN = 4031;
	
	/** SharedPreferences中储存已登录用户的标识 */
	public static final String LOGIN_USER_NAME = "username";
	/** SharedPreferences中储存已登录用户密码的标识 */
	public static final String LOGIN_USER_PASSWORD = "password";
	/** SharedPreferences中储存已下载完成的文件信息 */
	public static final String SP_DOWNLOAD = "download_infos";

	/** 显示中间控件的标识 */
	public static final String CONTENT_SHOW = "content_show";
	
	/** 广播下载地址 */
	public static final String ACTION_DOWNLOAD_URL = "com.e_road.service.DownLoadService_url";
	/** 广播下载进度 */
	public static final String ACTION_DOWNLOAD_PROGRESS = "com.e_road.service.DownLoadService_progress";
}
