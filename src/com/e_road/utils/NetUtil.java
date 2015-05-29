package com.e_road.utils;

import java.io.IOException;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.ParseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.alibaba.fastjson.JSONException;
import com.e_road.R;
import com.e_road.utils.parser.BaseParser;
import com.e_road.vo.RequestVo;

/**
 * 网络访问工具类
 * 
 * @author CaiMeng
 * 
 */
public class NetUtil {
	
	/** 请求超时时间 */
	private static int timeout = 15000;
	/** 请求头 */
	private static Header[] headers = new BasicHeader[11];
	static {
		headers[0] = new BasicHeader("Appkey", "");
		headers[1] = new BasicHeader("Udid", "");
		headers[2] = new BasicHeader("Os", "");
		headers[3] = new BasicHeader("Osversion", "");
		headers[4] = new BasicHeader("Appversion", "");
		headers[5] = new BasicHeader("Sourceid", "");
		headers[6] = new BasicHeader("Ver", "");
		headers[7] = new BasicHeader("Userid", "");
		headers[8] = new BasicHeader("Usersession", "");
		headers[9] = new BasicHeader("Unique", "");
		headers[10] = new BasicHeader("Cookie", "");

	}

	/**
	 * 检查网络连接<br>
	 * 并没有对3G,wifi等的识别
	 * 
	 * @param context
	 * @return false:网络不可用<br>
	 *         true:网络可用
	 */
	public static boolean hasNetwork(Context context) {
		ConnectivityManager manager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = manager.getActiveNetworkInfo();
		if (null == info || !info.isAvailable()) {
			return false;
		}
		return true;
	}

	/**
	 * get请求
	 * 
	 * @param requestVo
	 * @return
	 */
	public static Object get(RequestVo requestVo) {
		try {
			DefaultHttpClient client = new DefaultHttpClient();
			// 设置请求超时时间为10s
			//TODO 在连接到9797168.com时请求超时时间不起作用
			client.getParams().setParameter(
					CoreConnectionPNames.CONNECTION_TIMEOUT, 10000);
			String url = requestVo
					.getContext()
					.getString(R.string.url_host)
					.concat(requestVo.getContext().getString(
							requestVo.getRequestUrl()));
			LoggerUtil.d("NetUtil", "get请求：" + url);
			HttpGet get = new HttpGet(url);
			get.setHeaders(headers);
			HttpResponse response = client.execute(get);
			return handleResponse(requestVo, response);
		} catch (ConnectTimeoutException e) {
			return "连接服务器超时";
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return "--get请求异常--";
		}
	}

	/**
	 * 验证是否需要登陆<br>
	 * 
	 * @param result
	 * @throws JSONException
	 */
	@SuppressWarnings("unused")
	private static boolean invilidateLogin(String result) throws JSONException {
		String responsCode = BaseParser.checkResponse(result);
		if (null != responsCode && IConstant.LOGIN_OUT.equals(responsCode)) {
			return true;
		} else {
			// TODO responsCode=null的情况
			return false;
		}
	}

	private static void setCookies(HttpResponse response) {
		if (response.getHeaders("Set-Cookie").length > 0) {
			String cookie = response.getHeaders("Set-Cookie")[0].getValue();
			LoggerUtil.d("NetUtil", "cookie:" + cookie);
			headers[10] = new BasicHeader("Cookie", cookie);
		}
	}

	/**
	 * post请求
	 * 
	 * @param requestVo
	 * @return
	 */
	public static Object post(RequestVo requestVo) {
		try {
			DefaultHttpClient client = new DefaultHttpClient();
			client.getParams().setParameter(
					CoreConnectionPNames.CONNECTION_TIMEOUT, timeout);
			String url = requestVo
					.getContext()
					.getString(R.string.url_host)
					.concat(requestVo.getContext().getString(
							requestVo.getRequestUrl()));
			LoggerUtil.d("NetUtil", "post请求" + url);
			HttpPost post = new HttpPost(url);
			post.setHeaders(headers);

			HashMap<String, String> map = requestVo.getRequestMap();
			if (null != map && !map.isEmpty()) {
				ArrayList<BasicNameValuePair> parameters = new ArrayList<BasicNameValuePair>();
				for (Map.Entry<String, String> entry : map.entrySet()) {
					BasicNameValuePair pair = new BasicNameValuePair(
							entry.getKey(), entry.getValue());
					parameters.add(pair);
				}
				HttpEntity entity = new UrlEncodedFormEntity(parameters,
						"UTF-8");
				post.setEntity(entity);
			}
			
			HttpResponse response = client.execute(post);
			
			LoggerUtil.d("NetUtil", "处理response");
			return handleResponse(requestVo, response);

		} catch (ConnectTimeoutException e) {
			LoggerUtil.d("NetUtil", "error===>time_out");
			return "连接服务器超时";
		} catch (ConnectException e) {
			LoggerUtil.d("NetUtil", "error===>unConnected");
			return "未能连接到服务器";
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return "--post请求异常--";
		}
	}

	/**
	 * 处理response
	 * 
	 * @param requestVo
	 * @param response
	 * @return 1.可转换成parser指定对象的Object<br>
	 *         2.枚举类型：NetStatus<br>
	 *         3.异常：异常信息
	 */
	private static Object handleResponse(RequestVo requestVo,
			HttpResponse response) {
		int statusCode = response.getStatusLine().getStatusCode();

		if (statusCode == HttpStatus.SC_OK) {
			setCookies(response);
			String result;
			try {
				result = EntityUtils.toString(response.getEntity(), "UTF-8");
				LoggerUtil.d("NetUtil", "request_result" + result);

				if (invilidateLogin(result)) {
					// 业务需要登陆，返回枚举类型
					return NetStatus.Login;
				}
//				return requestVo.getParser().parseJson4App(result);
				return requestVo.getParser().parseJson(result);

			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "--解析错误--";
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "--IO错误--";
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "--json格式错误--";
			}

		} else if (statusCode == HttpStatus.SC_INTERNAL_SERVER_ERROR) {
			// 500：服务器错误
			LoggerUtil.d("NetUtil", "error===>500");
			return "--服务器错误--";

		} else if (statusCode == HttpStatus.SC_NOT_FOUND) {
			// 404：请求网页不存在
			LoggerUtil.d("NetUtil", "error===>404");
			return "--请求网页不存在--";
		}
		LoggerUtil.d("NetUtil", "error===>unknown");
		return "--未知异常--";
	}

	/**
	 * 枚举类型 : 登陆状态<br>
	 * Login
	 * 
	 * @author CaiMeng
	 * 
	 */
	public static enum NetStatus {
		Login
	}
}
